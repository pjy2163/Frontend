package com.example.graduation

import PaymentMethodAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.graduation.databinding.ActivityChoosePayMethodBinding
import java.util.Locale

//TODO:뷰페이저에서 통장 이미지 선택 후 인증화면으로 넘어가는 처리중
//결제수단 등록에서 계좌를 입력하면-> 여기로 넘어와야 하는데 인디케이터는 어떻게하냐..

class ChoosePayMethodActivity : AppCompatActivity(),PaymentMethodClickListener {

    lateinit var mtts: TextToSpeech
    private lateinit var binding: ActivityChoosePayMethodBinding
    private var selectedPaymentMethod: PaymentMethod? = null
    private var paymentMethods: List<PaymentMethod> = listOf(
        PaymentMethod(R.drawable.img_bank_hana, "Hana Bank","하나은행", "0123456-0123456",false),
        PaymentMethod(R.drawable.img_bank_shinhan,"Shinhan Bank","신한은행", "0123456-0123456",false),
        PaymentMethod(R.drawable.img_bank_kookmin,"Kookmin Bank","국민은행", "0123456-0123456",false)
            //필요한 리스트가 있으면 여기
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoosePayMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)


        mtts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // 화면 정보 읽어주기
                val textToSpeak ="결제 수단 선택 화면입니다"+binding.explainTv.text
                onSpeech(textToSpeak)
            } else {
                // 초기화가 실패한 경우
                Log.e("TTS", "TextToSpeech 초기화 실패")
            }
        }

/*        var paymentMethods = listOf(
            PaymentMethod(R.drawable.img_hana_bankbook, "하나은행", "0123456-0123456"),
            PaymentMethod(R.drawable.img_hana_bankbook2, "하나은행", "0123456-0123456"),
            PaymentMethod(R.drawable.img_kookmin_bankbook, "국민은행", "0123456-0123456"),
            //여기서 입력한 정보가 PayConfirmationActivity로 넘어가게 됨

        )*/

        val adapter = PaymentMethodAdapter(paymentMethods, this)
        binding.viewPagerCard.adapter = adapter
        binding.viewPagerCard.orientation = ViewPager2.ORIENTATION_HORIZONTAL



        binding.viewPagerCard.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.indicator0IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_grey))
                binding.indicator1IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_grey))
                binding.indicator2IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_grey))
                /* binding.indicator3IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_grey))
    */
                when (position) {
                    0 -> binding.indicator0IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_blue))
                    1 -> binding.indicator1IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_blue))
                    2 -> binding.indicator2IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_blue))
                    /*3 -> binding.indicator3IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_purple))
    */
                }
            }
        })

        binding.prevBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }

            val intent = Intent(this, CheckPayInfoActivity::class.java)
            startActivity(intent)
        }

        binding.nextBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }

            //선택된 통장이 있어야
            if (selectedPaymentMethod != null) {

                //인증 방법 선택 화면으로 넘어가기
                val intent = Intent(this, AuthWayActivity::class.java)

                //SharedPrefrence에 데이터 저장하기
                val sharedPreferences = getSharedPreferences("sp3", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                //은행이름과 계좌번호 저장 : paymentMethod 데이터 클래스 참고
                editor.putString("selectedAccountName", selectedPaymentMethod!!.korBank)
                editor.putString("selectedAccountNumber", selectedPaymentMethod!!.accountNumber)
                editor.apply()

                //은행별로 다른 소리 출력
                onSpeech(selectedPaymentMethod!!.korBank + "이 선택되었습니다.")
                startActivity(intent)


            } else {
                //결제수단 하나 골라야 한다고 알려주기
                Toast.makeText(this, "결제 수단을 선택해주세요.", Toast.LENGTH_SHORT).show()
                onSpeech("결제 수단을 선택해주세요.")
            }
        }

    }


    override fun onPaymentMethodClick(paymentMethod: PaymentMethod) {

        val selectedPosition = binding.viewPagerCard.currentItem

        paymentMethods.mapIndexed  { index, method ->
            method.isSelected = (index == selectedPosition && !method.isSelected)
        }

        selectedPaymentMethod = if (paymentMethod.isSelected) paymentMethod else null

        //어댑터에게 변화 알려주기
        binding.viewPagerCard.adapter?.notifyDataSetChanged()

        //통장 선택에 따라 UI 업데이트 (다음 버튼 회색->블루)
        binding.nextBtn.isEnabled = selectedPaymentMethod != null
        binding.nextBtn.setBackgroundColor(
            if (selectedPaymentMethod != null)
                this.resources.getColor(R.color.blue)
            else
                this.resources.getColor(R.color.grey)
        )

        //통장 이미지 누를 때마다 뭐가 눌렸는지 음성 안내
        if (selectedPaymentMethod?.korBank != null) {
            onSpeech(selectedPaymentMethod!!.korBank)
            selectedPaymentMethod!!.isSelected=true
        }


    }

    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}

