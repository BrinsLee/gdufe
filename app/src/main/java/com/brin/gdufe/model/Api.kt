package com.brin.gdufe.model

import com.brin.gdufe.AppConfig
import com.brin.gdufe.bean.BasicInfo
import com.brin.gdufe.bean.HttpResult
import com.brin.gdufe.bean.Score
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(AppConfig.Url.getScore)
    abstract fun getScore(@Query("stu_time") stu_time: String, @Query("minor") minor: Int): Observable<HttpResult<List<Score>>>

    @GET(AppConfig.Url.getBasicInfo)
    abstract fun getBasicInfo() : Observable<HttpResult<BasicInfo>>
}