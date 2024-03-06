package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.example.graduation.databinding.ActivityLoginEmailBinding
import com.example.graduation.model.User
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.Locale

class Login_Email : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEmailBinding
    lateinit var mtts:TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        //화면 정보 읽기
        if (soundState) {
            onSpeech("로그인 이메일 입력 화면입니다.")
        }

        binding.enter.setOnClickListener {
            val user = binding.loginInputEmail.text.toString()
            if (isEmailValid(user)) {
                if (isEmailRegistered(user)) {
                    val intent = Intent(this, Login_Pwd::class.java)

                    /*User user = new User();
                    //user.setId(id)
                    //user.setPassword(password);
                    //입력한 텍스트롤 서버로 보내야하는데.. 여기가 input인지?
                    // RetrofitService retrofitService = new RetrofitService
                    // UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
                    // -> 클릭 이벤트에서 일어나야함 등록 버튼을 누르면 서버로 값이 가야함
                    // userApi.save(user)
                    // . enqueue(new Callback<User>(){
                    @Override
                    public void onResponse(Call<User>) call, Response<User> response) {
                        Toast.makeText(Login_Email.this, "로그인성공..") 밑에는 동일하게 실패로
                    } 제 코드 생각의 흐름은 이렇습니다. (자바기반으로 씀)*/

                    intent.putExtra("email", user) //이메일 값 넘겨주기
                    startActivity(intent) }
                else {
                    //등록되지 않은 이메일인 경우
                    Toast.makeText(this, "가입되지 않은 이메일입니다.", Toast.LENGTH_SHORT).show()
                    if (soundState) {
                        onSpeech("가입되지 않은 이메일입니다.")
                    }
                }
            }
            else {
                //이메일 형식 오류일 경우
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
    private fun isEmailRegistered(email: String): Boolean {
        // JDBC 연결
        val url = TODO("데이터베이스 URL")
        val username = "username"
        val password = "password"
        // DB 연결
        var connection: Connection? = null
        var isRegistered = false
        try {
            connection = DriverManager.getConnection(url, username, password)
            // SQL 쿼리를 이용해서 이메일이 DB에 존재하는지 확인
            val sql = "SELECT COUNT(*) FROM users WHERE email = ?"
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, email)
            val resultSet = preparedStatement.executeQuery()
            //존재하지 않으면 isNotRegistered 값을 true로 설정
            if (resultSet.next()) {
                val count = resultSet.getInt(1)
                isRegistered = count > 0 //0보다 크면 등록 여부값 true
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            // 연결 닫기
            try {
                connection?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        return isRegistered
    }

    //음성 안내
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }
}

