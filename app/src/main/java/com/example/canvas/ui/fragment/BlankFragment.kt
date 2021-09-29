package com.example.canvas.ui.fragment

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import com.example.canvas.R
import com.example.canvas.utility.Util

class BlankFragment : Fragment(R.layout.fragment_blank) {
    /* Basic Canvas */

    private lateinit var canvas_view: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        canvas_view = view.findViewById(R.id.iv)

        /* Bitmap https://developer.android.com/topic/performance/graphics
      *
      *  createBitmap(): returns a mutable bitmap with the specified width and height.
      */
        val bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        drawRectangle(canvas)
        drawOval(canvas)

        canvas_view.background = BitmapDrawable(resources, bitmap)
    }

    private fun drawRectangle(canvas: Canvas) =
        Util.drawObject(100, 100, 600, 300, "#009944", RectShape(), canvas)

    private fun drawOval(canvas: Canvas) =
        Util.drawObject(100, 500, 600, 800, "#009944", OvalShape(), canvas)

}
