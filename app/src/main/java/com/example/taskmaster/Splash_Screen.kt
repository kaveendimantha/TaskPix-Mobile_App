package com.example.taskmaster

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

private val handler = Handler(Looper.getMainLooper())

class Splash_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        handler.postDelayed({
            startActivity(Intent(this, Onboard_screen_1::class.java))
            finish()
        }, 2500L)

        // Hide the status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Hide the action bar (if using AppCompatActivity)
        supportActionBar?.hide()

    }
}