package com.example.graduation.managePay

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.graduation.R
import com.example.graduation.databinding.ActivityRegisterAccountConfirmationBinding
import com.example.graduation.model.Account
import com.example.graduation.retrofit.AccountApi
import com.example.graduation.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale
import java.util.logging.Level
import java.util.logging.Logger

class RegisterAccountConfirmationActivity : AppCompatActivity(){
    private lateinit var binding: ActivityRegisterAccountConfirmationBinding
    lateinit var mtts: TextToSpeech
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityRegisterAccountConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        // SharedPreferences에서 소리 on/off 상태 불러오기
        sharedPreferences = getSharedPreferences("sp1", MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        binding.prevBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }
            startActivity(Intent(this, RegisterAccountNumberActivity::class.java))
        }


        //앞에서 입력한 은행명, 계좌번호 데이터 가져와서 텍스트뷰에 반영
        val sharedPreferences2 = getSharedPreferences("registerInfo", Context.MODE_PRIVATE)
        val bankName = sharedPreferences2.getString("bankName", "")
        val accountNumber = sharedPreferences2.getString("accountNum", "")
        binding.bankNameTv.text=bankName
        binding.accountNumberTv.text=accountNumber.toString()

        val retrofitService = RetrofitService()
        val accountApi: AccountApi = retrofitService.retrofit.create(AccountApi::class.java)

        val account = Account()
        account.setBank_name(bankName)
        account.setAccount_number(accountNumber)


        //다음버튼 누르면 계좌등록 화면으로 넘어가기
        binding.nextBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }
            accountApi.save1(account)
                .enqueue(object : Callback<Account> {
                    override fun onResponse(call: Call<Account>, response: Response<Account>) {
                        Log.d("RegisterChooseBankActivity", "bank name saved successfully: ${response.body()}")
                        Toast.makeText(this@RegisterAccountConfirmationActivity, "저장완료", Toast.LENGTH_SHORT).show()

                    }

                    override fun onFailure(call: Call<Account>, t: Throwable) {
                        Toast.makeText(this@RegisterAccountConfirmationActivity, "fail!!!", Toast.LENGTH_SHORT).show()
                        Logger.getLogger(RegisterChooseBankActivity::class.java.name).log(Level.SEVERE, "에러")
                    }
                })
            val fragment = RegisterCompletedFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_basic, fragment)
                .addToBackStack(null)
                .commit()
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