package com.brin.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.util.Log
import com.brin.gdufe.AppConfig
import com.brin.gdufe.model.UserInfo
import org.spongycastle.util.encoders.Hex
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class FileUtil {

    companion object {

        private val DATA_FILE_DIR = "mygdufe"
        private val AVATAR_FILE_NAME = "avatar.png"

        private val SP_FILE = "account_data"
        private val SP_SNO = "sno"
        private val SP_IDS_PSSWORD = "idsPwd"
        private val SP_JW_PSSWORD = "jwPwd"
        private val IV = "iv"
        private val ENCRYPT_KEY = "encrypt_key"

        private val SP_WEEK_FILE = "course_week"
        private val SP_CURRENT_WEEK = "currentWeek"
        private val SP_CURRENT_DAY = "currentDay"
        val SP_WEEK_NOT_SET = -1


        fun ByteArray.toHexString() = joinToString("") { String.format("%02x", it) }


        fun getStoredAccountAndSetApp(context: Context): Boolean {
            val userAccount = getStoredAccount(context) ?: return false
            AppConfig.sno = userAccount.sno
            AppConfig.idsPwd = userAccount.idsPwd
            AppConfig.jwPwd = userAccount.jwPwd
            Log.d("sno===",userAccount.sno)
            Log.d("idspw===",userAccount.idsPwd)
            Log.d("jwpw===",userAccount.jwPwd)
            Log.d("sno",AppConfig.sno)
            Log.d("idspw",AppConfig.idsPwd)
            Log.d("jwpw",AppConfig.jwPwd)
            Log.d("getStoredAccount","${!TextUtils.isEmpty(AppConfig.sno) && !TextUtils.isEmpty(AppConfig.idsPwd)}")
            return !(TextUtils.isEmpty(AppConfig.sno)) && !(TextUtils.isEmpty(AppConfig.idsPwd))
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun expireStoredAccount(context: Context) {
            val userAccount = UserInfo("", "", "")
            setStoredAccount(context, userAccount)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun setStoredAccount(context: Context, userAccount: UserInfo) {
            val edit = context.getSharedPreferences(SP_FILE, 0).edit()
            edit.putString(SP_SNO, userAccount.sno)
            var aes = AESUtils(context)
            var iv = aes.generateIV()
            edit.putString(IV, iv.toHexString())
            edit.putString(SP_IDS_PSSWORD, aes.encryptAccount(userAccount.idsPwd!!.toByteArray(), iv))
            edit.putString(SP_JW_PSSWORD, aes.encryptAccount(userAccount.jwPwd!!.toByteArray(), iv))
            edit.commit()
            edit.apply()
        }

        fun loadAvatarBitmap(context: Context): Bitmap? {
            val appDir = File(getDiskCacheDir(context))
            val file = File(appDir, AVATAR_FILE_NAME)
            return if (!file.exists()) {
                null
            } else BitmapFactory.decodeFile(file.getPath())
        }


        private fun getDiskCacheDir(context: Context): String {
            var cachePath: String? = null
            cachePath = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
                try {
                    context.externalCacheDir!!.path//SDCard/Android/data/应用包名/cache/目录
                } catch (e: NullPointerException) {
                    context.cacheDir.path    //data/data/<application package>/cache
                }

            } else {
                context.cacheDir.path    //data/data/<application package>/cache
            }
            return cachePath
        }


        fun saveAvatarImage(context: Context, bmp: Bitmap) {
            val fileName = AVATAR_FILE_NAME
            saveImageFile(context, bmp, fileName, true)
        }

        /**
         * 保存图片文件，返回图片的绝对路径
         * @param context
         * @param bmp
         * @param fileName
         * @return
         */
        fun saveImageFile(context: Context, bmp: Bitmap, fileName: String, isTemp: Boolean): String {
            var appDir = File(getDiskCacheDir(context))
            if (!isTemp) {
                appDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            }
            if (!appDir.exists()) {
                appDir.mkdir()
            }
            val file = File(appDir, fileName)
            try {
                val fos = FileOutputStream(file)
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return file.absolutePath
        }

        fun getStoredAccount(context: Context): UserInfo? {
            val userAccount = UserInfo()
            val sp = context.getSharedPreferences(SP_FILE, 0)
            var iv = sp.getString(IV, "")
            var encrypt_key = sp.getString(ENCRYPT_KEY, "")
            if (iv.equals("")&&encrypt_key.equals("")){
                return null
            }
            var aes = AESUtils(context)
            userAccount.sno = (sp.getString(SP_SNO, null))
            userAccount.idsPwd = (aes.decryptAccount(sp.getString(SP_IDS_PSSWORD, ""), encrypt_key, Hex.decode(iv)))
            userAccount.jwPwd = (aes.decryptAccount(sp.getString(SP_JW_PSSWORD, ""), encrypt_key, Hex.decode(iv)))
            return userAccount
        }

        fun getCurrentWeek(context: Context): String {
            val savedWeek = getSavedWeek(context)
            if (savedWeek == "" + SP_WEEK_NOT_SET) {
                return "" + SP_WEEK_NOT_SET
            }
            val today = TimeUtils.getDateStringWithFormat("yyyy-MM-dd")
            val savedDay = getSavedCurrentDay(context)
            val weekDiff = TimeUtils.weekBetweenTwoDateString(savedDay, today, "yyyy-MM-dd")
            var current = java.lang.Long.parseLong(savedWeek) + weekDiff
            if (current <= 0 || current > 16) {
                current = 1
            }
            return current.toString() + ""
        }

        private fun getSavedWeek(context: Context): String? {
            val sp = context.getSharedPreferences(SP_WEEK_FILE, 0)
            return sp.getString(SP_CURRENT_WEEK, "" + SP_WEEK_NOT_SET)
        }

        private fun getSavedCurrentDay(context: Context): String? {
            val sp = context.getSharedPreferences(SP_WEEK_FILE, 0)
            val today = TimeUtils.getDateStringWithFormat("yyyy-MM-dd")
            return sp.getString(SP_CURRENT_DAY, today)
        }

    }

}