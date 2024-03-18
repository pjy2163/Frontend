package com.example.graduation.transfer

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.graduation.R
import com.example.graduation.databinding.ActivityTransferConfirmationBinding
import java.util.Locale

class TransferConfirmationActivity : AppCompatActivity() {
    private lateinit var speechRecognizer: SpeechRecognizer
    lateinit var mtts:TextToSpeech
    private lateinit var binding: ActivityTransferConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        //목소리 입력과 카메라 촬영 중 무엇을 사용하여 계좌번호(to)를 입력하였는지 정보
        val window=sharedPreferences.getString("window", "a")
        if (window != null) {
            Log.d("TransferConfirmationActivity", window)

            if (window=="pic"){ //카메라 촬영으로 계좌번호(to)를 입력받은 경우
                //어느 계좌로 보낼 것인지(to) transferPicActivity에서 가져오기
                val toAccount=sharedPreferences.getString("toAccount", "123")
                binding.receiverAccountNumberTv.text=toAccount
            }
            else{ //목소리 입력으로 계좌번호(to)를 입력받은 경우
                //어느 계좌로 보낼 것인지(to) transferVoiceActivity에서 가져오기
                val toAccount=sharedPreferences.getString("toAccount", "123")
                binding.receiverAccountNumberTv.text=toAccount
            }
        }


        //선택된 은행계좌와 계좌번호(from) 가져와서 텍스트뷰에 반영
        val sharedPreferences3 = getSharedPreferences("sp3", Context.MODE_PRIVATE)
        val selectedAccountName = sharedPreferences3.getString("selectedAccountName", "")
        val selectedAccountNumber = sharedPreferences3.getString("selectedAccountNumber", "")
        binding.receiverBankTv.text =selectedAccountName
        binding.receiverAccountNumberTv.text=selectedAccountNumber

        //소리 설정
        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        binding.prevBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }

            val intent = Intent(this, TransferVoiceActivity::class.java)
            startActivity(intent)
        }

        // 권한 설정
        requestPermission()

        // RecognizerIntent 생성
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)    // 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")         // 언어 설정


        // <말하기> 버튼 눌러서 음성인식 시작
        binding.speechBtn.setOnClickListener {
            // 새 SpeechRecognizer 를 만드는 팩토리 메서드
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this@TransferConfirmationActivity)
            speechRecognizer.setRecognitionListener(recognitionListener)    // 리스너 설정
            speechRecognizer.startListening(intent)                         // 듣기 시작
        }

        //TODO:결제승인 과정에서 오류시 else 처리
        binding.approveBtn.setOnClickListener {

            if (soundState) {
                onSpeech(binding.approveBtn.text)
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_basic, TransferCompletedFragment())
                .commit()


        }
    }

    // 권한 설정 메소드
    private fun requestPermission() {
        // 버전 체크, 권한 허용했는지 체크
        if (Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(
                this@TransferConfirmationActivity,
                Manifest.permission.RECORD_AUDIO
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@TransferConfirmationActivity,
                arrayOf(Manifest.permission.RECORD_AUDIO), 0
            )
        }
    }

    // 리스너 설정
    private val recognitionListener: RecognitionListener = object : RecognitionListener {
        // 말하기 시작할 준비가되면 호출
        override fun onReadyForSpeech(params: Bundle) {
            Toast.makeText(applicationContext, "음성인식 시작", Toast.LENGTH_SHORT).show()
            binding.stateTv.text = "이제 말씀하세요!"
        }
        // 말하기 시작했을 때 호출
        override fun onBeginningOfSpeech() {
            binding.stateTv.text = "잘 듣고 있어요."
        }
        // 입력받는 소리의 크기를 알려줌
        override fun onRmsChanged(rmsdB: Float) {}
        // 말을 시작하고 인식이 된 단어를 buffer에 담음
        override fun onBufferReceived(buffer: ByteArray) {}
        // 말하기를 중지하면 호출
        override fun onEndOfSpeech() {
            binding.stateTv.text = "정상적으로 음성 인식이 완료되었습니다."
            //화면 정보 읽기

        }
        // 오류 발생했을 때 호출
        override fun onError(error: Int) {
            val message = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "오디오 에러"
                SpeechRecognizer.ERROR_CLIENT -> "클라이언트 에러"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "퍼미션 없음"
                SpeechRecognizer.ERROR_NETWORK -> "네트워크 에러"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "네트웍 타임아웃"
                SpeechRecognizer.ERROR_NO_MATCH -> "찾을 수 없음"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RECOGNIZER 가 바쁨"
                SpeechRecognizer.ERROR_SERVER -> "서버가 이상함"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "말하는 시간초과"
                else -> "알 수 없는 오류임"
            }
            binding.stateTv.text = "에러 발생: $message"
        }
        // 인식 결과가 준비되면 호출
        override fun onResults(results: Bundle) {
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
            val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            for (i in matches!!.indices) binding.senderNameTv.text = matches[i] /*binding.resultTv.text = matches[i]*/


            val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("toAccount", binding.resultTv.text.toString())
            editor.apply()
        }
        // 부분 인식 결과를 사용할 수 있을 때 호출
        override fun onPartialResults(partialResults: Bundle) {}
        // 향후 이벤트를 추가하기 위해 예약
        override fun onEvent(eventType: Int, params: Bundle) {}
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}