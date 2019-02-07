package com.brin.gdufe.api

import com.brin.gdufe.AppConfig
import com.brin.gdufe.bean.BasicInfo
import com.brin.gdufe.bean.HttpResult
import com.brin.gdufe.bean.Score
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface JwApi {

    @GET(AppConfig.Url.getScore)
    fun getScore(@Query("stu_time")stu_time : String, @Query("minor")minor:Int): Observable<HttpResult<List<Score>>>

    @GET(AppConfig.Url.getSchedule)
    fun getSchedule(@Query("stu_time")stu_time : String,@Query("split")spit : Int)

    @GET(AppConfig.Url.getBasicInfo)
    fun getBasicInfo(): Observable<HttpResult<BasicInfo>>
}