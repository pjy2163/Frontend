package com.example.graduation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.databinding.ActivityMainBinding
import com.example.graduation.databinding.ActivityMyInfoBinding
import com.example.graduation.databinding.ActivitySignupEmailBinding
import java.util.Locale

class SignupEmailActivity : AppCompatActivity(), SignupDialogInterface {

    lateinit var mtts:TextToSpeech
    private lateinit var binding: ActivitySignupEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupEmailBinding.inflate(layoutInflater)


        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        setContentView(binding.root)

        clickViewEvents()}

    private fun clickViewEvents() {
        binding.enterButton.setOnClickListener {
            //이메일 중복 시 (추후 작성)
            run {
                val dialog = SignupDialog(this, "이미 가입한 이메일입니다.")
                dialog.isCancelable = false
                dialog.show(this.supportFragmentManager, "SignupDialog")
            }
            //이메일 중복 아니면 저장 후 다음 화면으로 이동 (추후 작성)
        }
    }
    override fun onDialogButtonClick() {
        finish()
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}