package com.example.graduation.transfer

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable
import com.example.graduation.R
import com.example.graduation.databinding.ActivityRegisterChooseBankBinding
import com.example.graduation.databinding.ActivityTransferChooseBankBinding
import java.util.Locale

//송금프로세스: 은행선택>계좌번호입력>금액선택>송금정보확인>인증>완료
//어느 은행으로 보낼지 선택


//결제수단 등록> 은행선택
class TransferChooseBankActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferChooseBankBinding
    lateinit var mtts: TextToSpeech
    private var hanaChecked: Boolean = false // 하나 버튼의 체크 상태를 추적합니다.
    private var shinhanChecked: Boolean = false // 신한 버튼의 체크 상태를 추적합니다.
    private var kookminChecked: Boolean = false // 국민 버튼의 체크 상태를 추적합니다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferChooseBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        //화면 정보 읽기
        if (soundState) {
            onSpeech("계좌 선택 화면입니다")
        }

        binding.hanaBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.hanaBtn.text)
            }
            // 하나 버튼의 체크 상태 토글
            hanaChecked = !hanaChecked

            // SharedPreferences에 선택된 은행 정보 저장
            saveSelectedBank()

            // 체크 이미지 설정
            if (hanaChecked) {
                binding.checkHanaIv.setImageResource(R.drawable.img_check_yellow)
                // 하나 버튼이 선택되었으므로, 나머지 버튼들의 체크를 해제
                shinhanChecked = false
                kookminChecked = false
                binding.checkShinhanIv.setImageResource(R.drawable.img_check_grey)
                binding.checkKookminIv.setImageResource(R.drawable.img_check_grey)

            } else {
                binding.checkHanaIv.setImageResource(R.drawable.img_check_grey)
            }

            // 다음 버튼의 색상 설정
            setNextButtonColor()


        }

        binding.kookminBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.kookminBtn.text)
            }
            // 국민 버튼의 체크 상태 토글
            kookminChecked = !kookminChecked

            // SharedPreferences에 선택된 은행 정보 저장
            saveSelectedBank()

            // 체크 이미지 설정
            if (kookminChecked) {
                binding.checkKookminIv.setImageResource(R.drawable.img_check_yellow)
                // 국민 버튼이 선택되었으므로, 나머지 버튼들의 체크를 해제
                hanaChecked = false
                shinhanChecked = false
                binding.checkHanaIv.setImageResource(R.drawable.img_check_grey)
                binding.checkShinhanIv.setImageResource(R.drawable.img_check_grey)

            } else {
                binding.checkKookminIv.setImageResource(R.drawable.img_check_grey)
            }

            // 다음 버튼의 색상 설정
            setNextButtonColor()


        }


        binding.shinhanBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.shinhanBtn.text)
            }
            // 신한 버튼의 체크 상태 토글
            shinhanChecked = !shinhanChecked

            // SharedPreferences에 선택된 은행 정보 저장
            saveSelectedBank()

            // 체크 이미지 설정
            if (shinhanChecked) {
                binding.checkShinhanIv.setImageResource(R.drawable.img_check_yellow)
                // 신한 버튼이 선택되었으므로, 나머지 버튼들의 체크를 해제
                hanaChecked = false
                kookminChecked = false
                binding.checkHanaIv.setImageResource(R.drawable.img_check_grey)
                binding.checkKookminIv.setImageResource(R.drawable.img_check_grey)

            } else {
                binding.checkShinhanIv.setImageResource(R.drawable.img_check_grey)
            }

            // 다음 버튼의 색상 설정
            setNextButtonColor()


        }

        binding.nextBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }

            // 하나, 신한, 국민 버튼 중 하나라도 선택되었다면
            if (hanaChecked || shinhanChecked || kookminChecked) {

                val intent = Intent(this, TransferVoiceActivity::class.java)
                startActivity(intent)
            }
        }

    }



    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun setNextButtonColor() {
        // 하나, 신한, 국민 버튼 중 하나라도 선택되었다면 다음 버튼의 색상을 파랑색으로 변경
        if (hanaChecked || shinhanChecked || kookminChecked) {
            binding.nextBtn.setBackgroundColor(resources.getColor(R.color.blue)) // 파랑색
        } else { //선택하지 않음
            binding.nextBtn.setBackgroundColor(resources.getColor(android.R.color.darker_gray)) // 회색
        }
    }

    private fun saveSelectedBank() {
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if (hanaChecked) {
            editor.putString("selectedBank", "하나")
        } else if (shinhanChecked) {
            editor.putString("selectedBank", "신한")
        } else if (kookminChecked) {
            editor.putString("selectedBank", "국민")
        } else {
            // 선택된 버튼이 없는 경우를 처리할 수 있습니다.
            editor.putString("selectedBank", "")
        }

        editor.apply()
    }

}