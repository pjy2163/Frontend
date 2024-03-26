package com.example.graduation.managePay

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.graduation.MainActivity
import com.example.graduation.R
import com.example.graduation.databinding.FragmentRegisterCompletedBinding
import java.util.Locale

class RegisterCompletedFragment : Fragment() {
    private lateinit var binding: FragmentRegisterCompletedBinding
    lateinit var mtts: TextToSpeech
    lateinit var mediaPlayerSuccess: MediaPlayer
    lateinit var mediaPlayerFailure: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterCompletedBinding.inflate(layoutInflater)

        // 데이터 받아오기
        val recognizedAccountNumberText = arguments?.getString("recognizedText")


        mtts = TextToSpeech(requireActivity()) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = requireActivity().getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        // 효과음 초기화
        mediaPlayerSuccess = MediaPlayer.create(requireActivity(), R.raw.success_sound)
        mediaPlayerFailure = MediaPlayer.create(requireActivity(), R.raw.failure_sound)

        playSuccessSound() //완료되었다는 띠링 소리

        //화면 정보 읽기
        mtts = TextToSpeech(requireActivity()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // 화면 정보 읽어주기
                val textToSpeak =binding.titleTv.text
                onSpeech(textToSpeak)
            } else {
                // 초기화가 실패한 경우
                Log.e("TTS", "TextToSpeech 초기화 실패")
            }
        }


        binding.nextBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }

            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }

        return binding.root
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun playSuccessSound() { //성공 사운드
        mediaPlayerSuccess.start()
    }



    override fun onDestroy() {
        super.onDestroy()
        mtts.shutdown()
        mediaPlayerSuccess.release()
        mediaPlayerFailure.release()
    }



}