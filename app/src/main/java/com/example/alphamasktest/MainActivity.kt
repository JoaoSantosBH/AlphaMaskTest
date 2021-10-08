package com.example.alphamasktest

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.graphics.Bitmap

import androidx.core.content.ContextCompat

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

        cropAlphaMask()
        animateButton()

    }

    private fun cropAlphaMask() {
        val myImageView = findViewById<ImageView>(R.id.imageView)
        val canvas = Canvas()
        val paint = Paint()
        val cropedImage = getBitmapFromVectorDrawable(R.drawable.illustration)
        val mask = getBitmapFromVectorDrawable(R.drawable.ic_shape)
//        val mask = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_shape)
        val result = Bitmap.createBitmap(cropedImage.width, cropedImage.height, Bitmap.Config.ARGB_8888)

        canvas.setBitmap(result)
        paint.setFilterBitmap(false);
        canvas.drawBitmap(cropedImage, 0f, 0f, paint)
        paint.setXfermode( PorterDuffXfermode (PorterDuff.Mode.DST_IN))
        canvas.drawBitmap(mask,0f,0f,paint)
        paint.setXfermode(null);

        myImageView.setImageBitmap(result);
        myImageView.invalidate();
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
        mAnimator.duration = 3000
        mAnimator.start()
        mAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                Log.i("animation", "onAnimationStart")
            }

            override fun onAnimationEnd(animator: Animator) {
                img.setVisibility(View.GONE)
                img.setVisibility(View.VISIBLE)
            }

            override fun onAnimationCancel(animator: Animator) {
                Log.i("animation", "onAnimationCancel")
            }

            override fun onAnimationRepeat(animator: Animator) {
                // do nothing
            }
        })
    }

}