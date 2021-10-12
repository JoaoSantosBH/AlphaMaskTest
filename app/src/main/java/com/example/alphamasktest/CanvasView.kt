package com.example.alphamasktest

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View

class CanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        const val PERCENTAGE_VALUE_HOLDER = "percentage"
    }

    private var paint = Paint(ANTI_ALIAS_FLAG).apply {
        isFilterBitmap = false
        isAntiAlias = true
    }

    private var canvasRobot = Canvas()
    private val mode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    private var currentPercentage = 0
    private val image by lazy { getBitmapFromDrawable(context, R.drawable.ic_bandro) }
    private val mask by lazy { getBitmapFromDrawable(context, R.drawable.ic_path_tv) }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (width == 0 || height == 0) return
        canvas?.let {
            canvasRobot = it
            cropAlphaMask(canvasRobot)
            animateAlphaMask(canvasRobot)
        }
    }

    private fun cropAlphaMask(canvas: Canvas) {
        canvas.drawBitmap(image, 0f, 0f, paint)
        paint.xfermode = mode
        canvas.drawBitmap(mask, 0f, 0f, paint)
        paint.xfermode = null
    }

    private fun animateAlphaMask(canvas: Canvas){
        for (i in 0..currentPercentage){
            canvas.drawBitmap(image, 0f, 0f, paint)
            paint.xfermode = mode
            canvas.drawBitmap(mask, currentPercentage.toFloat(), 0f, paint)
            paint.xfermode = null
        }
    }

    fun animateProgress(dps: Float) {
        val animationRate = dps.convertDpToPixels()
        val valuesHolder = PropertyValuesHolder.ofFloat(PERCENTAGE_VALUE_HOLDER, 0f, animationRate.toFloat())
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            duration = 2000
            addUpdateListener {
                val percentage = it.getAnimatedValue(PERCENTAGE_VALUE_HOLDER) as Float
                currentPercentage = percentage.toInt()
                invalidate()
            }
        }
        animator.start()
    }
}
