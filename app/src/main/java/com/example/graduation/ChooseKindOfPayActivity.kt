package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduation.databinding.ActivityChooseBankBinding
import com.example.graduation.databinding.ActivityChooseKindOfPayBinding
import com.example.graduation.databinding.ActivityMainBinding

class ChooseKindOfPayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseKindOfPayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityChooseKindOfPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.accountBtn.setOnClickListener{
            val intent = Intent(this, ChooseBankActivity::class.java)
            startActivity(intent)
        }

        binding.cardBtn.setOnClickListener{
            val intent = Intent(this,  ChooseBankActivity::class.java)
            startActivity(intent)
        }

        binding.previousBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}