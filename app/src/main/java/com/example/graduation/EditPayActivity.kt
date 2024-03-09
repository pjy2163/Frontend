package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.databinding.ActivityEditPayBinding
import com.example.graduation.register.RegisterChooseBankActivity
import java.util.Locale

class EditPayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditPayBinding
    lateinit var mtts: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPayBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_edit_pay)
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        //화면 정보 읽기
        if (soundState) {
            onSpeech("결제 수단 관리 화면입니다.")
        }

        //이전화면 버튼
        binding.prevBtn.setOnClickListener{
            onSpeech(binding.prevBtn.text)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //결제수단 등록 버튼
        binding.registerPayBtn.setOnClickListener{
            onSpeech(binding.registerPayBtn.text)
            val intent = Intent(this, RegisterChooseBankActivity::class.java)
            startActivity(intent)
        }

        //결제수단 삭제 버튼
        binding.deletePayBtn.setOnClickListener{
            onSpeech(binding.deletePayBtn.text)
            val intent = Intent(this, DeletePayActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }
}