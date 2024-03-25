package com.example.graduation.transfer

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import com.example.graduation.MainActivity
import com.example.graduation.R
import com.example.graduation.databinding.ActivityTransferCompletedBinding
import java.util.*


//결제승인 확인 하면 뜨는 결제 완료 프래그먼트

class TransferCompletedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferCompletedBinding
    lateinit var mtts: TextToSpeech
    lateinit var mediaPlayerSuccess: MediaPlayer
    lateinit var mediaPlayerFailure: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences =getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        // 효과음 초기화
        mediaPlayerSuccess = MediaPlayer.create(this, R.raw.success_sound)
        mediaPlayerFailure = MediaPlayer.create(this, R.raw.failure_sound)

        playSuccessSound() //완료되었다는 띠링 소리

        /*//화면 정보 읽기
        if (soundState) {
            onSpeech(binding.payCompletedTv.text)
        }*/

        binding.nextBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }
            startActivity(Intent(this, MainActivity::class.java))
        }



    }


    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun playSuccessSound() { //결제 성공시 사운드
        mediaPlayerSuccess.start()
    }

    private fun playFailureSound() { //결제 실패시 사운드
        mediaPlayerFailure.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mtts.shutdown()
        mediaPlayerSuccess.release()
        mediaPlayerFailure.release()
    }


}