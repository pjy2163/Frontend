package com.example.graduation.transfer

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.graduation.R
import com.example.graduation.databinding.ActivityTransferPicBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.Locale

//Google vision api사용함
//손글씨는 잘 안되는데 키보드로 텍스트 입력해서 찍어보면 잘됨

class TransferPicActivity : AppCompatActivity() {

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    lateinit var binding: ActivityTransferPicBinding
    private val REQUEST_IMAGE_CAPTURE=1
    private var imageBitmap: Bitmap? =null
    lateinit var mtts:TextToSpeech


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transfer_pic)
        setContentView(binding.root)
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        //목소리 입력과 카메라 촬영 중 무엇을 사용하여 계좌번호를 입력하였는지 정보
        val window="pic"
        val editor = sharedPreferences.edit()
        editor.putString("window",window)
        editor.apply()

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        //화면 정보 읽기
        if (soundState) {
            onSpeech("사진으로 송금하기 화면입니다.")
        }

        binding.apply {
            //사진촬영 버튼 이벤트처리
            takePicBtn.setOnClickListener {
                takeImage()
   /*             binding.resultTv.text = ""*/
                if (soundState) {
                    onSpeech(binding.takePicBtn.text)
                }
            }

            binding.detectTextBtn.setOnClickListener {
                processImage() //이미지 처리해서 숫자 추출
                if (soundState) {
                    onSpeech(binding.detectTextBtn.text)
                }

                //어느 계좌번호로 보낼건지 사진촬영한 것을 저장, TransferConfirmationActivity로 보내기
                val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("toAccount", binding.resultTv.text.toString())
                editor.apply()

                binding.resultTv.visibility = View.VISIBLE


            }
        }
        binding.nextBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }

            val intent = Intent(this, TransferConfirmationActivity::class.java)
            startActivity(intent)
        }
    }


    private fun takeImage(){
        val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE)
        }
        catch (e:Exception){
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode== RESULT_OK){
            val extras: Bundle? = data?.extras
            imageBitmap= extras?.get("data") as Bitmap
            if (imageBitmap!=null) {
                binding.takenResultPicIv.setImageBitmap(imageBitmap)
            }
        }
    }

    //이미지 처리 함수
    private fun processImage(){

        if (imageBitmap!=null) {
            val image = imageBitmap?.let {
                InputImage.fromBitmap(it, 0)
            }

            image?.let {
                recognizer.process(it)
                    //이미지 인식 성공시
                    .addOnSuccessListener { visionText ->
                        //모든 텍스트는 숫자로만 인식(계좌번호)하지만 -는 -로 인식
                        val modifiedText =  maketextNumeric(visionText.text)
                        //인식된 계좌번호를 텍스트뷰에 띄우기
                        binding.resultTv.text = modifiedText
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "이미지 인식 실패", Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            Toast.makeText(this, "사진을 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun maketextNumeric(originalText: String): String {
        // 숫자와 -를 제외한 문자 제거
        val numericText = originalText.replace("[^0-9-]".toRegex(), "")
        return numericText
    }

    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}