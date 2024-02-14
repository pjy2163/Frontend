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

        binding.prevBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        //TODO: 등록된 결제수단 확인하기 위해 가로로 넘기면서 보는 뷰 (은행계좌 또는 카드명 부분 가로로 넘어가도록 처리)

        //비밀번호 변경 버튼 이벤트 처리
        binding.changePwdBtn.setOnClickListener{
            val intent = Intent(this, ChangePwdActivity::class.java)
            startActivity(intent)
        }

        //결제수단 관리 버튼 이벤트처리
        binding.managePayBtn.setOnClickListener{
            val intent = Intent(this,DeletePayActivity::class.java)
            startActivity(intent)
        }

        //결제수단 관리 버튼 이벤트처리
        binding.deleteAccountBtn.setOnClickListener{
            //TODO:정말로 탈퇴하시겠습니까 및 계정 탈퇴 처리
        }
    }
}