package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.databinding.ActivityLoginBinding
import com.example.graduation.databinding.ActivityLoginEmailBinding
import com.example.graduation.databinding.ActivityMainBinding
import java.util.Locale

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var mtts:TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
    // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        onSpeech("로그인 화면입니다. 이메일 로그인, 지문 로그인, 회원가입 버튼이 세로로 정렬되어 있습니다.")



        binding.pwdLoginBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.pwdLoginBtn.text)
            }
            val Intent = Intent(this, LoginEmailActivity::class.java)
            startActivity(Intent)
        }


        binding.fingerLoginBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.fingerLoginBtn.text)
            }
            val Intent = Intent(this, FingerLoginActivity::class.java)
            startActivity(Intent)
        }

        binding.signUpBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.signUpBtn.text)
            }

            val Intent = Intent(this, SignupNameActivity::class.java)
            startActivity(Intent)
        }
    }


    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}