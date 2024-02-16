package com.example.graduation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduation.databinding.ActivitySignupPwdBinding

class Signup_Pwd : AppCompatActivity(), SignupDialogInterface {

    private lateinit var binding: ActivitySignupPwdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickViewEvents()}

    private fun clickViewEvents() {
        binding.enterButton.setOnClickListener {
            //비밀번호 조건 미충족 시 (추후 작성)
            run {
                val dialog = SignupDialog(this, "숫자, 영문이 포함된\n8~16자리를 입력해주세요.")
                dialog.isCancelable = false
                dialog.show(this.supportFragmentManager, "SignupDialog")
            }
            //비밀번호 조건 충족 시 저장 후 다음 화면으로 이동(추후 작성)
        }
    }

    override fun onDialogButtonClick() {
        finish()
    }
}