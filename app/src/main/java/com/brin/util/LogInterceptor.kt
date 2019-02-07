package com.brin.util

import okhttp3.logging.HttpLoggingInterceptor

class LogInterceptor {


    companion object {
        fun getLoggingInterceptor() : HttpLoggingInterceptor{
            return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    }

}