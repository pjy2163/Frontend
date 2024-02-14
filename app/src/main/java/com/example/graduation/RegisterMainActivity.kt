package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduation.databinding.ActivityRegisterMainBinding

class RegisterMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.accountBtn.setOnClickListener{
            val intent = Intent(this, ChooseBankActivity::class.java)
            startActivity(intent)
        }

        binding.cardBtn.setOnClickListener{
            val intent = Intent(this,  ChooseBankActivity::class.java)
            startActivity(intent)
        }

        binding.prevBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}