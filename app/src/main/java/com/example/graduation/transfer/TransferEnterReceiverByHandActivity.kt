package com.example.graduation.transfer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.databinding.ActivityTransferEnterReceiverByHandBinding
import java.util.Locale


//받는 사람 입력 목소리 대신 손으로 입력하기

class TransferEnterReceiverByHandActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferEnterReceiverByHandBinding
    lateinit var mtts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferEnterReceiverByHandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences =getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)


        //화면 정보 읽기
        if (soundState) {
            onSpeech(binding.titleTv.text)
        }

        binding.prevBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }
            startActivity(Intent(this, TransferVoiceActivity::class.java))
        }


        binding.nextBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }
            // 입력된 이름을 SharedPreferences에 저장하기
            val sharedPreferences = getSharedPreferences("transferInfo", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("recipientName", binding.receiverNameEt.text.toString())
            editor.apply()


            startActivity(Intent(this,  TransferEnterAccountNumberActivity::class.java))
        }



    }


    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }



}