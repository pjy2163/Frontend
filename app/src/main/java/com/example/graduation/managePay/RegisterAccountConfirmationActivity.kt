package com.example.graduation.managePay

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import com.example.graduation.R
import com.example.graduation.databinding.ActivityRegisterAccountConfirmationBinding
import java.util.Locale

class RegisterAccountConfirmationActivity : AppCompatActivity(){
    private lateinit var binding: ActivityRegisterAccountConfirmationBinding
    lateinit var mtts: TextToSpeech
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityRegisterAccountConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        // SharedPreferences에서 소리 on/off 상태 불러오기
        sharedPreferences = getSharedPreferences("sp1", MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        binding.prevBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }
            startActivity(Intent(this, RegisterAccountNumberActivity::class.java))
        }


        //앞에서 입력한 은행명, 계좌번호 데이터 가져와서 텍스트뷰에 반영
        val sharedPreferences2 = getSharedPreferences("registerInfo", Context.MODE_PRIVATE)
        val bankName = sharedPreferences2.getString("bankName", "")
        val accountNumber = sharedPreferences2.getString("accountNum", "")
        binding.bankNameTv.text=bankName
        binding.accountNumberTv.text=accountNumber.toString()

        //다음버튼 누르면 계좌등록 화면으로 넘어가기
        binding.nextBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }
            val fragment = RegisterCompletedFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_basic, fragment)
                .addToBackStack(null)
                .commit()
        }
    }


    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }
    override fun onDestroy() {
        super.onDestroy()
        mtts.shutdown()

    }

}