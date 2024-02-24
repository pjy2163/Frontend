package com.example.graduation

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.graduation.databinding.ActivityMainBinding
import com.example.graduation.databinding.FragmentSplash1Binding
import com.example.graduation.databinding.FragmentSplash2Binding

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

        binding = FragmentSplash1Binding.inflate(layoutInflater)
        return inflater.inflate(R.layout.fragment_splash1, container, false)

    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}