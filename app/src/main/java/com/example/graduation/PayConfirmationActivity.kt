package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.graduation.databinding.ActivityMainBinding
import com.example.graduation.databinding.ActivityPayConfirmationBinding
import java.util.Locale

class PayConfirmationActivity : AppCompatActivity() {

    lateinit var mtts:TextToSpeech
    private lateinit var binding: ActivityPayConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityPayConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        binding.prevBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }

            val intent = Intent(this,PayPasswordActivity::class.java)
            startActivity(intent)
        }

        //TODO:결제승인 과정에서 오류시 else 처리
        binding.approveBtn.setOnClickListener {

            if (soundState) {
                onSpeech(binding.approveBtn.text)
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_basic, PayCompletedFragment())
                .commit()


        }
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}