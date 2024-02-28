package com.example.graduation.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
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

            //어느 은행인지 데이터를 가지고 RegisterePayVoiceActivity로 이동함
            val intent = Intent(this, RegisterPayVoiceActivity::class.java)
            startActivity(intent)

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