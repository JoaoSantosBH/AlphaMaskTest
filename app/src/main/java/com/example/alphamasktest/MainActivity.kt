package com.example.alphamasktest

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.graphics.Bitmap

import androidx.core.graphics.drawable.DrawableCompat

import android.os.Build

import androidx.core.content.ContextCompat

import android.graphics.drawable.Drawable
import android.animation.Animator

import android.animation.ObjectAnimator

import android.graphics.drawable.RotateDrawable

import android.graphics.drawable.LayerDrawable
import android.util.Log


class MainActivity : AppCompatActivity() {

    lateinit var mAnimator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        canvasGambi()
//        animateButton()

    }

    private fun canvasGambi() {
        val img = findViewById<ImageView>(R.id.imageView)

        val canvas = Canvas()

        val mainImage = getBitmapFromVectorDrawable(R.drawable.illustration)
        val mask = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.planta_mask)
        val result = Bitmap.createBitmap(mainImage.width, mainImage.height, Bitmap.Config.ARGB_8888)

        canvas.setBitmap(result)
        val paint = Paint()
        paint.setFilterBitmap(false);
        canvas.drawBitmap(mainImage, 0f, 0f, paint)
        paint.setXfermode( PorterDuffXfermode (PorterDuff.Mode.DST_IN))

        canvas.drawBitmap(mask,0f,0f,paint)
        paint.setXfermode(null);

        img.setImageBitmap(result);
        img.invalidate();

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

    private fun animateButton() {
        val img = findViewById<ImageView>(R.id.imageView)

        val layerDrawable: LayerDrawable = img.getDrawable() as LayerDrawable
        val drawable = layerDrawable.getDrawable(0) as RotateDrawable
        mAnimator = ObjectAnimator.ofInt(drawable, "level", 0, 10000)
        mAnimator.duration = 300
        mAnimator.start()
        mAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                // do nothing
            }

            override fun onAnimationEnd(animator: Animator) {
                img.setVisibility(View.GONE)
                img.setVisibility(View.VISIBLE)
            }

            override fun onAnimationCancel(animator: Animator) {
                Log.v("animation", "onAnimationCancel")
            }

            override fun onAnimationRepeat(animator: Animator) {
                // do nothing
            }
        })
    }

}