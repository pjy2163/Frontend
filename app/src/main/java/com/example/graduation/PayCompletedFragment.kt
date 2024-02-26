package com.example.graduation

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.graduation.databinding.FragmentPayCompletedBinding
import java.util.*


//결제승인 확인 하면 뜨는 결제 완료 프래그먼트

class PayCompletedFragment : Fragment() {
    private lateinit var binding: FragmentPayCompletedBinding
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
        binding = FragmentPayCompletedBinding.inflate(layoutInflater)

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
        if (soundState) {
            onSpeech("결제가 완료되었습니다. 잠시 후 메인화면으로 이동합니다.")
        }

        //TODO:아직 안됨 5초 후 메인화면으로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToMainActivity()
        }, 5000)


        return binding.root
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun playSuccessSound() {
        mediaPlayerSuccess.start()
    }

    private fun playFailureSound() {
        mediaPlayerFailure.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mtts.shutdown()
        mediaPlayerSuccess.release()
        mediaPlayerFailure.release()
    }

    private fun navigateToMainActivity() {
        val navController = findNavController()
        navController.navigate(R.id.action_payCompletedFragment_to_mainActivity)
    }
}