package com.brin.util

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.Log
import org.spongycastle.util.encoders.Hex

class BitmapUtil {

    companion object {

        fun getBitmap(iconSize: Int, name: String): Bitmap {

            val bitmap = Bitmap.createBitmap(iconSize, iconSize, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val rect = RectF(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat())
            val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DEV_KERN_TEXT_FLAG)
            val paintbackground = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DEV_KERN_TEXT_FLAG)
            val combine = Hex.toHexString((name + "广财").toByteArray())
            Log.d("原先", name)
            Log.d("combine", combine)
            paintbackground.color = Color.parseColor("#" + combine.substring(3, 9))
            canvas.drawRect(rect, paintbackground)
            paint.color = Color.WHITE
            paint.strokeWidth = 3f
            paint.textSize = iconSize * 0.6f
            paint.textAlign = Paint.Align.CENTER
            val fontMetrics = paint.fontMetricsInt
            val baseline = (rect.bottom + rect.top - fontMetrics.bottom.toFloat() - fontMetrics.top.toFloat()) / 2
            canvas.drawText(name.substring(0, 1), rect.centerX(), baseline, paint)
            return bitmap
        }
    }

}