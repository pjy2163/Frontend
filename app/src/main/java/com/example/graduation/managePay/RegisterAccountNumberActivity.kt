package com.example.graduation.managePay

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.graduation.MainActivity
import com.example.graduation.R
import com.example.graduation.databinding.ActivityRegisterAccountNumberBinding
import com.example.graduation.databinding.ActivityTransferEnterAccountNumberBinding
import com.example.graduation.transfer.TransferMoneyAmountActivity
import com.example.graduation.transfer.TransferVoiceActivity
import java.util.Locale


class RegisterAccountNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterAccountNumberBinding
    lateinit var mtts: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAccountNumberBinding.inflate(layoutInflater)
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
                val textToSpeak = binding.titleTv.text
                onSpeech(textToSpeak)
            } else {
                // 초기화가 실패한 경우
                Log.e("TTS", "TextToSpeech 초기화 실패")
            }
        }


        binding.prevBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }
            startActivity(Intent(this, EditPayActivity::class.java))
        }

        //뷰 객체 이용
        binding.oneTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.oneTv.text)
            }

            if (binding.accountNumberTv.text=="계좌번호 입력"){
                binding.accountNumberTv.text = ""
                binding.accountNumberTv.text = "${binding.oneTv.text}"
            }else{
                binding.accountNumberTv.text = "${binding.accountNumberTv.text}${binding.oneTv.text}"
            }
        }

        val AccountNum = binding.accountNumberTv
        binding.twoTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.twoTv.text)
            }

            if (AccountNum.text=="계좌번호 입력"){
                binding.accountNumberTv.text = ""
                AccountNum.text = "${binding.twoTv.text}"
            }else{
                AccountNum.text = "${AccountNum.text}${binding.twoTv.text}"
            }
        }
        binding.threeTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.threeTv.text)
            }

            if (AccountNum.text=="계좌번호 입력"){
                binding.accountNumberTv.text = ""
                AccountNum.text = "${binding.threeTv.text}"
            }else{
                AccountNum.text = "${AccountNum.text}${binding.threeTv.text}"
            }
        }
        binding.fourTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.fourTv.text)
            }

            if (AccountNum.text=="계좌번호 입력"){
                binding.accountNumberTv.text = ""
                AccountNum.text = "${binding.fourTv.text}"
            }else{
                AccountNum.text = "${AccountNum.text}${binding.fourTv.text}"
            }
        }
        binding.fiveTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.fiveTv.text)
            }

            if (AccountNum.text=="계좌번호 입력"){
                binding.accountNumberTv.text = ""
                AccountNum.text = "${binding.fiveTv.text}"
            }else{
                AccountNum.text = "${AccountNum.text}${binding.fiveTv.text}"
            }
        }
        binding.sixTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.sixTv.text)
            }

            if (AccountNum.text=="계좌번호 입력"){
                binding.accountNumberTv.text = ""
                AccountNum.text = "${binding.sixTv.text}"
            }else{
                AccountNum.text = "${AccountNum.text}${binding.sixTv.text}"
            }
        }
        binding.sevenTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.sevenTv.text)
            }

            if (AccountNum.text=="계좌번호 입력"){
                binding.accountNumberTv.text = ""
                AccountNum.text = "${binding.sevenTv.text}"
            }else{
                AccountNum.text = "${AccountNum.text}${binding.sevenTv.text}"
            }
        }
        binding.eightTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.eightTv.text)
            }

            if (AccountNum.text=="계좌번호 입력"){
                binding.accountNumberTv.text = ""
                AccountNum.text = "${binding.eightTv.text}"
            }else{
                AccountNum.text = "${AccountNum.text}${binding.eightTv.text}"
            }
        }
        binding.nineTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.nineTv.text)
            }

            if (AccountNum.text=="계좌번호 입력"){
                binding.accountNumberTv.text = ""
                AccountNum.text = "${binding.nineTv.text}"
            }else{
                AccountNum.text = "${AccountNum.text}${binding.nineTv.text}"
            }
        }
        binding.zeroTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.zeroTv.text)
            }

            if (AccountNum.text=="계좌번호 입력"){
                binding.accountNumberTv.text = ""
                AccountNum.text = "${binding.zeroTv.text}"
            }else{
                AccountNum.text = "${AccountNum.text}${binding.zeroTv.text}"
            }
        }
        binding.hyphenTv.setOnClickListener {
            if (soundState) {
                onSpeech("하이픈")
            }

            if (AccountNum.text=="계좌번호 입력"){
                binding.accountNumberTv.text = ""
                AccountNum.text = "${binding.hyphenTv.text}"
            }else{
                AccountNum.text = "${AccountNum.text}${binding.hyphenTv.text}"
            }
        }

        binding.deleteTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.deleteTv.text)
            }

            if (!AccountNum.text.isEmpty()) {
                AccountNum.text = AccountNum.text
                    .substring(0, AccountNum.text.length - 1)
            }
        }

        binding.nextBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }
            // 입력된 계좌번호를 SharedPreferences에 저장하기
            val sharedPreferences = getSharedPreferences("registerInfo", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("bankName", AccountNum.toString())
            editor.apply()

            startActivity(Intent(this, RegisterAccountPwdActivity::class.java))
        }
    }


    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }



}