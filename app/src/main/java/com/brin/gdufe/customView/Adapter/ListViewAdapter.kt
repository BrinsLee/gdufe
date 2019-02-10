package com.brin.gdufe.customView.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.brin.gdufe.R
import com.brin.gdufe.customView.ListItem
import kotlinx.android.synthetic.*
import java.util.*
import java.util.zip.Inflater
import kotlin.collections.ArrayList

class ListViewAdapter (var list:ArrayList<Any>, mContext : Context): BaseAdapter(){

//    var list : ArrayList<Object>? = null
//    var inflater : LayoutInflater? = null
    private val ITEM_CONTENT = 0
    private val ITEM_HEADER = 1
    private var inflater : LayoutInflater = LayoutInflater.from(mContext)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var  view: View? = null
        if (convertView == null){
            when(getItemViewType(position)){
                ITEM_CONTENT -> {
                    view = inflater.inflate(R.layout.item_security,null)
                }
                ITEM_HEADER -> {
                    view = inflater.inflate(R.layout.titleview,null)
                }
            }

            when (getItemViewType(position)){
                ITEM_CONTENT -> {
                    var iv = view?.findViewById(R.id.iv_image) as ImageView
                    var tv_content = view?.findViewById(R.id.tv_title) as TextView
                    var iv_icon = view?.findViewById(R.id.iv_icon) as ImageView
                    iv.setImageResource((list[position] as ListItem).image)
                    tv_content.text = (list[position] as ListItem).content
                    iv_icon.setImageResource((list[position] as ListItem).icon)
                    Log.d("content",(list[position] as ListItem).content)

                    if (position == 5) {
                        Log.d("version data position", "" + position)
                        val version_id_tv = view?.findViewById(R.id.iv_text) as TextView
                        version_id_tv.text = (list[position] as ListItem).version_info
                        version_id_tv.visibility = View.VISIBLE
                    }
                }
                ITEM_HEADER -> {}

            }
        }else
            view = convertView

        return view!!
    }

    override fun getItem(position: Int): Any {

        return list.get(position)
    }

    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun getCount(): Int {

        return list.size
    }

    override fun getItemViewType(position: Int): Int {

        return if (list[position] is ListItem) {
            ITEM_CONTENT

        } else {
            ITEM_HEADER
        }
    }
}