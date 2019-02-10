package com.brin.gdufe.model

/*
class UserInfo {

    var sno :String? = null
    var idsPwd : String? = null
    var jwPwd : String? = null
    init {

    }
    constructor()

}*/
class UserInfo {
    var sno: String? = null
    var idsPwd: String? = null
    var jwPwd: String? = null

    constructor() {}

    constructor(sno: String, idsPwd: String, jwPwd: String) {
        this.idsPwd = idsPwd
        this.sno = sno
        this.jwPwd = jwPwd
    }
}
