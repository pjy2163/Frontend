package com.example.graduation.register

import android.content.SharedPreferences
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import com.example.graduation.databinding.ActivityRegisterAccountInfoEnterBinding
import java.util.Locale

class RegisterAccountInfoEnterActivity : AppCompatActivity(){
    private lateinit var binding: ActivityRegisterAccountInfoEnterBinding
    lateinit var mtts: TextToSpeech
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAccountInfoEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        // SharedPreferences에서 소리 on/off 상태 불러오기
        sharedPreferences = getSharedPreferences("sp1", MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)


        //음성으로 입력한 계좌번호를 텍스트뷰에 반영하기
        val intent = intent
        val recognizedText = intent.getStringExtra("recognizedText")
        binding.accountNumberTv.text = recognizedText

        if (soundState){
            onSpeech("등록할 계좌 정보 확인 화면입니다. 입력된 계좌번호는"+recognizedText+"입니다. 다시 입력하시려면 화면 상단의 이전 화면 버튼을 눌러주세요. 정상적으로 입력되었다면 화면 하단의 다음 버튼을 눌러주세요.")
        }

    }


    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }


}