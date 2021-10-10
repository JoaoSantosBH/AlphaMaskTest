package com.example.alphamasktest

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat

class CardView @JvmOverloads constructor(
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

    private val mode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    private var currentPercentage = 0
    private val cropedImage by lazy { getBitmapFromVectorDrawable(R.drawable.illustration) }
    private val mask by lazy { getBitmapFromVectorDrawable(R.drawable.ic_shape) }
    private val result by lazy {
        Bitmap.createBitmap(
            cropedImage.width,
            cropedImage.height,
            Bitmap.Config.ARGB_8888
        )
    }

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (width == 0 || height == 0) return
//        invalidate()
        canvas?.let {
            cropAlphaMask(it)
            invalidate()
        }
    }

    private fun cropAlphaMask(canvas: Canvas) {
//        canvas.setBitmap(result)
        canvas.drawBitmap(cropedImage, 0f,0f, paint)
        paint.xfermode = mode
        canvas.drawBitmap(mask,0f,0f, paint)

        paint.xfermode = null
//        canvas.drawBitmap(result, 0f, 0f, paint)
//        canvas.restore()
    }

    fun getBitmapFromVectorDrawable(drawableId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, drawableId)
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    fun animateProgress() {
        val valuesHolder = PropertyValuesHolder.ofFloat(PERCENTAGE_VALUE_HOLDER, 0f, 100f)
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            duration = 2000
            addUpdateListener {
                val percentage = it.getAnimatedValue(PERCENTAGE_VALUE_HOLDER) as Float
                currentPercentage = percentage.toInt()
                Log.i("TAG", "Animationr " + currentPercentage)
                invalidate()
            }
        }
        animator.start()
    }
}
