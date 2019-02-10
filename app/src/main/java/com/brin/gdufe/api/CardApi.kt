package com.brin.gdufe.api

import com.brin.gdufe.AppConfig
import com.brin.gdufe.bean.HttpResult
import com.brin.gdufe.model.CardInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CardApi {

    //饭卡余额
    @GET(AppConfig.Url.getCurrentCash)
    abstract fun getCurrentCash(): Observable<HttpResult<CardInfo>>


}