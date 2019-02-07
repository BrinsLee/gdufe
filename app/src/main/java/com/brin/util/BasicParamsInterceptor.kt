package com.brin.util

import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.util.Map

class BasicParamsInterceptor : Interceptor{

    var queryParamsMap : HashMap<String,String>  = HashMap()
    var paramsMap : HashMap<String,String>  = HashMap()
    var headerParamsMap : HashMap<String,String>  = HashMap()
    var headerLinesList : MutableList<String> = ArrayList()

    override fun intercept(chain: Interceptor.Chain): Response {

        var originRequest = chain.request()
        var requestBuilder = originRequest.newBuilder()
        var headerBuilder : Headers.Builder = originRequest.headers().newBuilder()

        if (headerParamsMap.size > 0) {
            val iterator = headerParamsMap.entries.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next() as Map.Entry<*, *>
                headerBuilder.add(entry.key as String, entry.value as String)
            }
        }

        if (headerLinesList.size > 0) {
            for (line in headerLinesList) {
                headerBuilder.add(line)
            }
        }

        requestBuilder.headers(headerBuilder.build())
        // process header params end


        // process queryParams inject whatever it's GET or POST
        if (queryParamsMap.size > 0) {
            injectParamsIntoUrl(originRequest, requestBuilder, queryParamsMap)
        }
        // process header params end


        // process post body inject
        if (originRequest.method() == "POST") {// && request.body().contentType().subtype().equals("x-www-form-urlencoded")) {
            val formBodyBuilder = FormBody.Builder()
            if (paramsMap.size > 0) {
                val iterator = paramsMap.entries.iterator()
                while (iterator.hasNext()) {
                    val entry = iterator.next() as Map.Entry<*, *>
                    formBodyBuilder.add(entry.key as String, entry.value as String)
                }
            }
            val formBody = formBodyBuilder.build()
            var postBodyString = bodyToString(originRequest.body())
            postBodyString += (if (postBodyString.length > 0) "&" else "") + bodyToString(formBody)
            requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString))
        } else {    // can't inject into body, then inject into url
            injectParamsIntoUrl(originRequest, requestBuilder, paramsMap)
        }

        originRequest = requestBuilder.build()
        return chain.proceed(originRequest)

    }

    private fun injectParamsIntoUrl(request: Request, requestBuilder: Request.Builder, paramsMap: kotlin.collections.Map<String, String>) {
        val httpUrlBuilder = request.url().newBuilder()
        if (paramsMap.size > 0) {
            val iterator = paramsMap.entries.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next() as Map.Entry<*, *>
                httpUrlBuilder.addQueryParameter(entry.key as String, entry.value as String)
            }
        }

        requestBuilder.url(httpUrlBuilder.build())
    }

    private fun bodyToString(request: RequestBody?): String {
        try {
            val buffer = Buffer()
            if (request != null)
                request.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }

    }

     class Builder {
        var interceptor : BasicParamsInterceptor = BasicParamsInterceptor()

        fun addParam(key : String, value : String):Builder{
            interceptor.paramsMap.put(key,value)
            return this
        }

        fun addParamsMap(paramsMap: kotlin.collections.Map<String, String>): Builder {
            interceptor.paramsMap.putAll(paramsMap)
            return this
        }

        fun addHeaderParam(key: String, value: String): Builder {
            interceptor.headerParamsMap[key] = value
            return this
        }

        fun addHeaderParamsMap(headerParamsMap: kotlin.collections.Map<String, String>): Builder {
            interceptor.headerParamsMap.putAll(headerParamsMap)
            return this
        }

        fun addHeaderLine(headerLine: String): Builder {
            val index = headerLine.indexOf(":")
            if (index == -1) {
                throw IllegalArgumentException("Unexpected header: $headerLine")
            }
            interceptor.headerLinesList.add(headerLine)
            return this
        }

        fun addHeaderLinesList(headerLinesList: List<String>): Builder {
            for (headerLine in headerLinesList) {
                val index = headerLine.indexOf(":")
                if (index == -1) {
                    throw IllegalArgumentException("Unexpected header: $headerLine")
                }
                interceptor.headerLinesList.add(headerLine)
            }
            return this
        }

        fun addQueryParam(key: String, value: String): Builder {
            interceptor.queryParamsMap[key] = value
            return this
        }

        fun addQueryParamsMap(queryParamsMap: kotlin.collections.Map<String, String>): Builder {
            interceptor.queryParamsMap.putAll(queryParamsMap)
            return this
        }

        fun build(): BasicParamsInterceptor {
            return interceptor
        }

    }

}