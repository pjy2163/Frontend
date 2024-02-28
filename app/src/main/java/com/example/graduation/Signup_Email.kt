package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.example.graduation.databinding.ActivitySignupEmailBinding
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.Locale

class Signup_Email : AppCompatActivity(), SignupDialogInterface {

    private lateinit var binding: ActivitySignupEmailBinding
    lateinit var mtts: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        //화면 정보 읽기
        if (soundState) {
            onSpeech("회원가입 이메일 입력 화면입니다.")
        }

        binding.enterButton.setOnClickListener {
            val email = binding.signupInputEmail.text.toString().trim()
            if (isEmailValid(email)) {
                if (isEmailAvailable(email)) {
                    //등록 가능한 이메일인 경우
                    val intent1 = Intent(this, Signup_Pwd::class.java)
                    val intent2 = Intent(this, Signup_Checkpwd::class.java)
                    intent2.putExtra("email", email) //이메일 값 전달
                    startActivity(intent1)
                } else {
                    //등록된 이메일인 경우
                    run {
                        val dialog = SignupDialog(this, "이미 등록된 이메일입니다.")
                        dialog.isCancelable = false
                        dialog.show(this.supportFragmentManager, "SignupDialog")
                        if (soundState) {
                            onSpeech("이미 등록된 이메일입니다.")
                        }
                    }
                }
            } else {
                //이메일 형식 오류인 경우
                Toast.makeText(this, "이메일을 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                if (soundState) {
                    onSpeech("이메일을 다시 확인해주세요.")
                }
            }
        }
    }

    //이메일 형식 검사
    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //이메일 등록 여부 검사
    private fun isEmailAvailable(email: String): Boolean {
        //JDBC 연결
        val url = "DB url 넣기"
        val username = "username"
        val password = "password"
        // DB 연결
        var connection: Connection? = null
        var isAvailable = true

        try {
            connection = DriverManager.getConnection(url, username, password)
            //SQL 쿼리를 이용해서 이메일이 DB에 존재하는지 확인
            val sql = "SELECT COUNT(*) FROM users WHERE email = ?"
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, email)
            val resultSet = preparedStatement.executeQuery()
            //이미 존재한다면 isAvailable 값을 false로 설정
            if (resultSet.next()) {
                val count = resultSet.getInt(1)
                isAvailable = count == 0 //0이면 사용 가능, 그 외에는 이미 존재하는 이메일
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            //연결 닫기
            try {
                connection?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        return isAvailable
    }

    override fun onDialogButtonClick() {
        finish()
    }

    //음성 안내
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }
}