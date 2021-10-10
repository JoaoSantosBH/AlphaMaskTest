package com.example.alphamasktest

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

fun convertToBW(original: Bitmap): Bitmap {
    val grayscale = Bitmap.createBitmap(
        original.getWidth(), original.getHeight(),
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
fun getBitMapFromView(view: View, applicationContext: Context): Bitmap {

    view.layoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.WRAP_CONTENT,
        ConstraintLayout.LayoutParams.WRAP_CONTENT
    )
    val dm = applicationContext.resources.displayMetrics
    view.measure(
        View.MeasureSpec.makeMeasureSpec(141, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(183, View.MeasureSpec.EXACTLY)
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