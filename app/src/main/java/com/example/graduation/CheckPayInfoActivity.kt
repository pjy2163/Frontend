package com.example.graduation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.databinding.ActivityCheckPayInfoBinding
import java.util.Locale

class CheckPayInfoActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var mtts:TextToSpeech
    private lateinit var binding: ActivityCheckPayInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckPayInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //결제할 품목 더미데이터
        val dummyPayment = PaymentInfo("ExampleStore", "SampleProduct", 29.99)


        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        if (soundState) {
            onSpeech("결제 정보 확인 화면입니다")
        }

        binding.prevBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.nextBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }

            val intent = Intent(this, ChoosePayMethodActivity::class.java)
            startActivity(intent)
        }
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}
