package com.brin.gdufe.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.Toast
import com.brin.gdufe.AppConfig
import com.brin.gdufe.BuildConfig
import com.brin.gdufe.R
import com.brin.gdufe.bean.BasicInfo
import com.brin.gdufe.customView.Adapter.ListViewAdapter
import com.brin.gdufe.customView.ListItem
import com.brin.gdufe.database.GdufeDatabaseHelper
import com.brin.util.BitmapUtil
import kotlinx.android.synthetic.main.mefragment.*
import java.util.ArrayList
import kotlin.math.log

class meFragment : Fragment() {

    lateinit var adapter: ListAdapter
    private lateinit var list: ArrayList<Any>
    internal var content = arrayOf("饭卡余额", "反馈", "常见问题", "版本")
    internal var iv_ser = intArrayOf(R.drawable.ic_right, R.drawable.ic_right, R.drawable.ic_right)
    internal var iv_Icon = intArrayOf(R.drawable.ic_card24dp,R.drawable.ic_email24dp,R.drawable.ic_faq_24dp,R.drawable.ic_info24dp)
    var InfoList : ArrayList<BasicInfo>? = null
    companion object {

        @JvmStatic
        fun newInstance(): meFragment {
            return meFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.mefragment, container, false)
        list = getData(content, arrayOf("", ""))
        adapter = ListViewAdapter(list,activity!!)
        return view
    }

    private fun getData(Item: Array<String>, title: Array<String>): ArrayList<Any> {

        val list = ArrayList<Any>()
        list.add(title[0])
        list.add(ListItem(Item[0], iv_ser[0],iv_Icon[0]))//饭卡
        list.add(title[1])
        list.add(ListItem(Item[1], iv_ser[1],iv_Icon[1]))//反馈
        list.add(ListItem(Item[2], iv_ser[2],iv_Icon[2]))//常见问题
        list.add(ListItem(Item[3], iv_Icon[3],BuildConfig.VERSION_NAME))//版本
        return list
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (InfoList == null) {
            InfoList = GdufeDatabaseHelper.getInstance(activity!!).getBasicInfo()
        }
        var basicInfo = InfoList!![0]
        name.text = basicInfo.name
        snumber.text = AppConfig.sno
        user_class.text = basicInfo.classroom
        iv_icon.setImageBitmap(BitmapUtil.getBitmap(180,basicInfo.name!!))

        list_account.adapter = adapter

        list_account.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            when(position){

                1 -> {Toast.makeText(activity,"消费记录功能正在实现中...",Toast.LENGTH_SHORT).show() }
                3 -> {}
                4 -> {}
            }
        }
    }
}