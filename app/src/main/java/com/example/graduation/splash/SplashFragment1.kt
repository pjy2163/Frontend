package com.example.graduation.splash

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.graduation.databinding.ActivityMainBinding
import com.example.graduation.databinding.FragmentSplash1Binding
import com.example.graduation.databinding.FragmentSplash2Binding
import java.util.Locale

class SplashFragment1 : Fragment() {

    private lateinit var binding: FragmentSplash1Binding
    lateinit var mtts:TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = requireActivity().getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        mtts = TextToSpeech(requireActivity()) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        //화면 정보 읽기
        if (soundState) {
            onSpeech("소리페이 메인 화면입니다.")
        }

        binding = FragmentSplash1Binding.inflate(layoutInflater)
        return binding.root

    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}