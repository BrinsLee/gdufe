package com.brin.gdufe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.brin.gdufe.api.JwApiFactory
import com.brin.gdufe.bean.BasicInfo
import com.brin.util.DialogUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import java.util.zip.Inflater

class LoginActivity : AppCompatActivity() {

    private var dialogView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        text_diff_jw.setOnClickListener {
            etJwPassword.visibility = View.VISIBLE
        }

        comfirm.setOnClickListener {

            val sno = ed_username.text.toString().trim()
            val pwd = ed_password.text.toString().trim()
            val jwPwd = ed_jwpassword.getText().toString().trim()

            if (sno.length == 0|| pwd.length == 0
                    || (etPasswordLayout.visibility == View.VISIBLE && ed_jwpassword.length() == 0)){
                Toast.makeText(this@LoginActivity , getString(R.string.info_incomplete),Toast.LENGTH_SHORT).show()

            }
            AppConfig.sno = sno
            AppConfig.idsPwd = pwd
            AppConfig.jwPwd = pwd

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

                override fun onNext(t: BasicInfo) {

                    Log.d("name",t.name)
                    Log.d("birthday",t.birthday)
                    Log.d("classroom",t.classroom)
                    Log.d("department",t.department)
                    Log.d("major",t.major)

                    Toast.makeText(this@LoginActivity, "${t.name}", Toast.LENGTH_SHORT).show()

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
