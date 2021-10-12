package com.example.alphamasktest


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val robotCustomView = findViewById<CanvasView>(R.id.canvasView)
        robotCustomView.setOnClickListener {
            robotCustomView.animateProgress(robotCustomView.width.toFloat())
        }
    }


}