package com.example.graduation

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.FragmentTransaction
import com.example.graduation.databinding.ActivityMainBinding
import com.example.graduation.databinding.ActivityRegisterMainBinding
import java.util.Locale

class RegisterMainActivity : AppCompatActivity() {

    lateinit var mtts:TextToSpeech
    private lateinit var binding: ActivityRegisterMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var soundState: Boolean = false //처음에는 화면 안내 소리 끔 상태

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        //계좌버튼 클릭 이벤트 처리
        binding.accountBtn.setOnClickListener{
            onSpeech(binding.accountBtn.text)

            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, ChooseBankFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        //카드버튼 클릭 이벤트 처리
        binding.cardBtn.setOnClickListener {
            onSpeech(binding.cardBtn.text)
            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, ChooseBankFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        binding.prevBtn.setOnClickListener{
            onSpeech(binding.prevBtn.text)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}