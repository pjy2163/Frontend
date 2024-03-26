package com.example.graduation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.graduation.databinding.ActivitySplash1Binding

class Splash1Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySplash1Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplash1Binding.inflate(layoutInflater)
        setContentView(binding.root)


        // Handler를 사용하여 5초 후에 Splash2Activity로 전환
        Handler().postDelayed({
            val intent = Intent(this, Splash2Activity::class.java)
            startActivity(intent)
            finish() // 현재 Activity 종료
        }, 5000)
    }

}