package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.payBtn.setOnClickListener{
            val intent = Intent(this, CheckPayInfoActivity::class.java)
            startActivity(intent)
        }

        binding.registerPayBtn.setOnClickListener{
            val intent = Intent(this, RegisterMainActivity::class.java)
            startActivity(intent)
        }

        binding.editInfoBtn.setOnClickListener{
            val intent = Intent(this, MyInfoActivity::class.java)
            startActivity(intent)
        }


    }


}