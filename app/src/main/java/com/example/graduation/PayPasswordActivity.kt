package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.graduation.databinding.ActivityPayPasswordBinding

class PayPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.prevBtn.setOnClickListener {
            val intent = Intent(this,PayMethodSelectionActivity::class.java)
            startActivity(intent)
        }

        binding.nextBtn.setOnClickListener {
            val intent = Intent(this,PayConfirmationActivity::class.java)
            startActivity(intent)
        }

        binding.enterBtn.setOnClickListener{
            Toast.makeText(applicationContext,"비밀번호가 입력되었습니다.",Toast.LENGTH_SHORT ).show()
        //TODO: if 비밀번호 일치, else 비밀번호 불일치 오류 처리
            //입력버튼의 역할이 뭐지. 이벤트 처리해야함
        }

        //TODO: 구현중
     /*   binding.deleteBtn.setOnClickListener{
            //비밀번호 한자리씩 지우기
            val currentText = binding.passwordEt.text.toString()

            if (currentText.isNotEmpty()) {
                val newText = currentText.substring(0, currentText.length - 1)
                binding.passwordEt.setText(newText)
                Toast.makeText(applicationContext, "비밀번호 한 자리를 삭제하였습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "삭제할 비밀번호가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }*/
    }
}