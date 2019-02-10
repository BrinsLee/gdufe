package com.brin.gdufe.api

import com.brin.gdufe.AppConfig
import com.brin.gdufe.bean.BasicInfo
import com.brin.gdufe.bean.Schedule
import com.brin.gdufe.bean.Score
import com.brin.gdufe.model.ApiUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class JwApiFactory : ApiUtils(){

    val MERGE_SCHEDULE = 0
    val SPLIT_SCHEDULE = 1

    companion object {

        fun getInstence() : JwApiFactory{

         return  SingletonHolder.INSTANCE
        }
    }


    private object SingletonHolder {
        internal val INSTANCE = JwApiFactory()
    }

    //stu_time格式：2013-2014-1，或者为空字符串表示查询整个大学的成绩，minor查主修为0，辅修为1
    fun getScore(stu_time: String, minor: Int, sub: Observer<List<Score>>) {
        ApiUtils.getApi(AppConfig.jwPwd).create(JwApi::class.java).getScore(stu_time, minor)
                .map(HttpResultFunc<List<Score>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub)
    }

    //获取课表信息
    fun getSchedule(stu_time: String, split: Int, sub: Observer<List<Schedule>>) {
        ApiUtils.getApi(AppConfig.jwPwd).create(JwApi::class.java).getSchedule(stu_time, split)
                .map(HttpResultFunc<List<Schedule>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub)
    }

    //获取个人基本信息
    fun getBasicInfo(sub: Observer<BasicInfo>) {
        ApiUtils.getApi(AppConfig.jwPwd).create(JwApi::class.java).getBasicInfo()
                .map(HttpResultFunc<BasicInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub)
    }
}