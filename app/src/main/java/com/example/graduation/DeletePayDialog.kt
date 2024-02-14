package com.example.graduation

import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.graduation.databinding.DeleteConfirmationDialogBinding
import com.example.graduation.databinding.DeletePayDialogBinding

class DeletePayDialog(private val context: AppCompatActivity) {

    private lateinit var binding: DeletePayDialogBinding
    private val dlg = Dialog(context)   // 부모 액티비티의 context 가 들어감

    fun show(content: String) {
        binding =  DeletePayDialogBinding.inflate(context.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   // 타이틀바 제거
        dlg.setContentView(binding.root)     // 다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    // 다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        binding.contentTv.text = content // 부모 액티비티에서 전달 받은 텍스트 세팅

        // ok 버튼 동작
        binding.okBtn.setOnClickListener {
            showSecondDialog()

            // TODO: 부모 액티비티로 내용을 돌려주기 위해 작성할 코드

            dlg.dismiss()
        }

        //cancel 버튼 동작
        binding.cancelBtn.setOnClickListener {
            dlg.dismiss()
        }


        dlg.show()
    }

    private fun showSecondDialog() {
        val secondDialogBinding = DeleteConfirmationDialogBinding.inflate(context.layoutInflater)
        val secondDialog = Dialog(context)
        secondDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        secondDialog.setContentView(secondDialogBinding.root)


        // ok 버튼 동작
        secondDialogBinding.okBtn.setOnClickListener {
            // TODO: 작성할 코드
            secondDialog.dismiss()
        }
        secondDialog.show()
    }
}

