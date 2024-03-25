package com.example.graduation.managePay

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.MainActivity
import com.example.graduation.databinding.ActivityEditPayBinding
import java.util.Locale

class EditPayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditPayBinding
    lateinit var mtts: TextToSpeech
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        // SharedPreferences에서 소리 on/off 상태 불러오기
        sharedPreferences = getSharedPreferences("sp1", MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)


        if (soundState){
            onSpeech("결제 수단 관리 화면입니다. 새 결제수단을 등록하시려면 화면 상단 버튼을, 이미 등록된 결제수단을 삭제하시려면 화면 하단 버튼을 눌러주세요.")
        }

        binding.prevBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.addPayBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.addPayBtn.text)
            }
            val intent = Intent(this, RegisterChooseBankActivity::class.java)
            startActivity(intent)
        }

        binding.deletePayBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.addPayBtn.text)
            }


        }
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}