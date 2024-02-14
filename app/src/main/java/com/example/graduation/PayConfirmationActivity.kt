package com.example.graduation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.graduation.databinding.ActivityPayConfirmationBinding

class PayConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityPayConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO:결제승인 과정에서 오류시 else 처리
        binding.approveBtn.setOnClickListener {
            // 결제 승인 다이얼로그

            val dialog =PayConfirmationDialog()
            dialog.show(supportFragmentManager, "PayConfirmationDialog")
        }
    }
}