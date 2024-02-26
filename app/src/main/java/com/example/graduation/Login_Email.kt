package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.example.graduation.databinding.ActivityLoginEmailBinding
import java.util.Locale

class Login_Email : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityLoginEmailBinding
    private lateinit var textToSpeech: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*val db = "DB 경로"*/
        textToSpeech = TextToSpeech(this, this)

        binding.enter.setOnClickListener {
            val user = binding.loginInputEmail.text.toString()
            if (isEmailValid(user)) {
                if (isEmailRegistered(user)) {
                    val intent = Intent(this, Login_Pwd::class.java)
                    intent.putExtra("email", user) //이메일 값 넘겨주기
                    startActivity(intent) }
                else{
                    //등록되지 않은 이메일인 경우
                    Toast.makeText(this, "가입되지 않은 이메일입니다.", Toast.LENGTH_SHORT).show()
                    speakOut("가입되지 않은 이메일입니다.") }
            }
            else {
                //이메일 형식 오류일 경우
                Toast.makeText(this, "이메일을 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                speakOut("이메일을 다시 확인해주세요.") }
        }
    }

    //이메일 형식 검사
    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //이메일 등록 여부 검사
    private fun isEmailRegistered(email: String): Boolean {
        //이메일 등록 여부 확인 로직  db.collection("users").whereEqualTo("email", email)
        return TODO("Provide the return value")
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
}

