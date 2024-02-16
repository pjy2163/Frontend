package com.example.graduation

import androidx.fragment.app.DialogFragment
import com.example.graduation.databinding.DialogSignupBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

interface SignupDialogInterface {
    fun onDialogButtonClick()
}

class SignupDialog(signupDialogInterface: SignupDialogInterface, text: String,) : DialogFragment() {
    // 뷰 바인딩 정의
    private var _binding: DialogSignupBinding? = null
    private val binding get() = _binding!!

    private var signupDialogInterface: SignupDialogInterface? = null
    private var text: String? = null

    init {
        this.text = text
        this.signupDialogInterface = signupDialogInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSignupBinding.inflate(inflater, container, false)
        val view = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogText.text = text

        binding.dialogButton.setOnClickListener {
            dismiss()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
