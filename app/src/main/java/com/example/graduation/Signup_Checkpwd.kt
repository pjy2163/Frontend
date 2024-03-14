package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.graduation.databinding.ActivitySignupCheckpwdBinding
import java.util.Locale

class Signup_Checkpwd : AppCompatActivity(), SignupDialogInterface {

    private lateinit var binding: ActivitySignupCheckpwdBinding
    lateinit var mtts: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupCheckpwdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        //화면 정보 읽기
        if (soundState) {
            onSpeech("회원가입 이메일 입력 화면입니다.")
        }

        binding.enterButton.setOnClickListener {
            val checkpwd = binding.signupInputCheckpwd.text.toString().trim()
            val pwd = intent.getStringExtra("pwd").toString()
            if (isPwdIdentified(checkpwd, pwd)) {
                //비밀번호 일치하면 계정 생성 후 로그인 화면으로 이동
                val email = intent.getStringExtra("email").toString()
                val name = intent.getStringExtra("name").toString()
                makeUser(name, email, pwd)
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            } else {
                //비밀번호 불일치 시
                run {
                    val dialog = SignupDialog(this, "비밀번호가 일치하지않습니다.")
                    dialog.isCancelable = false
                    dialog.show(this.supportFragmentManager, "SignupDialog")
                    if (soundState) {
                        onSpeech("비밀번호가 일치하지않습니다.")
                    }
                }
            }
        }
    }

    //비밀번호 일치 검사
    private fun isPwdIdentified(checkpwd: String, pwd: String) : Boolean {
        return pwd == checkpwd
    }

    //계정 생성
    private fun makeUser(name: String, email: String, pwd: String): Boolean {
        return TODO("서버에 입력받은 email, pwd로 사용자 계정 생성")
    }

    override fun onDialogButtonClick() {
        finish()
    }

    //음성 안내
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}