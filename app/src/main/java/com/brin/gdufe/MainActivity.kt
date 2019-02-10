package com.brin.gdufe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import com.brin.gdufe.fragment.SuperFragment
import com.brin.util.FileUtil

class MainActivity : AppCompatActivity() {

    private var mExitTime: Long = 0
    lateinit var superFragment: SuperFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("sno_main",AppConfig.sno)
        Log.d("idspw_main",AppConfig.idsPwd)
        Log.d("jwpw_main",AppConfig.jwPwd)
        if (!FileUtil.getStoredAccountAndSetApp(this) || TextUtils.isEmpty(AppConfig.sno) || TextUtils.isEmpty(AppConfig.idsPwd)) {
            startActivity(Intent(this, LoginActivity::class.java))
            this.finish()
            return
        }
        setFragment()
    }

    private fun setFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        if (!::superFragment.isInitialized)
            superFragment = SuperFragment.newInstance("fragment")
        transaction.replace(R.id.frame_content, superFragment).commit()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            //如果俩次按的时候的时间不小于2秒，就执行这个，否则就会退出程序
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toast.makeText(applicationContext, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show()
                mExitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
