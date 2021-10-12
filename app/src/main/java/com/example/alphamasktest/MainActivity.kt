package com.example.alphamasktest


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val card = findViewById<CanvasView>(R.id.canvasView)
        card.setOnClickListener {
            card.animateProgress(card.width.toFloat())
        }
    }


}