package com.brin.gdufe.fragment

import android.content.Context
import android.support.v4.app.Fragment
import com.brin.gdufe.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayout
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.brin.gdufe.R.color.colorPrimary
import com.brin.gdufe.api.JwApiFactory
import com.brin.gdufe.bean.Schedule
import com.brin.gdufe.database.GdufeDatabaseHelper
import com.brin.util.Array2D
import com.brin.util.CalcUtils
import com.brin.util.FileUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.frgment_course.*
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList


class courseFragment : Fragment() {



    private val factory = JwApiFactory.getInstence()
    var ScheduleList : ArrayList<Schedule>? = null
    private val maxDifferentSchedule = 42
    var currentWeek : Int? = null
    var colorStr = arrayOfNulls<String>(maxDifferentSchedule)
    var twoDArray = Array2D<String>(8,13)


    companion object {

        @JvmStatic
        fun newInstance(): courseFragment {
            return courseFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.frgment_course, container, false)
        initViewByDb()
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(tool)
        (activity as AppCompatActivity).supportActionBar!!.title = ""


    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        activity?.menuInflater!!.inflate(R.menu.toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.week -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewByDb() {

         currentWeek = Integer.parseInt(FileUtil.getCurrentWeek(activity!!))
        if (ScheduleList == null){
            ScheduleList = GdufeDatabaseHelper.getInstance(activity!!).getSchedule()
        }
        if (ScheduleList?.size == 0){

            //无数据
            realQuerySchedule("")

        }
    }

 private fun scheduleView (data : Array2D<String?>){


     var data1 = data
     var duration : Int?
        var color = arrayOf(resources.getColor(colorPrimary,null)
                ,resources.getColor(R.color.oringe,null)
                ,resources.getColor(R.color.yellow,null)
                ,resources.getColor(R.color.blue,null)
                ,resources.getColor(R.color.green,null)
                ,resources.getColor(R.color.red,null))

        //课表总字符串
        var schedulesStirng = ""
        //课程种类计数
        var scheduleCount = 0
        val scheduleToColor = JSONObject()
        if (null != schedule_grid) {
//            schedule_grid?.removeViews(18, schedule_grid.childCount - 18)
            val layoutInflater = LayoutInflater.from(context)
            //确定每一项子view的宽度和高度，如果不进行这一步，内容将显示不正确
            val wm = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            var outMetrics : DisplayMetrics = DisplayMetrics()
            wm.getDefaultDisplay().getMetrics(outMetrics)
            val width = outMetrics.widthPixels
            val height = outMetrics.heightPixels
            val item_width = width / 8
            val item_height = height / 6
            //填充子view
            for (columnSpec in 1..6) {  //data.length:行数， data[0].length: 0行的的列数
                for (rowSpec in 1..12) {
                    //此LinearLayout为子View，只包含一个TextView
                    val view = layoutInflater.inflate(R.layout.all_schedule_item_layout, null) as LinearLayout
                    val item = view.findViewById(R.id.schedule_content) as TextView
                    val content = data[columnSpec , rowSpec]?.trim()
                    //判断课表内容是否为空，若为空，则不填充子View
                    if (content != null) {
                        item.text = content
                        val position = content?.indexOf("\n")
                        val schedule = content?.substring(0, position!! - 1)
                       /* var start = content?.split("\n")[3].split(",")[0].trim().toInt()
                        var end = content?.split("\n")[3].split(",")[1].trim().toInt()
                        duration = end - start*/

                        //这里进行的是相同课程同背景色处理
                        try {
                            //判断课表名称里是否含有新进来的课
                            if (!schedulesStirng.contains(schedule!!)) {
                                schedulesStirng += schedule
                                scheduleToColor.put(schedule, scheduleCount % 7)
                                view.setBackgroundColor(color[scheduleCount % 7])
                                scheduleCount++
                            } else {
                                view.setBackgroundColor(color[scheduleToColor.getInt(schedule)])
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }else
                        continue
                    val param = GridLayout.LayoutParams()
                    param.columnSpec = GridLayout.spec(columnSpec, 1)
                    param.rowSpec = GridLayout.spec(rowSpec,2)
                    param.setGravity(Gravity.FILL)
                    param.setMargins(1, 1, 1, 1)
                    param.width = item_width - 5
                    param.height = item_height
                    schedule_grid.addView(view, param)
                }
            }
        }
    }

    private fun realQuerySchedule(studyTime: String) {
        factory.getSchedule(studyTime, 0, object : Observer<List<Schedule>> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(value: List<Schedule>) {
                if (value.size == 0) {
                    Toast.makeText(activity, "没有课", Toast.LENGTH_SHORT).show()
                    return
                }
                Log.d("size===","${value.size}")
                for(schedule : Schedule in value){
                    Log.d("name===",schedule.name)
                    Log.d("start===","${schedule.startSec}")
                    Log.d("end===","${schedule.endSec}")
                    Log.d("string===",schedule.toString())
                }
                setScheduleData(value , currentWeek!!)


            }

            override fun onError(e: Throwable) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            }

            override fun onComplete() {}
        })
    }

    fun setScheduleData(list: List<Schedule>, currentWeek: Int) {
        var mCurrentWeek = currentWeek
        if (mCurrentWeek == -1)
            mCurrentWeek = 1
        Log.d("currentWeek","$currentWeek")
        val size = list.size
        //单双周不同课或者同课不同地点的情况，去掉不是本周的那个
        //{"name":"电子商务及Web开发","teacher":"张婧炜讲师（高校）","period":"3,5,7,9,11,13,14,15,16(周)","location":"拓新楼(SS1)203","dayInWeek":1,"startSec":1,"endSec":2},
        //{"name":"电子商务及Web开发","teacher":"张婧炜讲师（高校）","period":"1,2,4,6,8,10,12(周)","location":"拓新楼(SS1)320","dayInWeek":1,"startSec":1,"endSec":2},
        for (i in 0 until size) {
            Log.d("size++","${size}")
            val item = list[i]
            var hasSame = false
            for (j in 0 until size) {
                if (i == j) continue
                val sc = list[j]
                if (item.dayInWeek === sc.dayInWeek
                        && item.endSec === sc.endSec
                        && item.startSec === sc.startSec) {
                    hasSame = true
                }
            }
            /*if (hasSame && !CalcUtils.isCurrentWeek(item.period, mCurrentWeek)) {//有重且当前这个不是当周的，则不加
            } else {*/
                twoDArray.set(item.dayInWeek!!,item.startSec!!,item.toString())
                Log.d("" +
                        "++","${item.dayInWeek}")
                Log.d("startSec++","${item.startSec}")
        }
        scheduleView(twoDArray)


    }
}