package com.example.graduation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.graduation.databinding.ActivitySoundSplashBinding
import java.util.Locale

class SplashSoundActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySoundSplashBinding
    lateinit var mtts:TextToSpeech
    private lateinit var sharedPreferences: SharedPreferences
    private var soundState: Boolean = false //처음에는 화면 안내 소리 끔 상태

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySoundSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        //화면 안내 소리 켜기 버튼 이벤트 처리
        binding.soundOnBtn.setOnClickListener{
            val currentSoundState=binding.soundOnBtn.text

            if (currentSoundState=="화면 안내 소리 켜기"){ //화면 안내 소리 기능 켜져있을 때 버튼 누르면 소리 꺼짐
                //소리 킴
                onSpeech("화면 안내 소리를 켰습니다")
                Toast.makeText(this, "화면 안내 소리를 켰습니다.", Toast.LENGTH_SHORT).show()
                soundState=true

                //텍스트는 소리 끄기로 선택지 변경
                binding.soundOnBtn.text="화면 안내 소리 끄기"
            }
            else if (currentSoundState=="화면 안내 소리 끄기"){ //화면 안내 소리 기능 꺼져있을 때 버튼 누르면 소리 켜짐
                //소리 끔
                onSpeech("화면 안내 소리를 껐습니다")
                Toast.makeText(this, "화면 안내 소리를 껐습니다.", Toast.LENGTH_SHORT).show()
                soundState=true

                //텍스트는 소리 켜기로 선택지 변경
                binding.soundOnBtn.text="화면 안내 소리 켜기"

            }
            saveSoundOnState() //소리켜기 버튼 on/off상태 저장

        }

        binding.nextBtn.setOnClickListener{
            val Intent = Intent(this, Login::class.java)
            startActivity(Intent)
        }
    }

    private fun saveSoundOnState() { //소리 on/off 상태를 SharedPreference에 저장함

        sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("soundState", soundState)
        editor.apply()
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }


}