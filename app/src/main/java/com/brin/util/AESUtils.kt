package com.brin.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import com.brin.gdufe.AppConfig
import org.spongycastle.util.encoders.Hex
import java.io.UnsupportedEncodingException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.HashMap
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

class AESUtils (var mContext : Context){


        private var dcipher: Cipher? = null
        private val HEX = "0123456789ABCDEF"
        private val ENCRYPT_KEY = "encrypt_key"
        private val SP_FILE = "account_data"




    fun ByteArray.toHexString() = joinToString("") { String.format("%02x", it) }
        /**
         * generate a 32 byte salt for the aes encrypt
         *
         * @return byte [ ]
         */
        fun generateSalt(): ByteArray {
            //32byte
            val salt = ByteArray(32)
            val random = SecureRandom()
            random.nextBytes(salt)
            return salt
        }

        /**
         * 生成AES 256 bit 密钥，转为字符串 并返回该字符串
         *
         * @return 生成的AES 256 密钥字符串
         */
        @RequiresApi(api = Build.VERSION_CODES.O)
        fun encryptKeyGenerator(): String? {
            var key: String? = null
            val edit = mContext.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE).edit()
            try {
                val keyGen = KeyGenerator.getInstance("AES")
                keyGen.init(256)
                val secretKey = keyGen.generateKey()
                key = String(secretKey.encoded, charset("ISO-8859-1"))
                Log.d("key++++", key)
                edit.putString(ENCRYPT_KEY,key).commit()
//                Log.d("encryptKeyString", secretKey.encoded.size.toString() + "++++++the string is " + Hex.toHexString(secretKey.encoded))
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return key
        }

        /**
         * generate a 32 byte salt for the aes encrypt
         *
         * @return byte [ ]
         */
        fun generateIV(): ByteArray {
            //32byte
            val iv = ByteArray(16)
            val random = SecureRandom()
            random.nextBytes(iv)
            return iv
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun encryptAccount(accountInfo: ByteArray,iv : ByteArray): String? {

            var key = mContext.getSharedPreferences(SP_FILE,Context.MODE_PRIVATE).getString(ENCRYPT_KEY,"")
            var keybyte : ByteArray
            if (key .equals("")){
                keybyte = encryptKeyGenerator()!!.toByteArray(charset("ISO-8859-1"))

            }else{
                keybyte = key.toByteArray(charset("ISO-8859-1"))
            }
            try {
                dcipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
                val ivspec = IvParameterSpec(iv)
                val encryptKey = SecretKeySpec(keybyte, 0, keybyte!!.size, "AES")
                dcipher?.init(Cipher.ENCRYPT_MODE, encryptKey, ivspec)
                val utf8EncryptedData = dcipher?.doFinal(accountInfo)
//                Log.d("accountInfo Log data", Hex.toHexString(accountInfo))
//                Log.d("结果Hex", Hex.toHexString(utf8EncryptedData).length() + "")
                return utf8EncryptedData?.toHexString()
            } catch (e: InvalidAlgorithmParameterException) {
                e.printStackTrace()
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
            } catch (e: BadPaddingException) {
                e.printStackTrace()
            } catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            } catch (e: NoSuchPaddingException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            return null
        }

    @Throws(Exception::class)
    fun decryptAccount(encrypted: String, encrypt_key: String, iv: ByteArray): String? {

        // 秘钥
        //        byte[] enCodeFormat = getencrykeybyte();
        // 创建AES秘钥
        var encrypted_byte = Hex.decode(encrypted)
        dcipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        var key = mContext.getSharedPreferences(SP_FILE,Context.MODE_PRIVATE).getString(ENCRYPT_KEY,"")
        var keybyte = key.toByteArray(charset("ISO-8859-1"))
        val encryptKey = SecretKeySpec(keybyte, 0, keybyte.size, "AES")
        val ivspec = IvParameterSpec(iv)
        // 创建密码器
        // 初始化解密器
        dcipher?.init(Cipher.DECRYPT_MODE, encryptKey, ivspec)
        // 解密
        var utf8DecryptedData: ByteArray?
        try {
            utf8DecryptedData = dcipher?.doFinal(encrypted_byte)
            return String(utf8DecryptedData!!)

//            Log.d("accountInfo Log", Hex.toHexString(utf8DecryptedData))
        } catch (e: Exception) {
            utf8DecryptedData = null
            Log.d("解密失败", "解密失败")
        }

        return null
    }

}