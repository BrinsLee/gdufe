package com.brin.gdufe.database

import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.brin.gdufe.bean.BasicInfo
import com.brin.gdufe.bean.Schedule

@android.arch.persistence.room.Dao
interface Dao {

    @Insert
    fun addBasicInfo (basicinfo : BasicInfo)

    @Query("select * from basicinfo")
    fun getBasicInfo() : List<BasicInfo>

    @Query("select * from basicinfo where id = :id")
    fun getBasicInfoViaId(id : Int) : List<BasicInfo>


    @Query("delete from basicinfo where id = :id")
    fun deleteViaId (id : Int)

    /*    @Query("update basicinfo set dept = :dept ,major = :major," +
            "classroom = :classroom , name = :name , sex = :sex , birthday = :birthday," +
            "party = :party , nation = :nation , education = :education")
    fun updateAll (dept : String,major : String,classroom : String,name : String
                   ,sex : String,birthday:String,party:String,nation:String,education:String) : List<BasicInfo>*/


    @Insert
    fun addSchedule (schedule : Schedule)

    @Query("select * from schedule")
    fun getSchedule() : List<Schedule>

    @Query("select * from schedule where id = :id")
    fun getScheduleViaId(id : Int) : List<Schedule>

    @Query("delete from schedule where id = :id")
    fun deleteScheduleViaId (id : Int)

}