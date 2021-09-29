package com.example.canvas.utility

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FILTER_BITMAP_FLAG
import android.graphics.Xfermode
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import androidx.annotation.Nullable

object Util {

    fun drawObject(
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        colorString: String = "#000000",
        shape: Shape,
        canvas: Canvas
    ) {
        val shapeDrawable = ShapeDrawable()
        shapeDrawable.apply {
            setBounds(x, y, width, height)
            paint.color = Color.parseColor(colorString)
            setShape(shape)
            draw(canvas)
        }
    }

    fun paintObject(
        flag: Int = FILTER_BITMAP_FLAG,
        isAntiAlias: Boolean = true,
        isDither: Boolean = true,
        color: Int,
        style: Paint.Style = Paint.Style.FILL,
        strokeJoin: Paint.Join = Paint.Join.MITER,
        strokeCap: Paint.Cap = Paint.Cap.BUTT,
        strokeWidth: Float = 1.0f,
        @Nullable xfermode: Xfermode? = null
    ) : Paint {
        val paint = Paint(flag)
        paint.apply {
            setAntiAlias(isAntiAlias)
            setDither(isDither)
            setColor(color)
            setStyle(style)
            setStrokeJoin(strokeJoin)
            setStrokeCap(strokeCap)
            setStrokeWidth(strokeWidth)
            setXfermode(xfermode)
        }

        return paint
    }
}
