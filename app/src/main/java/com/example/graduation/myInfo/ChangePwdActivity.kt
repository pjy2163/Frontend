package com.example.graduation.myInfo

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.databinding.ActivityChangePwdBinding
import java.util.Locale


class ChangePwdActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var mtts:TextToSpeech
    private lateinit var binding: ActivityChangePwdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePwdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

    }

    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}