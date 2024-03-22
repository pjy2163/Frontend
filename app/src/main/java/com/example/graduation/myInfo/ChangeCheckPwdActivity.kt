package com.example.graduation.myInfo

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.graduation.SignupDialog
import com.example.graduation.SignupDialogInterface
import com.example.graduation.databinding.ActivityChangeCheckPwdBinding
import java.util.Locale
import java.util.concurrent.Executor

//지난번 피드백 이후 비밀번호 변경시 지문인증 추가함
class ChangeCheckPwdActivity : AppCompatActivity(), SignupDialogInterface {

    private lateinit var binding: ActivityChangeCheckPwdBinding
    lateinit var mtts: TextToSpeech
    private var executor: Executor? = null
    private var biometricPrompt: BiometricPrompt? = null
    private var promptInfo: BiometricPrompt.PromptInfo? = null
    lateinit var mediaPlayerSuccess: MediaPlayer
    lateinit var mediaPlayerFailure: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityChangeCheckPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        //지문 관련 변수
        biometricPrompt = setBiometricPrompt()
        promptInfo = setPromptInfo()

        
        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        //화면 정보 읽기
        if (soundState) {
            onSpeech("회원가입 이메일 입력 화면입니다.")
        }

        binding.enterButton.setOnClickListener {
            val checkpwd = binding.signupInputCheckpwd.text.toString().trim()
            val pwd = intent.getStringExtra("pwd").toString()
            if (isPwdIdentified(checkpwd, pwd)) {
                //비밀번호 일치하면 계정 생성 후 로그인 화면으로 이동
                val email = intent.getStringExtra("email").toString()
                val name = intent.getStringExtra("name").toString()
                makeUser(name, email, pwd)
                val intent = Intent(this, ChangePwdCompletedFragment::class.java)
                startActivity(intent)
            } else {
                //비밀번호 불일치 시
                run {
                    val dialog = SignupDialog(this, "비밀번호가 일치하지않습니다.")
                    dialog.isCancelable = false
                    dialog.show(this.supportFragmentManager, "SignupDialog")
                    if (soundState) {
                        onSpeech("비밀번호가 일치하지않습니다.")
                    }
                }
            }
        }
    }

    //비밀번호 일치 검사
    private fun isPwdIdentified(checkpwd: String, pwd: String) : Boolean {
        return pwd == checkpwd
    }

    //계정 생성
    private fun makeUser(name: String, email: String, pwd: String): Boolean {
        return TODO("서버에 입력받은 email, pwd로 사용자 계정 생성")
    }

    override fun onDialogButtonClick() {
        finish()
    }

    private fun setPromptInfo(): BiometricPrompt.PromptInfo {

        val promptBuilder: BiometricPrompt.PromptInfo.Builder = BiometricPrompt.PromptInfo.Builder()

        promptBuilder.setTitle("지문 인증")
        promptBuilder.setSubtitle("터치 센서에 지문을 인식하고 결제하세요")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { //  안면인식 ap사용 android 11부터 지원
            promptBuilder.setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
        }

        promptInfo = promptBuilder.build()
        return promptInfo as BiometricPrompt.PromptInfo
    }
    private fun setBiometricPrompt(): BiometricPrompt {
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this@ChangeCheckPwdActivity, executor!!, object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@ChangeCheckPwdActivity, """"지문 인식 ERROR [ errorCode: $errorCode, errString: $errString ]""".trimIndent(), Toast.LENGTH_SHORT).show()
            }

            //지문 인식 성공시
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                playSuccessSound()
                Toast.makeText(this@ChangeCheckPwdActivity, "지문 인증에 성공하였습니다.", Toast.LENGTH_SHORT).show()

                //송금완료 화면으로 이동
                val intent = Intent(this@ChangeCheckPwdActivity, ChangeCheckPwdActivity::class.java)
                startActivity(intent)
            }

            //지문 인식 실패시
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                playFailureSound()
                Toast.makeText(this@ChangeCheckPwdActivity, "지문 인증에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }

        } )
        return biometricPrompt as BiometricPrompt
    }


    /*
    * 생체 인식 인증을 사용할 수 있는지 확인
    * */
    fun authenticateToEncrypt() = with(binding) {

        Log.d("0222", "authenticateToEncrypt() ")

        var textStatus = ""
        val biometricManager = BiometricManager.from(this@ChangeCheckPwdActivity)
//        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {

            //생체 인증 가능
            BiometricManager.BIOMETRIC_SUCCESS -> textStatus = "이 디바이스에서 생체 정보를 활용할 수 있습니다."

            //기기에서 생체 인증을 지원하지 않는 경우
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> textStatus = "이 디바이스에서는 생체 정보 활용이 불가합니다."

            //현재 생체 인증을 사용할 수 없는 경우
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> textStatus = "생체 정보 활용이 허용되지 않았습니다."

            //생체 인식 정보가 등록되어 있지 않은 경우
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                textStatus = "생체 인식 정보가 디바이스에 등록되어 있지 않습니다."

                val dialogBuilder = AlertDialog.Builder(this@ChangeCheckPwdActivity)
                dialogBuilder //지문등록 필요함 안내 다이얼로그
                    .setTitle("SoriPay")
                    .setMessage("지문 등록이 필요합니다. 지문등록 설정화면으로 이동하시겠습니까?")
                    .setPositiveButton("확인") { dialog, which -> goBiometricSettings() }

                dialogBuilder.show()
            }

            //기타 실패
            else ->  textStatus = "생체 정보 인식 실패"

        }
        /* binding.statusTv.text = textStatus*/

        //인증 실행하기
        goAuthenticate()
    }


    /*
    * 생체 인식 인증 실행
    * */
    private fun goAuthenticate() {
        Log.d("0222", "goAuthenticate - promptInfo : $promptInfo")
        promptInfo?.let {
            biometricPrompt?.authenticate(it);  //인증 실행
        }
    }


    /*
    * 지문 등록 화면으로 이동
    * */
    fun goBiometricSettings() {
        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        }

    }
    
    //음성 안내
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun playSuccessSound() {
        mediaPlayerSuccess.start()
    }

    private fun playFailureSound() {
        mediaPlayerFailure.start()
    }

}