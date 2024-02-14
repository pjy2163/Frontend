package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduation.databinding.ActivityAuthWayBinding

class AuthWayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthWayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthWayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.prevBtn.setOnClickListener {
            val intent = Intent(this,PayMethodSelectionActivity::class.java)
            startActivity(intent)
        }

        //비밀번호 입력 버튼 이벤트 처리
        binding.pwdBtn.setOnClickListener {
            val intent = Intent(this,PayPasswordActivity::class.java)
            startActivity(intent)
        }


     /*   binding.nextBtn.setOnClickListener {
            val intent = Intent(this,PayPasswordActivity::class.java)
            startActivity(intent)
        }*/
    }
}