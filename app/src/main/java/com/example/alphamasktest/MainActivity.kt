package com.example.alphamasktest


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val canvasLayout = findViewById<CanvasView>(R.id.cardView)
        val bitmapFromLayout = findViewById<ConstraintLayout>(R.id.makeImage)
        val blackAndWhiteBitmap = getBitMapFromView(bitmapFromLayout, applicationContext)
        canvasLayout.setImageBitmap(blackAndWhiteBitmap)
        val bw = convertToBW(blackAndWhiteBitmap)
        val bgBlackAndWhite = findViewById<ImageFilterView>(R.id.bg)
        bgBlackAndWhite.setImageBitmap(bw)
        canvasLayout.setOnClickListener {
            canvasLayout.animateProgress()
        }
    }




}