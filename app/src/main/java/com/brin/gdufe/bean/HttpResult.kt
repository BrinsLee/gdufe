package com.brin.gdufe.bean

class HttpResult<T> {
    var code: Int = 0
    var data: T? = null
    var msg: String? = null

    val isSuccess: Boolean
        get() = code == 0
}