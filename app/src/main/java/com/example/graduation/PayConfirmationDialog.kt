package com.example.graduation

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.graduation.databinding.PayConfirmationDialogBinding

class PayConfirmationDialog: DialogFragment()  {

    private var _binding: PayConfirmationDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = PayConfirmationDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        dialog?.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함


        binding.okBtn.setOnClickListener {
            Log.d("yk","확인 버튼 클릭")
            dismiss()    // 대화상자를 닫는 함수
        }

        //메인으로 버튼 동작
        binding.gotoHomeBtn.setOnClickListener {
            dismiss()    // 대화상자를 닫는 함수

            //메인화면으로 이동
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)

        }

        return view
    }

}
