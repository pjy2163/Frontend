package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.myInfo.MyInfoActivity
import com.example.graduation.databinding.ActivityMainBinding
import com.example.graduation.managePay.EditPayActivity
import com.example.graduation.transfer.TransferActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mtts:TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }


        //화면 정보 읽기
        if (soundState) {
            onSpeech("소리페이 메인 화면입니다.")
        }


        //결제하기 버튼
        binding.payBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.payBtn.text)
            }
            val intent = Intent(this, CheckPayInfoActivity::class.java)
            startActivity(intent)
        }

        //송금 버튼
        binding.transferBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.transferBtn.text)
            }
            val intent = Intent(this, TransferActivity::class.java)
            startActivity(intent)
        }


        //결제수단 관리 버튼
        binding.editPayBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.editPayBtn.text)
            }
            val intent = Intent(this, EditPayActivity::class.java)
            startActivity(intent)
        }

        //정보수정 버튼
        binding.editInfoBtn.setOnClickListener{
            onSpeech(binding.editInfoBtn.text)
            val intent = Intent(this, MyInfoActivity::class.java)
            startActivity(intent)
        }


    }

    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }


}
