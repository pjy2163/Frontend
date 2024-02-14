package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduation.databinding.ActivityCheckPayInfoBinding

class CheckPayInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckPayInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckPayInfoBinding.inflate(layoutInflater)
        setContentView(binding.root) // 수정된 부분

        binding.prevBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.nextBtn.setOnClickListener{
            val intent = Intent(this, PayMethodSelectionActivity::class.java)
            startActivity(intent)
        }
    }
}
