package com.brin.gdufe.database

import android.arch.persistence.room.Room
import android.content.Context
import com.brin.gdufe.bean.BasicInfo
import com.brin.gdufe.bean.Schedule

class GdufeDatabaseHelper (context: Context){

    val appDataBase = Room.databaseBuilder(context, GdufeDatabase::class.java,
            "basicinfo").allowMainThreadQueries().build()
    val appDataBase_Schedule = Room.databaseBuilder(context, GdufeDatabase::class.java
            ,"schedule").allowMainThreadQueries().build()

    companion object {
        @Volatile
        var INSTANCE: GdufeDatabaseHelper? = null

        fun getInstance(context: Context): GdufeDatabaseHelper {
            if (INSTANCE == null) {
                synchronized(GdufeDatabaseHelper::class) {
                    if (INSTANCE == null) {
                        INSTANCE = GdufeDatabaseHelper(context.applicationContext)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    fun insert(basicinfo:BasicInfo){

        appDataBase.dao().addBasicInfo(basicinfo)

    }

    fun getBasicInfo () :ArrayList<BasicInfo>{

        return appDataBase.dao().getBasicInfo() as ArrayList<BasicInfo>
    }

    fun insert(schedule : Schedule){
        appDataBase_Schedule.dao().addSchedule(schedule)
    }

    fun getSchedule() : ArrayList<Schedule>{

        return appDataBase_Schedule.dao().getSchedule() as ArrayList<Schedule>
    }
}