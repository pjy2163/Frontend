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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupCheckpwdBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.enterButton.setOnClickListener {
            val checkpwd = binding.signupInputCheckpwd.text.toString().trim()
            val pwd = intent.getStringExtra("pwd").toString()
            if (isPwdIdentified(checkpwd, pwd)) {
                //비밀번호 일치하면 계정 생성 후 로그인 화면으로 이동
                val email = intent.getStringExtra("email").toString()
   /*             makeUser(email, pwd)*/
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            } else {
                //비밀번호 불일치 시
                run {
                    val dialog = SignupDialog(this, "비밀번호가 일치하지않습니다.")
                    dialog.isCancelable = false
                    dialog.show(this.supportFragmentManager, "SignupDialog")

                }
            }
        }
    }

    //비밀번호 일치 검사
    private fun isPwdIdentified(checkpwd: String, pwd: String) : Boolean {
        return pwd == checkpwd
    }

/*    //계정 생성
    private fun makeUser(email: String, pwd: String) : Boolean {
        return TODO("서버에 입력받은 email, pwd로 사용자 계정 생성")
    }*/

    override fun onDialogButtonClick() {
        finish()
    }


}