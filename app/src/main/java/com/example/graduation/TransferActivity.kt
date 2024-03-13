package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.databinding.ActivityTransferBinding
import java.util.Locale

class TransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding
    lateinit var mtts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_transfer)
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        //화면 정보 읽기
        if (soundState) {
            onSpeech("송금 방법 선택 화면입니다.")
        }

        //이전화면 버튼
        binding.prevBtn.setOnClickListener{
            onSpeech(binding.prevBtn.text)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //사진으로 송금하기 버튼
        binding.transferWithPictureBtn.setOnClickListener{
            onSpeech(binding.transferWithPictureBtn.text)
            val intent = Intent(this, TransferPicActivity::class.java)
            startActivity(intent)
        }

        //음성으로 송금하기 버튼
        binding.transferWithVoiceBtn.setOnClickListener{
            onSpeech(binding.transferWithVoiceBtn.text)
            val intent = Intent(this, TransferVoiceActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }
}