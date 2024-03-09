package com.example.graduation.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.example.graduation.databinding.ActivityLoginPwdBinding
import java.util.*

class Login_Pwd : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPwdBinding
    lateinit var mtts:TextToSpeech
    // SharedPreferences에서 소리 on/off 상태 불러오기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        //화면 정보 읽기
        if (soundState) {
            onSpeech("로그인 비밀번호 입력 화면입니다.")
        }

        binding.enter.setOnClickListener {
            val pwd = binding.loginInputPwd.text.toString()
            val email = intent.getStringExtra("email")
            if (pwd == "") {
                //비밀번호 공백 입력 시
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                if (soundState) {
                    onSpeech("비밀번호를 입력해주세요.")
                }
            }
            else {
                //입력한 비밀번호를 DB에 저장된 비밀번호와 비교
                val dbPassword = TODO("입력한 email에 해당하는 사용자의 DB 경로.get(password).toString()")
                if (pwd == dbPassword) {
                    TODO("로그인 로직")
                }
                else {
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    if (soundState) {
                        onSpeech("비밀번호가 일치하지 않습니다.")
                    } }
            }
        }

        //비밀번호 찾기 화면 연결
        /* binding.resetPwd.setOnClickListener {
            val intent = Intent("비밀번호 찾기 화면"::class.java)
            startActivity(intent)
            if (soundState) {
                    onSpeech("비밀번호 찾기")
                }
        }*/
    }

    //음성 안내
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }
}