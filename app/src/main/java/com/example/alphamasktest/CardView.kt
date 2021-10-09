package com.example.alphamasktest

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.setBlendMode

class CardView(
    context: Context?,
    attrs: AttributeSet?
) : View(context, attrs) {

    companion object {
        const val PERCENTAGE_VALUE_HOLDER = "percentage"
    }

    private val bgColor = Color.GRAY

    private val paint = Paint(ANTI_ALIAS_FLAG)

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
        setLayerType(View.LAYER_TYPE_SOFTWARE, paint)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (width == 0 || height == 0) return
        canvas?.let {
            cropAlphaMask(it)
            invalidate()
        }
    }

    private fun cropAlphaMask(canvas: Canvas) {
        canvas.setBitmap(result)
        paint.isFilterBitmap = false
        paint.isAntiAlias = true
        canvas.drawBitmap(cropedImage, 0f, 0f, paint)
        paint.xfermode = mode
        canvas.drawBitmap(mask, 0f, 0f, paint)
        paint.xfermode = null
        canvas.drawBitmap(result, 0f, 0f, paint)

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
        val valuesHolder = PropertyValuesHolder.ofFloat("percentage", 0f, 100f)
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            duration = 3000
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
