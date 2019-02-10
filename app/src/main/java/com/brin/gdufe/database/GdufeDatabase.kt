package com.brin.gdufe.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.brin.gdufe.bean.BasicInfo
import com.brin.gdufe.bean.Schedule

@Database(entities = arrayOf(BasicInfo::class , Schedule::class),version = 1,exportSchema = false)
abstract class GdufeDatabase : RoomDatabase() {

    abstract fun dao(): Dao
}