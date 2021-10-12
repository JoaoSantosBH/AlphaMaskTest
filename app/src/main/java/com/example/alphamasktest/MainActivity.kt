package com.example.alphamasktest


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val canvasLayout = findViewById<CanvasView>(R.id.canvasView)
        val viewToCapture = findViewById<ConstraintLayout>(R.id.layoutToPrint)
        val bitmapOriginal = getBitMapFromView(viewToCapture, 200,172)
        val bgBlackAndWhite = findViewById<AppCompatImageView>(R.id.bg)
        val blackAndWhiteBitmap = convertToBW(bitmapOriginal)

        canvasLayout.setImageBitmap(bitmapOriginal)
        bgBlackAndWhite.setImageBitmap(blackAndWhiteBitmap)

        canvasLayout.setOnClickListener {
            canvasLayout.animateProgress(200)
        }
    }




}