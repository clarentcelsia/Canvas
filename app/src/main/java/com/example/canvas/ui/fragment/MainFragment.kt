package com.example.canvas.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import com.example.canvas.R
import com.example.canvas.utility.CustomView
import com.example.canvas.utility.CustomView.Companion.CIRCLE
import com.example.canvas.utility.CustomView.Companion.ERASE
import com.example.canvas.utility.CustomView.Companion.FREEFORM
import com.example.canvas.utility.CustomView.Companion.LINE
import com.example.canvas.utility.CustomView.Companion.RECTANGLE
import com.example.canvas.utility.CustomView.Companion.SQUARE
import com.example.canvas.utility.CustomView.Companion.TRIANGLE

/**
 *  Canvas is where you can draw anything on it but
 *  it needs 4 basic components which are
 *  1. A Canvas: to host the draw() [ The process of writing into bitmap ]
 *  2. A Bitmap: to hold the pixels

 *  3. A Shape: e.g oval, rect, etc,
 *  4. A Paint: for colors and styles.
 */

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var canvas: CustomView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        canvas = view.findViewById(R.id.iv_canvas)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.rectangle -> canvas.mCurrentShape = RECTANGLE
            R.id.square -> canvas.mCurrentShape = SQUARE
            R.id.line -> canvas.mCurrentShape = LINE
            R.id.circle -> canvas.mCurrentShape = CIRCLE
            R.id.triangle -> canvas.mCurrentShape = TRIANGLE
            R.id.freeform -> canvas.mCurrentShape = FREEFORM
            R.id.clear -> canvas.mCurrentShape = ERASE
        }
        return super.onOptionsItemSelected(item)
    }
}