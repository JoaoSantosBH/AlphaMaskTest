package com.example.alphamasktest

import android.content.res.Resources
import android.graphics.*
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.roundToInt

fun convertToBW(original: Bitmap): Bitmap {
    val grayscale = Bitmap.createBitmap(
        original.width, original.height,
        Bitmap.Config.ARGB_8888
    )
    val c = Canvas(grayscale)
    val p = Paint()
    val cm = ColorMatrix()
    cm.setSaturation(0f)
    val f = ColorMatrixColorFilter(cm)
    p.colorFilter = f
    c.drawBitmap(original, 0f, 0f, p)
    c.setBitmap(null)
    return grayscale
}

fun getBitMapFromView(view: View, width: Int, height: Int): Bitmap {

    view.layoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.WRAP_CONTENT,
        ConstraintLayout.LayoutParams.WRAP_CONTENT
    )
    view.measure(
        View.MeasureSpec.makeMeasureSpec(convertDpToPixels(width), View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(convertDpToPixels(height), View.MeasureSpec.EXACTLY)
    )
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    val bitmap = Bitmap.createBitmap(
        view.measuredWidth,
        view.measuredHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    view.layout(view.left, view.top, view.right, view.bottom)
    view.draw(canvas)
    return bitmap
}

fun convertDpToPixels(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(), Resources.getSystem().displayMetrics
    ).roundToInt()
}