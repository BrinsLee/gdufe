package com.brin.gdufe.api

import com.brin.gdufe.AppConfig
import com.brin.gdufe.model.ApiUtils
import com.brin.gdufe.model.CardInfo
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CardApiFactory constructor():ApiUtils(){

    companion object {

        fun getInstence() : CardApiFactory{

            return  SingletonHolder.INSTANCE
        }
    }


    private object SingletonHolder {
        internal val INSTANCE = CardApiFactory()
    }

    fun getCurrentCash(sub: Observer<CardInfo>) {
        ApiUtils.getApi(AppConfig.idsPwd).create(CardApi::class.java).getCurrentCash()
                .map(HttpResultFunc<CardInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub)
    }
}