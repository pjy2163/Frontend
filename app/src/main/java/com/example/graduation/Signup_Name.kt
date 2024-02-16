package com.example.graduation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduation.databinding.ActivitySignupNameBinding

class Signup_Name : AppCompatActivity(), SignupDialogInterface {

    private lateinit var binding: ActivitySignupNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickViewEvents()}

    private fun clickViewEvents() {
        binding.enterButton.setOnClickListener {
            //닉네임 중복 시 (추후 작성)
            run {
                val dialog = SignupDialog(this, "이미 사용중인 닉네임입니다.")
                dialog.isCancelable = false
                dialog.show(this.supportFragmentManager, "SignupDialog")
            }
            //닉네임 중복 아니면 저장 후 다음 화면으로 이동(추후 작성)
        }
        }

    override fun onDialogButtonClick() {
        finish()
    }
}