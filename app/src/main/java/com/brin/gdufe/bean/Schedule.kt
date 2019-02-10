package com.brin.gdufe.bean

import android.arch.persistence.room.*
import android.support.annotation.NonNull
import com.brin.util.Converters

@Entity(tableName = "schedule")
@TypeConverters(Converters::class)
class Schedule constructor(

        @ColumnInfo(name="id")
        @PrimaryKey(autoGenerate = true)
        @NonNull
        var mId : Int
){

    @Ignore
    constructor():this(0)

    @Ignore
    constructor(name : String,teacher : String, period : String
                ,location : String , dayInWeek : Int , startSec :Int , endSec : Int):this(0){

        this.name = name
        this.teacher = teacher
        this.period = period
        this.location = location
        this.dayInWeek = dayInWeek
        this.startSec = startSec
        this.endSec = endSec
    }
    @ColumnInfo(name="name")
    var name: String? = null
    @ColumnInfo(name="teacher")
    var teacher: String? = null
    @ColumnInfo(name="period")
    var period: String? = null//周情况
    @ColumnInfo(name="location")
    var location: String? = null//地点
    @ColumnInfo(name="startSec")
    var startSec: Int? = null//开始小节
    @ColumnInfo(name="endSec")
    var endSec: Int? = null//结束
    @ColumnInfo(name="dayInWeek")
    var dayInWeek: Int? = null//结束


}