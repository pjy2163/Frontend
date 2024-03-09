package com.example.graduation.signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.databinding.ActivitySignupNameBinding
import java.util.Locale

class Signup_Name : AppCompatActivity() {

    private lateinit var binding: ActivitySignupNameBinding
    lateinit var mtts: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        //화면 정보 읽기
        if (soundState) {
            onSpeech("회원가입 이름 입력 화면입니다.")
        }

        binding.enterButton.setOnClickListener {
            val name = binding.signupInputName.text.toString().trim()
            val intent1 = Intent(this, Signup_Email::class.java)
            val intent2 = Intent(this, Signup_Checkpwd::class.java)
            intent2.putExtra("name", name) //이름 값 전달
            startActivity(intent1) //다음 화면 시작
        }
    }

    //음성 안내
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }
}