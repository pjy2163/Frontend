package com.example.graduation

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduation.databinding.FragmentRegisterCompletedBinding
import java.util.Locale

//TODO:Activity로 바꿀까..

//결제승인 확인 하면 뜨는 결제 완료 프래그먼트

class RegisterCompletedFragment : Fragment() {
    private lateinit var binding: FragmentRegisterCompletedBinding
    lateinit var mtts: TextToSpeech

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

        //화면 정보 읽기
        if (soundState) {
            onSpeech(binding.registerCompletedTv.text)
        }


        return binding.root
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }


}