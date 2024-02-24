package com.example.graduation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduation.databinding.ActivityAuthWayBinding
import com.example.graduation.databinding.ActivityMainBinding
import com.example.graduation.databinding.FragmentAccountInfoEnterBinding
import java.util.Locale


class AccountInfoEnterFragment : Fragment() {
    private lateinit var binding: FragmentAccountInfoEnterBinding
    lateinit var mtts:TextToSpeech
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountInfoEnterBinding.inflate(layoutInflater)

        sharedPreferences = requireActivity().getSharedPreferences("sp1", Context.MODE_PRIVATE)
        mtts = TextToSpeech(requireActivity()) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        return inflater.inflate(R.layout.fragment_account_info_enter, container, false)
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }


}