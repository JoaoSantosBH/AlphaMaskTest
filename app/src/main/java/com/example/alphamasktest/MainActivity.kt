package com.example.alphamasktest


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val canvasLayout = findViewById<CanvasView>(R.id.cardView)
        val bitmapFromLayout = findViewById<ConstraintLayout>(R.id.layoutToPrint)
        val blackAndWhiteBitmap = getBitMapFromView(bitmapFromLayout, applicationContext)
        val bgBlackAndWhite = findViewById<AppCompatImageView>(R.id.bg)

        canvasLayout.setImageBitmap(blackAndWhiteBitmap)
        val bw = convertToBW(blackAndWhiteBitmap)

        bgBlackAndWhite.setImageBitmap(bw)
        canvasLayout.setOnClickListener {
            canvasLayout.animateProgress()
        }
    }




}