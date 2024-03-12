package com.example.graduation.transfer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.graduation.R
import com.example.graduation.databinding.ActivityTransferConfirmationBinding
import java.util.Locale

class TransferConfirmationActivity : AppCompatActivity() {

    lateinit var mtts:TextToSpeech
    private lateinit var binding: ActivityTransferConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        //목소리 입력과 카메라 촬영 중 무엇을 사용하여 계좌번호(to)를 입력하였는지 정보
        val window=sharedPreferences.getString("window", "a")
        if (window != null) {
            Log.d("TransferConfirmationActivity", window)

            if (window=="pic"){ //카메라 촬영으로 계좌번호(to)를 입력받은 경우
                //어느 계좌로 보낼 것인지(to) transferPicActivity에서 가져오기
                val toAccount=sharedPreferences.getString("toAccount", "123")
                binding.toAccountNumberTv.text=toAccount
            }
            else{ //목소리 입력으로 계좌번호(to)를 입력받은 경우
                //어느 계좌로 보낼 것인지(to) transferVoiceActivity에서 가져오기
                val toAccount=sharedPreferences.getString("toAccount", "123")
                binding.toAccountNumberTv.text=toAccount
            }
        }


        //선택된 은행계좌와 계좌번호(from) 가져와서 텍스트뷰에 반영
        val sharedPreferences3 = getSharedPreferences("sp3", Context.MODE_PRIVATE)
        val selectedAccountName = sharedPreferences3.getString("selectedAccountName", "")
        val selectedAccountNumber = sharedPreferences3.getString("selectedAccountNumber", "")
        binding.chosenPaymentNameText.text =selectedAccountName
        binding.chosenPaymentNumberText.text=selectedAccountNumber

        //소리 설정
        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        binding.prevBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }

            val intent = Intent(this, TransferVoiceActivity::class.java)
            startActivity(intent)
        }

        //TODO:결제승인 과정에서 오류시 else 처리
        binding.approveBtn.setOnClickListener {

            if (soundState) {
                onSpeech(binding.approveBtn.text)
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_basic, TransferCompletedFragment())
                .commit()


        }
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}