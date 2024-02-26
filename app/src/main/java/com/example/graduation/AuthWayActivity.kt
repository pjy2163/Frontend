package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.databinding.ActivityAuthWayBinding
import java.util.Locale

class AuthWayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthWayBinding
    lateinit var mtts:TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthWayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        //화면 정보 읽기
        if (soundState) {
            onSpeech("인증 방법 선택 화면입니다")
        }

        binding.prevBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }
            val intent = Intent(this,ChoosePayMethodActivity::class.java)
            startActivity(intent)
        }

        //지문 인식 버튼 이벤트 처리
        binding.fingerprintBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.fingerprintBtn.text)
            }
            val intent = Intent(this,PayFingerActivity::class.java)
            startActivity(intent)
        }


        //비밀번호 입력 버튼 이벤트 처리
        binding.pwdBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.pwdBtn.text)
            }
            val intent = Intent(this,PayPasswordActivity::class.java)
            startActivity(intent)
        }

    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}