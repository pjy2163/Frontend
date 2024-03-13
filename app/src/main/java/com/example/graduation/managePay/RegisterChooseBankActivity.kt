package com.example.graduation.managePay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import com.example.graduation.databinding.ActivityRegisterChooseBankBinding
import java.util.Locale

//결제수단 등록> 은행선택
class RegisterChooseBankActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterChooseBankBinding
    lateinit var mtts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterChooseBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        //화면 정보 읽기
        if (soundState) {
            onSpeech("계좌 선택 화면입니다")
        }



        binding.hanaBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.hanaBtn.text)
            }



        }

        binding.shinhanBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.shinhanBtn.text)
            }
        }

        binding.kookminBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.kookminBtn.text)
            }
        }

    }



    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }


}