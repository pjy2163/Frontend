package com.example.graduation.transfer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import com.example.graduation.R
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
        mtts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val titleText = binding.titleTv.text.toString()
                val explainText = binding.explainTv.text.toString()
                val textToSpeak = "$titleText $explainText"
                onSpeech(textToSpeak)
            } else {
                // 초기화가 실패한 경우
                Log.e("TTS", "TextToSpeech 초기화 실패")
            }
        }

        binding.receiverNameEt.addTextChangedListener(textWatcher)

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

    // 클래스 내부 또는 외부에 TextWatcher 구현
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // 입력 전 동작
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // 입력 중 동작
            val inputText = s.toString().trim()
            if (inputText.isNotEmpty()) {
                setButtonBlue(binding.nextBtn)
            } else {
                setButtonDefault(binding.nextBtn)
            }
        }

        override fun afterTextChanged(s: Editable?) {
            // 입력 후 동작
        }

        private fun setButtonBlue(button: Button) {
            button.setBackgroundResource(R.color.blue) // 파란색으로 변경
        }

        private fun setButtonDefault(button: Button) {
            // 기본 색상으로 변경하려는 경우 해당 버튼의 기본 배경색을 지정해야 합니다.
            button.setBackgroundResource(R.color.light_grey) // 기본 색상으로 변경
        }
    }

}