package com.example.alphamasktest

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.graphics.Bitmap

import androidx.core.content.ContextCompat

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable


import android.util.Log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val card = findViewById<CardView>(R.id.cardView)
//        card.animateProgress()
    }


    private fun animateShapeLayer(layerList: LayerDrawable) {

        val drawable = layerList.getDrawable(1)
        val drawable2 = layerList.getDrawable(0)
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