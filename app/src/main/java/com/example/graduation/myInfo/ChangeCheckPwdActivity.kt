package com.example.graduation.myInfo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.graduation.Login
import com.example.graduation.SignupDialog
import com.example.graduation.SignupDialogInterface
import com.example.graduation.databinding.ActivityChangeCheckPwdBinding
import java.util.Locale

class ChangeCheckPwdActivity : AppCompatActivity(), SignupDialogInterface {

    private lateinit var binding: ActivityChangeCheckPwdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeCheckPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.enterButton.setOnClickListener {
            val checkpwd = binding.signupInputCheckpwd.text.toString().trim()
            val pwd = intent.getStringExtra("pwd").toString()
            if (isPwdIdentified(checkpwd, pwd)) {
                //비밀번호 일치하면 비밀번호 변경 완료 화면으로 이동
                val email = intent.getStringExtra("email").toString()

                    //TODO:변경된 비밀번호를 백엔드 서버에 전송 및 저장
                val intent = Intent(this, ChangePwdCompletedFragment::class.java)
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