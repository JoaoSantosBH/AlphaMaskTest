package com.example.alphamasktest

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.graphics.Bitmap

import androidx.core.content.ContextCompat

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable


import android.util.Log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cropAlphaMask()
    }

    private fun cropAlphaMask() {
        val myImageView = findViewById<ImageView>(R.id.cardColor)
        val layers = arrayOfNulls<Drawable>(2)
        val canvas = Canvas()
        val paint = Paint()

        val cropedImage = getBitmapFromVectorDrawable(R.drawable.illustration)
        val mask = getBitmapFromVectorDrawable(R.drawable.ic_shape)
//        val mask = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_shape)
        val result =
            Bitmap.createBitmap(cropedImage.width, cropedImage.height, Bitmap.Config.ARGB_8888)

        canvas.setBitmap(result)
        paint.isFilterBitmap = true

        canvas.drawBitmap(cropedImage, 0f, 0f, paint)
        layers[0] = BitmapDrawable(applicationContext.resources,result)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.drawBitmap(mask, 0f, 0f, paint)

        layers[1] = BitmapDrawable(applicationContext.resources,result)
        val layerDrawable = LayerDrawable(layers)
        myImageView.setImageDrawable(layerDrawable)
//            myImageView.setImageBitmap(result)
        animateShapeLayer(layerDrawable)
//        myImageView.invalidate()

    }

    fun getBitmapFromVectorDrawable(drawableId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(applicationContext, drawableId)
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun animateShapeLayer(layerList: LayerDrawable) {

        val drawable  = layerList.getDrawable(1)
        val drawable2  = layerList.getDrawable(0)
        val animate = ObjectAnimator.ofInt(drawable, "level", 0, 10000)
        animate.duration = 5000
        animate.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                Log.i("animation", "onAnimationStart")
            }

            override fun onAnimationEnd(animator: Animator) {
                Log.i("animation", "onAnimationEnd")
            }

            override fun onAnimationCancel(animator: Animator) {
                Log.i("animation", "onAnimationCancel")
            }

            override fun onAnimationRepeat(animator: Animator) {
                // do nothing
            }
        })
        animate.start()

    }

}