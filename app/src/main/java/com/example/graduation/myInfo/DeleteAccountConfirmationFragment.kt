package com.example.graduation.myInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.graduation.R
import com.example.graduation.databinding.FragmentDeleteAccountConfirmationBinding
import java.util.Locale

//정말로 탈퇴하시겠습니까?
class DeleteAccountConfirmationFragment: Fragment() {
    private lateinit var binding: FragmentDeleteAccountConfirmationBinding
    lateinit var mtts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteAccountConfirmationBinding.inflate(layoutInflater)

        mtts = TextToSpeech(requireActivity()) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = requireActivity().getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        //화면 정보 읽기
        mtts = TextToSpeech(requireActivity()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val textToSpeak =binding.titleTv.text
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
            startActivity(Intent(requireActivity(), MyInfoActivity::class.java))
        }

        binding.nextBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }
            // "탈퇴하기" 버튼과 이전화면 버튼을 숨깁니다.
            binding.prevBtn.visibility = View.GONE
            binding.nextBtn.visibility = View.GONE

            val fragment = DeleteAccountCompletedFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_bottom, fragment)
            transaction.addToBackStack(null) // 백 스택에 추가하여 뒤로가기 버튼으로 되돌릴 수 있도록 함
            transaction.commit()
        }
        return binding.root
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }





}