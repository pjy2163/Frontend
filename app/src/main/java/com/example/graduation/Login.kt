package com.example.graduation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.graduation.databinding.ActivityLoginBinding
import java.util.concurrent.Executor


class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "에러", Toast.LENGTH_SHORT).show()
                }
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT).show()
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("지문 인증")
            .setSubtitle("기기에 등록된 지문을 이용하여 지문을 인증해주세요.")
            .setNegativeButtonText("취소")
            .build()

        //지문 로그인 버튼 클릭 시 인식
        binding.FingerLogin.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)
        }

        //비밀번호 로그인 화면 연결
        binding.PwdLogin.setOnClickListener {
            val intent = Intent(this,Login_Email::class.java)
            startActivity(intent)
        }

        //회원가입 화면 연결
        binding.SignUp.setOnClickListener {
            val intent = Intent(this,Signup_Email::class.java)
            startActivity(intent)
        }
    }
}