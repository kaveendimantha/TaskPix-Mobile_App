package com.example.taskmaster

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmaster.fragments.HomeFragment

class Onboard_screen_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboard_screen1)

        val btnNavigate: Button = findViewById(R.id.btnNavigate1)
        btnNavigate.setOnClickListener {
            val intent = Intent(this, Onboard_Screen2::class.java)
            startActivity(intent)
        }

        val skpNavigate: TextView = findViewById(R.id.skpNavigate1)
        skpNavigate.setOnClickListener {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }
    }
}