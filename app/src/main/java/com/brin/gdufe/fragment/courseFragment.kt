package com.brin.gdufe.fragment

import android.content.Context
import android.support.v4.app.Fragment
import com.brin.gdufe.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayout
import android.util.Log
import android.view.*
import android.widget.Toast
import com.brin.gdufe.api.JwApiFactory
import com.brin.gdufe.bean.Schedule
import com.brin.gdufe.database.GdufeDatabaseHelper
import com.brin.util.FileUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.frgment_course.*
import java.util.ArrayList


class courseFragment : Fragment() {



    var gridLayout : GridLayout? = null
    private val factory = JwApiFactory.getInstence()
    var ScheduleList : ArrayList<Schedule>? = null
    private val maxDifferentSchedule = 42
    var colorStr = arrayOfNulls<String>(maxDifferentSchedule)
    var twoDArray = Array(13, {IntArray(8)})


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

        val currentWeek = Integer.parseInt(FileUtil.getCurrentWeek(activity!!))
        if (ScheduleList == null){
            ScheduleList = GdufeDatabaseHelper.getInstance(activity!!).getSchedule()
        }
        if (ScheduleList?.size == 0){

            //无数据
            realQuerySchedule("")

        }
    }

   /* private fun scheduleView (data : Array<String>){

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
        if (null != gridLayout) {
            //18为边栏固有子view数目，清空后添加子view
            gridLayout?.removeViews(18, gridLayout!!.childCount - 18)
            val layoutInflater = LayoutInflater.from(context)
            //确定每一项子view的宽度和高度，如果不进行这一步，内容将显示不正确
            val wm = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//            val display = wm.defaultDisplay
//            val width = display.width
//            val height = display.height
            var outMetrics : DisplayMetrics = DisplayMetrics()
            wm.getDefaultDisplay().getMetrics(outMetrics)
            val width = outMetrics.widthPixels
            val height = outMetrics.heightPixels
            val item_width = width / 8
            val item_height = height / 6
            //填充子view
            for (columnSpec in 1..data.size) {
                for (rowSpec in 1..data[columnSpec - 1].length) {
                    //此LinearLayout为子View，只包含一个TextView
                    val view = layoutInflater.inflate(R.layout.frgment_course, null) as LinearLayout
                    val item = view.findViewById(R.id.schedule_content) as TextView
                    val content = data[columnSpec - 1][rowSpec - 1].trim
                    //判断课表内容是否为空，若为空，则不填充子View
                    if (content != "") {
                        item.setText(content)
                        val position = content.indexOf("@")
                        val schedule = content.substring(0, position - 1)
                        //这里进行的是相同课程同背景色处理
                        try {
                            //判断课表名称里是否含有新进来的课
                            if (!schedulesStirng.contains(schedule)) {
                                schedulesStirng = schedulesStirng + schedule
                                scheduleToColor.put(schedule, scheduleCount % 7)
                                view.setBackgroundColor(color[scheduleCount % 7])
                                scheduleCount++
                            } else {
                                view.setBackgroundColor(color[scheduleToColor.getInt(schedule)])
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }
                    val param = GridLayout.LayoutParams()
                    param.columnSpec = GridLayout.spec(columnSpec, 1)
                    param.rowSpec = GridLayout.spec(rowSpec * 2 - 1, 2)
                    param.setGravity(Gravity.FILL)
                    param.setMargins(1, 1, 1, 1)
                    param.width = item_width - 5
                    param.height = item_height
                    gridLayout?.addView(view, param)
                }
            }
        }
    }*/

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
                }


            }

            override fun onError(e: Throwable) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            }

            override fun onComplete() {}
        })
    }
}