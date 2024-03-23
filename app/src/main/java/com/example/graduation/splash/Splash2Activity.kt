package com.example.graduation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.graduation.Login
import com.example.graduation.R
import com.example.graduation.databinding.ActivitySplash1Binding
import com.example.graduation.databinding.ActivitySplash2Binding
import java.util.Locale

class Splash2Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySplash2Binding
    lateinit var mtts:TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplash2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        onSpeech(binding.titleTv.text)
        onSpeech(binding.explainTv.text)


        // Handler를 사용하여 5초 후에 Login으로 전환
        Handler().postDelayed({
            val intent = Intent(this, SplashSoundActivity::class.java)
            startActivity(intent)
            finish() // 현재 Activity 종료
        }, 5000) //5초 후에 실행
    }

    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}