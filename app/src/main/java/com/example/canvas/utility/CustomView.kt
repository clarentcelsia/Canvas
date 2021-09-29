package com.example.canvas.utility

import android.content.Context
import android.graphics.*
import android.graphics.Paint.DITHER_FLAG
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.canvas.R

class CustomView : View {

    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas
    private lateinit var paint: Paint
    private lateinit var eraserPaint: Paint

    private var isDrawing = false

    private var isNotFirstTouch = true

    private var mStartX = 0.0f

    private var mStartY = 0.0f

    private var path = Path()

    private var mx = 0.0f

    private var my = 0.0f

    private val STROKE_WIDTH = 2.0F

    private val ERASER_WIDTH = 20.0F

    var mCurrentShape = 0

    companion object {
        const val RECTANGLE = 1
        const val SQUARE = 2
        const val LINE = 3
        const val CIRCLE = 4
        const val TRIANGLE = 5
        const val FREEFORM = 6
        const val ERASE = 7
    }

    constructor(context: Context) : super(context) {
        //TODO
        init()
    }

    // Option to create another constructor
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        //TODO
        init()
    }

    /**
     * Called whenever the view changes size.
     * Since the view starts out with no size, this is also called after
     * the view has been inflated and has a valid size.
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }


    /* invalidate() calls draw method */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mx = event.x
        my = event.y

        when (mCurrentShape) {
            RECTANGLE -> onTouchEventRectangle(event)
            SQUARE -> onTouchEventSquare(event)
            LINE -> onTouchEventLine(event)
            CIRCLE -> onTouchEventCircle(event)
            TRIANGLE -> onTouchEventTriangle(event)
            FREEFORM -> onTouchEventFreeForm(event)
            ERASE -> onTouchEventErase(event)
        }

        return true
    }

    /* This method called to create custom drawing UI */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Set left and top 0f, to draw object as where
        // the current coordinate position of pointer/cursor is.
        // unless you want to have such a margin of the object
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        if (isDrawing) {
            when (mCurrentShape) {
                RECTANGLE -> onDrawRectangle(canvas)
                SQUARE -> onDrawSquare(canvas)
                LINE -> onDrawLine(canvas)
                CIRCLE -> onDrawCircle(canvas)
                TRIANGLE -> onDrawTriangle(canvas)
                FREEFORM -> onDrawFreeForm(canvas)
                ERASE -> onErase(canvas)
            }
        }
    }

    private fun onTouchEventRectangle(event: MotionEvent) {
        when (event.action) {
            //First touch
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mx
                mStartY = my
            }

            //Drawing process
            MotionEvent.ACTION_MOVE -> {
                invalidate()
            }

            //Finishing the draw
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                drawRectangle(canvas, paint)
            }
        }
    }

    private fun onTouchEventSquare(event: MotionEvent) {
        when (event.action) {
            //First touch
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mx
                mStartY = my
            }

            //Drawing process
            MotionEvent.ACTION_MOVE -> {
                adjustSquare(mx, my)
                invalidate()
            }

            //Finishing the draw
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                adjustSquare(mx, my)
                drawRectangle(canvas, paint)
            }
        }
    }

    private fun onTouchEventLine(event: MotionEvent) {
        when (event.action) {
            //First touch
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mx
                mStartY = my
            }

            //Drawing process
            MotionEvent.ACTION_MOVE -> invalidate()

            //Finishing the draw
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                drawLine(canvas, paint)
            }
        }
    }

    private fun onTouchEventCircle(event: MotionEvent) {
        when (event.action) {
            //First touch
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mx
                mStartY = my
            }

            //Drawing process
            MotionEvent.ACTION_MOVE -> invalidate()

            //Finishing the draw
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                drawCircle(canvas, paint)
            }
        }
    }

    private var pointA = PointF(0.0f, 0.0f)
    private var pointB = PointF(0.0f, 0.0f)
    private var pointC = PointF(0.0f, 0.0f)

    private fun onTouchEventTriangle(event: MotionEvent) {
        when (event.action) {
            //First touch
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                isDrawing = true
                isNotFirstTouch = false
                mStartX = mx
                mStartY = my
                pointB = PointF(mStartX, mStartY)
            }

            //Drawing process
            MotionEvent.ACTION_MOVE -> {
                isNotFirstTouch = true
                adjustTriangle(mx, my)
                invalidate()
            }

            //Finishing the draw
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                drawTriangle(canvas, paint)
                path.reset()
            }
        }
    }

    private fun onTouchEventFreeForm(event: MotionEvent) {
        when (event.action) {
            //First touch
            MotionEvent.ACTION_DOWN -> {
                // Clear any lines and curves from the path, making it empty first.
                path.reset()
                isDrawing = true
                mStartX = mx
                mStartY = my
            }

            //Drawing process
            MotionEvent.ACTION_MOVE -> {
                drawFreeForm(canvas, paint)
                invalidate()
            }

            //Finishing the draw
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                path.reset()
            }
        }
    }

    private fun onTouchEventErase(event: MotionEvent) {
        when (event.action) {
            //First touch
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                isDrawing = true
                mStartX = mx
                mStartY = my
            }

            //Drawing process
            MotionEvent.ACTION_MOVE -> {
                drawFreeForm(canvas, eraserPaint)
                invalidate()
            }

            //Finishing the draw
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                path.reset()
            }
        }
    }

    private fun onDrawRectangle(canvas: Canvas) {
        drawRectangle(canvas, paint)
    }

    private fun onDrawSquare(canvas: Canvas) {
        onDrawRectangle(canvas)
    }

    private fun onDrawLine(canvas: Canvas) {
        drawLine(canvas, paint)
    }

    private fun onDrawCircle(canvas: Canvas) {
        drawCircle(canvas, paint)
    }

    private fun onDrawTriangle(canvas: Canvas) {
        drawTriangle(canvas, paint)
    }

    private fun onDrawFreeForm(canvas: Canvas) {
        drawFreeForm(canvas, paint)
    }

    private fun onErase(canvas: Canvas) {
        drawFreeForm(canvas, eraserPaint)
    }

    private fun drawRectangle(canvas: Canvas, paint: Paint) {
        val left = if (mStartX > mx) mx else mStartX
        val top = if (mStartY > my) my else mStartY
        val width = if (mStartX > mx) mStartX else mx
        val height = if (mStartY > my) mStartY else my

        canvas.drawRect(left, top, width, height, paint)
    }

    private fun drawLine(canvas: Canvas, paint: Paint) {
        canvas.drawLine(mStartX, mStartY, mx, my, paint)
    }

    private fun drawCircle(canvas: Canvas, paint: Paint) {
        val cx = Math.abs(mx - mStartX).toDouble()
        val cy = Math.abs(my - mStartY).toDouble()
        val rad = Math.sqrt(Math.pow(cx, 2.0) + Math.pow(cy, 2.0))
        canvas.drawCircle(mStartX, mStartY, rad.toFloat(), paint)
    }

    private fun drawTriangle(canvas: Canvas, paint: Paint) {
        // Path encapsulates multiple contour of geometric paths such as straight line segments,
        // quadratic curves and cubic curves.
        if (isNotFirstTouch) {
            path.reset()
            path.apply {
                fillType = Path.FillType.EVEN_ODD
                moveTo(pointB.x, pointB.y)
                lineTo(pointA.x, pointA.y)
                lineTo(pointC.x, pointC.y)
                close()
            }

            canvas.drawPath(path, paint)
        }
    }

    private fun drawFreeForm(canvas: Canvas, paint: Paint) {
        path.apply {
            moveTo(mStartX, mStartY)
            quadTo(mStartX, mStartY, mx, my)
            mStartX = mx
            mStartY = my
        }

        canvas.drawPath(path, paint)
    }

    private fun adjustTriangle(x: Float, y: Float) {
        val dx = x - mStartX

        // Position X of new PointA
        val pointAx = x - dx
        val pointAy = mStartY

        pointA = PointF(pointAx, pointAy)
        pointB = PointF(x, y)
        pointC = PointF((mStartX - dx), my)
    }

    private fun adjustSquare(x: Float, y: Float) {
        val deltaX = Math.abs(x - mStartX)
        val deltaY = Math.abs(y - mStartY)

        val max = Math.max(deltaX, deltaY)
        mx = if (mStartX - x < 0) mStartX + max else mStartX - max
        my = if (mStartY - y < 0) mStartY + max else mStartY - max
    }

    /**
     *  Object style and color.
     *      AntiAlias is to make the edge of the object be smoother.
     *      Dither: proses di mana nilai warna dari setiap piksel diubah ke
     *          nilai warna yang paling cocok di palet yang menjadi target.
     *
     *   Paint.Style.STROKE -> Geometry and text drawn with this style will be stroked, respecting the stroke-related fields on the paint.

     *   Paint.Cap
    The Cap specifies the treatment for the beginning and ending of stroked lines and paths.
    Paint.Cap.ROUND -> The stroke projects out as a semicircle, with the center at the end of the path.

    Paint.Join
    The Join specifies the treatment where lines and curve segments join on a stroked path
    Paint.Join.ROUND -> The outer edges of a join meet in a circular arc.
     */
    private fun init() {
        paint = Util.paintObject(
            flag = DITHER_FLAG,
            isAntiAlias = true,
            isDither = true,
            color = ResourcesCompat.getColor(resources, R.color.teal_700, null),
            style = Paint.Style.STROKE,
            strokeJoin = Paint.Join.ROUND,
            strokeCap = Paint.Cap.ROUND,
            strokeWidth = STROKE_WIDTH
        )

        eraserPaint = Util.paintObject(
            flag = DITHER_FLAG,
            isAntiAlias = true,
            isDither = true,
            color = ResourcesCompat.getColor(resources, R.color.white, null),
            style = Paint.Style.FILL_AND_STROKE,
            strokeJoin = Paint.Join.ROUND,
            strokeCap = Paint.Cap.ROUND,
            strokeWidth = ERASER_WIDTH
        )

    }

}
