package com.brin.gdufe

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.brin.gdufe.api.JwApiFactory
import com.brin.gdufe.bean.BasicInfo
import com.brin.gdufe.database.GdufeDatabaseHelper
import com.brin.gdufe.model.UserInfo
import com.brin.util.DialogUtil
import com.brin.util.FileUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var dialogView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        text_diff_jw.setOnClickListener {
            if (etJwPassword.visibility == View.GONE)
                etJwPassword.visibility = View.VISIBLE
            else etJwPassword.visibility = View.GONE

        }

        comfirm.setOnClickListener {

            val sno = ed_username.text.toString().trim()
            val pwd = ed_password.text.toString().trim()
            val jwPwd = ed_jwpassword.text.toString().trim()

            if (sno.length == 0|| pwd.length == 0
                    || (etJwPassword.visibility == View.VISIBLE && jwPwd.length == 0)){
                Toast.makeText(this@LoginActivity , getString(R.string.info_incomplete),Toast.LENGTH_SHORT).show()

            }
            AppConfig.sno = sno
            AppConfig.idsPwd = pwd
            AppConfig.jwPwd = pwd
            if (etJwPassword.visibility == View.VISIBLE) {
                AppConfig.jwPwd = jwPwd
            }

//            startLoadingProgess()
            var layoutInflater = LayoutInflater.from(this)

            dialogView = layoutInflater.inflate(R.layout.progressdialog,null)
            var progressBar = DialogUtil.createDialog(this@LoginActivity,true,dialogView)
            progressBar.show()
            var jwApiFactory = JwApiFactory.getInstence()
            jwApiFactory.getBasicInfo(object :Observer<BasicInfo> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onNext(t: BasicInfo) {

                    Log.d("name",t.name)
                    Log.d("birthday",t.birthday)
                    Log.d("classroom",t.classroom)
                    Log.d("department",t.department)
                    Log.d("major",t.major)
                    GdufeDatabaseHelper.getInstance(this@LoginActivity).insert(t)

//                    Toast.makeText(this@LoginActivity, "${t.name}", Toast.LENGTH_SHORT).show()
                    Log.d("login_idspwd",AppConfig.idsPwd)
                    FileUtil.setStoredAccount(this@LoginActivity, UserInfo(sno, AppConfig.idsPwd, AppConfig.jwPwd))
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    this@LoginActivity.finish()
                }

                override fun onError(e: Throwable) {

                    Toast.makeText(this@LoginActivity, getString(R.string.account_error), Toast.LENGTH_SHORT).show()
                    progressBar.dismiss()


                }


            })





        }
    }

    private fun startLoadingProgess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
