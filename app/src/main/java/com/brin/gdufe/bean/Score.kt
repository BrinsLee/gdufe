package com.brin.gdufe.bean

class Score{

    var time:String? = null
    private var name: String? = null
    private var score: Int = 0
    private var credit: Double = 0.toDouble()
    private var classCode: String? = null  //课程编号
    private var dailyScore: Int = 0   //平时成绩
    private var expScore: Int = 0     //实验成绩
    private var paperScore: Int = 0   //卷面期末分
}