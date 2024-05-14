package com.example.taskmaster

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmaster.fragments.HomeFragment

class Onboard_Screen2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboard_screen2)

        val btnNavigate: Button = findViewById(R.id.btnNavigate2)
        btnNavigate.setOnClickListener {
            val intent = Intent(this, Onboard_screen3::class.java)
            startActivity(intent)
        }

        val skpNavigate: TextView = findViewById(R.id.skpNavigate2)
        skpNavigate.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Hide the action bar (if using AppCompatActivity)
        supportActionBar?.hide()
    }
}