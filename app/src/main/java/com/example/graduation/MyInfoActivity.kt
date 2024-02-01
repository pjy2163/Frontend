package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduation.databinding.ActivityMainBinding
import com.example.graduation.databinding.ActivityMyInfoBinding

class MyInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changePwdBtn.setOnClickListener{
            val intent = Intent(this, CheckPayInfoActivity::class.java)
            startActivity(intent)
        }
    }
}