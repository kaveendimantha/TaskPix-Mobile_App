package com.example.taskmaster

import android.content.Context
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
            if (isUserLoggedInOnce()) {
                // User has logged in before, navigate to HomeScreen
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // User has not logged in before, show OnboardingScreen
                setUserLoggedInOnce()
                startActivity(Intent(this, Onboard_screen_1::class.java))
            }
            finish()
        }, 2500L)

        // Hide the status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Hide the action bar (if using AppCompatActivity)
        supportActionBar?.hide()
    }

    private fun isUserLoggedInOnce(): Boolean {
        val sharedPreferences = getSharedPreferences("taskPivotPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("userLoggedInOnce", false)
    }

    private fun setUserLoggedInOnce() {
        val sharedPreferences = getSharedPreferences("taskPivotPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("userLoggedInOnce", true)
        editor.apply()
    }
}
