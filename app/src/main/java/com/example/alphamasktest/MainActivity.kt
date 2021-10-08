package com.example.alphamasktest

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.graphics.Bitmap

import androidx.core.content.ContextCompat

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.graphics.drawable.RotateDrawable


import android.util.Log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cropAlphaMask()
    }

    private fun cropAlphaMask() {
        val myImageView = findViewById<ImageView>(R.id.cardColor)
        val canvas = Canvas()
        val paint = Paint()

        val cropedImage = getBitmapFromVectorDrawable(R.drawable.illustration)
        val mask = getBitmapFromVectorDrawable(R.drawable.ic_shape)
//        val mask = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_shape)
        val result =
            Bitmap.createBitmap(cropedImage.width, cropedImage.height, Bitmap.Config.ARGB_8888)

        canvas.setBitmap(result)
        paint.isFilterBitmap = false

        canvas.drawBitmap(cropedImage, 0f, 0f, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.drawBitmap(mask, 0f, 0f, paint)

        myImageView.setImageBitmap(result)
        animateShapeLayer(myImageView)

        myImageView.invalidate()

        //ImageView imageView = (ImageView) findViewById(R.id.imageView);
        //Resources r = getResources();
        //Drawable[] layers = new Drawable[2];
        //layers[0] = r.getDrawable(R.drawable.your_background_res);
        //PulseDrawable pulseDrawable = new PulseDrawable(Color.WHITE);
        //layers[1] = pulseDrawable;
        //LayerDrawable layerDrawable = new LayerDrawable(layers);
        //// This will adjust X & Y of layer, we have to pass layer index (250 pixels from Left and 150 pixels from Right)
        //layerDrawable.setLayerInset(1, 250, 150, 0, 0);
        //imageView.setImageDrawable(layerDrawable);

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

    private fun animateShapeLayer(drawable2: ImageView) {
        val drawable = findViewById<ImageView>(R.id.cardColor)
        
//        val layerDrawable =
//            ResourcesCompat.getDrawable(resources, R.drawable.canvas_layer, null) as LayerDrawable?
//        val drawable :RotateDrawable = layerDrawable?.findDrawableByLayerId(R.id.card) as RotateDrawable

        val animate = ObjectAnimator.ofInt(drawable, "level",  0, 10000)
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