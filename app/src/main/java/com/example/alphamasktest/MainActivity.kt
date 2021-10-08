package com.example.alphamasktest

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        canvasGambi()
    }

    private fun canvasGambi() {

        val img = findViewById<ImageView>(R.id.mask)

        val canvas = Canvas()
         val fullRect by lazy { Rect(0, 0, canvas.width, canvas.height) }

        val mask =
            BitmapFactory.decodeResource(applicationContext.resources, R.mipmap.planta_mask)

//        val mainImage =
//            BitmapFactory.decodeResource(applicationContext.resources, R.drawable.illustration)
        val result = Bitmap.createBitmap(218, 138, Bitmap.Config.ARGB_8888)
//        val resultM = Bitmap.createBitmap(218, 138, Bitmap.Config.ARGB_8888)

        canvas.setBitmap(result)
//        canvasM.setBitmap(resultM)
//        val imagePaint = Paint()
        val maskPaint = Paint()


        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SCREEN)
//        imagePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
        val saturation = creatingColorMatrix()

        maskPaint.setColorFilter(saturation)

        canvas.drawBitmap(mask, 0f, 0f, maskPaint)
//        canvasM.drawBitmap(mask, 0f, 0f, maskPaint)


        result.setHasAlpha(true)
        canvas.save()
        img.setImageBitmap(result)
//        mas.background(resultM)

        img.invalidate()
        canvas.restore()
    }

    fun creatingColorMatrix(): ColorMatrixColorFilter {
        val colorMatrix = ColorMatrix()
        val colorMatrixFilter = ColorMatrixColorFilter(colorMatrix)
        colorMatrix.setSaturation(0.5f)
        return colorMatrixFilter
    }

}