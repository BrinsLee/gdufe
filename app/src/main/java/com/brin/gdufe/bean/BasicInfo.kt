package com.brin.gdufe.bean

import android.arch.persistence.room.*
import android.support.annotation.NonNull
import com.brin.util.Converters

@Entity(tableName = "basicinfo")
@TypeConverters(Converters::class)
class BasicInfo constructor(

        @ColumnInfo(name="id")
        @PrimaryKey(autoGenerate = true)
        @NonNull
        var mId : Int


){
     @Ignore
     constructor():this(0)

     @ColumnInfo(name="dept")
     var department: String? = null
     @ColumnInfo(name="major")
     var major: String? = null
     @ColumnInfo(name="classroom")
     var classroom: String? = null
     @ColumnInfo(name="name") //跟getClass重名了
     var name: String? = null

     @ColumnInfo(name="sex")
     var sex: String? = null
     @ColumnInfo(name="namePy")
     var namePy: String? = null
     @ColumnInfo(name="birthday")
     var birthday: String? = null
     @ColumnInfo(name="party")
     var party: String? = null
     @ColumnInfo(name="nation")
     var nation: String? = null
     @ColumnInfo(name="education")
     var education: String? = null

}