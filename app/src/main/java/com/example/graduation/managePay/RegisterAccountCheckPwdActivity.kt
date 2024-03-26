package com.example.graduation.managePay

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText
import android.widget.Toast
import com.example.graduation.MainActivity
import com.example.graduation.R
import com.example.graduation.databinding.ActivityRegisterAccountCheckPwdBinding
import java.util.Locale


//회원가입시 등록할 계좌비밀번호 (Ex.536612) 확인

class RegisterAccountCheckPwdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterAccountCheckPwdBinding
    private lateinit var passwordEditTexts: Array<EditText>
    lateinit var mtts: TextToSpeech
    private var currentPasswordIndex = 0
    private var enteredAccountPassword: String = ""
    private var savedAccountPassword = "" //RegisterAccountPwdActivity에서 설정한 비밀번호를 저장할 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityRegisterAccountCheckPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        // SignUpAccountPwdActivity에서 설정한 비밀번호 가져오기
        val prefs = getSharedPreferences("AccountPwd", Context.MODE_PRIVATE)
        savedAccountPassword = prefs.getString("password", "") ?: ""

        passwordEditTexts = arrayOf(
            binding.passwordEt1,
            binding.passwordEt2,
            binding.passwordEt3,
            binding.passwordEt4,
            binding.passwordEt5,
            binding.passwordEt6
        )

        //비밀번호 첫 시작은 0
        for (editText in passwordEditTexts) {
            editText.setText("0")
        }

        for (editText in passwordEditTexts) {
            editText.setOnKeyListener { _, keyCode, event ->
                handleKeyEvent(editText, keyCode, event)
                false
            }


            if (editText == passwordEditTexts.last()) {
                editText.setOnEditorActionListener { _, actionId, event ->
                    handleEditorAction()
                    true
                }
            }
        }

        binding.prevBtn.setOnClickListener {
            val intent = Intent(this, RegisterAccountPwdActivity::class.java)
            startActivity(intent)

            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }
        }

        //비밀번호 입력을 끝마치고 다음 버튼을 눌렀을때, 계좌비밀번호를 두번다 옳게 입력했으면 메인화면으로 넘어가기
        binding.nextBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }
            Log.d("yklee",enteredAccountPassword)
            Log.d("yklee",savedAccountPassword)
            if (enteredAccountPassword == savedAccountPassword) {
                val intent = Intent(this, RegisterAccountConfirmationActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }



        //다음 입력버튼 이벤트 처리- 다음 자리의 비밀번호 입력
        binding.enterBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.enterBtn.text)
            }
            Toast.makeText(this, "비밀번호가 입력되었습니다.", Toast.LENGTH_SHORT).show()
            handleEnterButton()
        }

        //비밀번호 한자리씩 지우기
        binding.deleteBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.deleteBtn.text)
            }
            handleDeleteButton()
        }

    }

    private fun handleEditorAction() {
        handleEnterButton()
    }

    //입력버튼 이벤트 처리
    private fun handleEnterButton() {
        if (currentPasswordIndex < passwordEditTexts.size - 1) {   //다음 비밀번호를 입력할 수 있게 넘어가기
            currentPasswordIndex++ //다음 비밀번호로 인덱스 이동
            passwordEditTexts[currentPasswordIndex].requestFocus()
        } else {
            // 비밀번호를 모두 입력했을 때
            // 흩어진 여섯자리의 비밀번호를 하나로 모아 enteredPassword 변수에 저장
            val stringBuilder = StringBuilder()
            for (editText in passwordEditTexts) {
                stringBuilder.append(editText.text.toString())
            }
            enteredAccountPassword = stringBuilder.toString()
            Log.d("ykleepasswordCheck",  enteredAccountPassword ) //정상출력됨 확인함 (0도 잘 입력됨)

            //다음 버튼은 원래 회색. 비밀번호 입력완료하면 파랑색으로 바뀜
            binding.nextBtn.setBackgroundColor(this.getResources().getColor(R.color.blue));

            Toast.makeText(this, "모든 비밀번호가 입력되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun handleKeyEvent(editText: EditText, keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_VOLUME_UP -> incrementEditTextValue(editText)
                KeyEvent.KEYCODE_VOLUME_DOWN -> decrementEditTextValue(editText)
            }
        }
        return false
    }

    private fun incrementEditTextValue(editText: EditText) {
        val currentValue = editText.text.toString().toInt()
        editText.setText((currentValue + 1).toString())
    }

    private fun decrementEditTextValue(editText: EditText) {
        val currentValue = editText.text.toString().toInt()
        if (currentValue>0){ //비밀번호 각 자리가 0보다 클 때
            editText.setText((currentValue - 1).toString())
        }
        else if (currentValue==0){ //이미 비밀번호의 각 자리가 0임 -> -1로 내려가지 않도록 막기
            Toast.makeText(this, "비밀번호의 각 자리는 양수로 설정해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    //지우기 버튼 이벤트 처리
    private fun handleDeleteButton() {
        if (currentPasswordIndex > 0) {  //입력한 비밀번호 값이 있을 때
            passwordEditTexts[currentPasswordIndex].setText("0") // 현재 인덱스의 값을 0으로 초기화함
            passwordEditTexts[currentPasswordIndex].requestFocus()
        } else {
            //입력한 비밀번호 값이 없을 때
            Toast.makeText(this, "삭제할 비밀번호가 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }
    override fun onDestroy() {
        super.onDestroy()
        mtts.shutdown()

    }
}
