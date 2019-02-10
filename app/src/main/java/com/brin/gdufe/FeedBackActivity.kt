package com.brin.gdufe

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_feed_back.*

class FeedBackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)

        bt_confirm.setOnClickListener {

            if (ed_feedback.text.length == 0){
                Toast.makeText(this@FeedBackActivity,"随便写点呗",Toast.LENGTH_SHORT).show()
            }else {
                var data = Intent(Intent.ACTION_SENDTO)
                data.data = Uri.parse("mailto:747127514@qq.com")
                data.putExtra(Intent.EXTRA_SUBJECT, "广财助手用户反馈")
                data.putExtra(Intent.EXTRA_TEXT, ed_feedback.text.trim())
                startActivity(data)
                finish()
            }
        }


    }
}
