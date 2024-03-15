package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.graduation.databinding.ActivitySignupCheckpwdBinding
import com.example.graduation.model.User
import com.example.graduation.retrofit.RetrofitService
import com.example.graduation.retrofit.UserApi
import com.example.graduation.retrofit.UserApi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale
import java.util.logging.Level
import java.util.logging.Logger

class Signup_Checkpwd : AppCompatActivity(), SignupDialogInterface {

    private lateinit var binding: ActivitySignupCheckpwdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupCheckpwdBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val retrofitService = RetrofitService()
        val userApi: UserApi = retrofitService.retrofit.create(UserApi::class.java)
        binding.enterButton.setOnClickListener {

            //val id = intent.getStringExtra("id").toString()
            val password = intent.getStringExtra("password").toString()
            val checkpwd = binding.signupInputCheckpwd.text.toString().trim()

            val user = User()
           // user.id = id
           // user.password = password


            if (isPwdIdentified(checkpwd, password)) {
                //비밀번호 일치하면 계정 생성 후 로그인 화면으로 이동
                //val id = intent.getStringExtra("id").toString()
                userApi.save(user)
                    .enqueue(object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            Toast.makeText(this@Signup_Checkpwd, "ok!!!", Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Toast.makeText(this@Signup_Checkpwd, "fail!!!", Toast.LENGTH_SHORT).show()
                            Logger.getLogger(Signup_Checkpwd::class.java.name).log(Level.SEVERE, "에러")
                        }
                    })

                /*             makeUser(email, pwd)*/

                val intent = Intent(this, Login::class.java)
                startActivity(intent)

            } else {
                //비밀번호 불일치 시
                run {
                    val dialog = SignupDialog(this, "비밀번호가 일치하지않습니다.")
                    dialog.isCancelable = false
                    dialog.show(this.supportFragmentManager, "SignupDialog")

                }
            }
        }
    }

    //비밀번호 일치 검사
    private fun isPwdIdentified(checkpwd: String, password: String) : Boolean {
        return password == checkpwd
    }

    /*    //계정 생성
        private fun makeUser(email: String, pwd: String) : Boolean {
            return TODO("서버에 입력받은 email, pwd로 사용자 계정 생성")
        }*/

    override fun onDialogButtonClick() {
        finish()
    }

}