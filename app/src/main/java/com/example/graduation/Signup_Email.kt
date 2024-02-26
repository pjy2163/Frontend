package com.example.graduation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduation.databinding.ActivitySignupEmailBinding

class Signup_Email : AppCompatActivity(), SignupDialogInterface {

    private lateinit var binding: ActivitySignupEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickViewEvents()}

    private fun clickViewEvents() {
        binding.enterButton.setOnClickListener {
            //이메일 중복 시 (추후 작성)
            run {
                val dialog = SignupDialog(this, "이미 가입한 이메일입니다.")
                dialog.isCancelable = false
                dialog.show(this.supportFragmentManager, "SignupDialog")
            }
            //이메일 중복 아니면 저장 후 다음 화면으로 이동 (추후 작성)
        }
    }
    override fun onDialogButtonClick() {
        finish()
    }
}