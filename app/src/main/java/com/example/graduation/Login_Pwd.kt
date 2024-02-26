package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.example.graduation.databinding.ActivityLoginPwdBinding
import java.security.MessageDigest
import java.util.*

class Login_Pwd : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityLoginPwdBinding
    private lateinit var textToSpeech: TextToSpeech
    /* val db = "DB 경로" */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPwdBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login_pwd)
        setContentView(binding.root)
        /*auth = com.google.firebase.ktx.Firebase.auth */

        textToSpeech = TextToSpeech(this, this)
        binding.enter.setOnClickListener {
            val pwd = binding.loginInputPwd.text.toString()
            val email = intent.getStringExtra("email")
            if (pwd == "") {
                //비밀번호 공백 입력 시
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                speakOut("비밀번호를 입력해주세요.")
            }
            else {
                //입력한 비밀번호를 해시하여 DB에 저장된 비밀번호와 비교
                val hashedPwd = hashPassword(pwd)
                val dbPassword = "password" /* <= "email에 해당하는 사용자의 DB 경로".get("password").toString() */
                if (hashedPwd == dbPassword) {
                    /* 로그인 로직
                    auth.signInWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                LoginInformation.setCurrentLoginUserWithUID(auth.currentUser!!.uid, 1)
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                        } */
                }
                else {
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    speakOut("비밀번호가 일치하지 않습니다.") }
            }
        }

        //비밀번호 찾기 화면 연결
        /* binding.resetPwd.setOnClickListener {
            val intent = Intent("비밀번호 찾기 화면"::class.java)
            startActivity(intent)
        }*/
    }

    //음성 안내
    override fun onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.KOREAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "언어 데이터가 없거나 지원되지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "TextToSpeech 초기화 실패", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    //입력한 비밀번호를 해시하는 함수
    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashBytes = md.digest(password.toByteArray())
        val hexString = StringBuilder()
        for (byte in hashBytes) {
            val hex = Integer.toHexString(0xff and byte.toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    }
}