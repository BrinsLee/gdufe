package com.brin.gdufe.model

import android.util.Log
import com.brin.gdufe.AppConfig
import com.brin.gdufe.bean.HttpResult
import com.brin.util.BasicParamsInterceptor
import io.reactivex.functions.Function
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class ApiUtils {
    companion object {
        protected var apiBuilder : Retrofit.Builder = Retrofit.Builder().baseUrl(AppConfig.BASE_URL)
        private val DEFAULT_TIMEOUT : Int = 2

        fun getApi(password :String):Retrofit{

            var basicParamsInterceptor = BasicParamsInterceptor.Builder()
                    .addParam("sno",AppConfig.sno)
                    .addParam("pwd",password)
                    .addParam("from","android")
                    .addParam("app_ver",AppConfig.appVer)
                    .build()
            var httpClientBuilder =  OkHttpClient.Builder()
                    .addInterceptor(basicParamsInterceptor)
                    .connectTimeout(5000,TimeUnit.MILLISECONDS)
            var api = apiBuilder.client(httpClientBuilder.build())
                    .build()
            return api

        }
    }
    init {
        apiBuilder.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }


    class HttpResultFunc<T> : Function<HttpResult<T>, T> {
        @Throws(Exception::class)
        override fun apply(httpResult: HttpResult<T>): T {
            Log.d("isSuccess","${httpResult.isSuccess}")
            if (!httpResult.isSuccess) {  //业务错误到onError里获取
                throw ApiException(httpResult.code, httpResult.msg!!)
            }
            return httpResult.data!!
        }
    }

    private class ApiException(var code: Int, var message1: String) : RuntimeException() {

        override val message: String?
            get() = message1

        //返回APi的code，因getCode()没法在回调里被调用，故随便找了个父类的方法重写
        override fun getLocalizedMessage(): String {
            return code.toString() + ""
        }
    }

}