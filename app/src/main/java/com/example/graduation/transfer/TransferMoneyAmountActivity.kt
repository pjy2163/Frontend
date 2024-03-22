package com.example.graduation.transfer

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.MainActivity
import com.example.graduation.R
import com.example.graduation.databinding.ActivityTransferMoneyAmountBinding
import java.util.Locale


//결제승인 확인 하면 뜨는 결제 완료 프래그먼트

class TransferMoneyAmountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferMoneyAmountBinding
    lateinit var mtts: TextToSpeech


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferMoneyAmountBinding.inflate(layoutInflater)
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
            startActivity(Intent(this, TransferEnterAccountNumberActivity::class.java))
        }

        //뷰 객체 이용
        binding.oneTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.oneTv.text)
            }

            if (binding.moneyAmountTv.text=="금액 입력"){
                binding.moneyAmountTv.text = ""
                binding.moneyAmountTv.text = "${binding.oneTv.text}"
            }else{
                binding.moneyAmountTv.text = "${binding.moneyAmountTv.text}${binding.oneTv.text}"
            }
        }

        val moneyNum = binding.moneyAmountTv
        binding.twoTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.twoTv.text)
            }

            if (moneyNum.text=="금액 입력"){
                binding.moneyAmountTv.text = ""
                moneyNum.text = "${binding.twoTv.text}"
            }else{
                moneyNum.text = "${moneyNum.text}${binding.twoTv.text}"
            }
        }
        binding.threeTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.threeTv.text)
            }

            if (moneyNum.text=="금액 입력"){
                moneyNum.text = "${binding.threeTv.text}"
            }else{
                moneyNum.text = "${moneyNum.text}${binding.threeTv.text}"
            }
        }
        binding.fourTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.fourTv.text)
            }

            if (moneyNum.text=="금액 입력"){
                moneyNum.text = "${binding.fourTv.text}"
            }else{
                moneyNum.text = "${moneyNum.text}${binding.fourTv.text}"
            }
        }
        binding.fiveTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.fiveTv.text)
            }

            if (moneyNum.text=="금액 입력"){
                moneyNum.text = "${binding.fiveTv.text}"
            }else{
                moneyNum.text = "${moneyNum.text}${binding.fiveTv.text}"
            }
        }
        binding.sixTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.sixTv.text)
            }

            if (moneyNum.text=="금액 입력"){
                moneyNum.text = "${binding.sixTv.text}"
            }else{
                moneyNum.text = "${moneyNum.text}${binding.sixTv.text}"
            }
        }
        binding.sevenTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.sevenTv.text)
            }

            if (moneyNum.text=="금액 입력"){
                moneyNum.text = "${binding.sevenTv.text}"
            }else{
                moneyNum.text = "${moneyNum.text}${binding.sevenTv.text}"
            }
        }
        binding.eightTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.eightTv.text)
            }

            if (moneyNum.text=="금액 입력"){
                moneyNum.text = "${binding.eightTv.text}"
            }else{
                moneyNum.text = "${moneyNum.text}${binding.eightTv.text}"
            }
        }
        binding.nineTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.nineTv.text)
            }

            if (moneyNum.text=="금액 입력"){
                moneyNum.text = "${binding.nineTv.text}"
            }else{
                moneyNum.text = "${moneyNum.text}${binding.nineTv.text}"
            }
        }
        binding.zeroTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.zeroTv.text)
            }

            if (moneyNum.text=="금액 입력"){
                moneyNum.text = "${binding.zeroTv.text}"
            }else{
                moneyNum.text = "${moneyNum.text}${binding.zeroTv.text}"
            }
        }
        binding.hyphenTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.hyphenTv.text)
            }

            if (moneyNum.text=="금액 입력"){
                moneyNum.text = "${binding.hyphenTv.text}"
            }else{
                moneyNum.text = "${moneyNum.text}${binding.hyphenTv.text}"
            }
        }

        binding.deleteTv.setOnClickListener {
            if (soundState) {
                onSpeech(binding.deleteTv.text)
            }

            if (!moneyNum.text.isEmpty()) {
                moneyNum.text = moneyNum.text
                    .substring(0, moneyNum.text.length - 1)
            }
        }
    

        binding.nextBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }
            // 입력된 액수를 SharedPreferences에 저장하기
            val editor = sharedPreferences.edit()
            editor.putString("moneyAmount", moneyNum.text.toString())
            editor.apply()

            startActivity(Intent(this, TransferConfirmationActivity::class.java))
        }



    }


    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }



}