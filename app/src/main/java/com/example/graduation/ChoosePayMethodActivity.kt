package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.graduation.databinding.ActivityChoosePayMethodBinding
import java.util.Locale

//TODO:뷰페이저에서 통장 이미지 선택 후 인증화면으로 넘어가는 처리중
//결제수단 등록에서 계좌를 입력하면-> 여기로 넘어와야 하는데 인디케이터는 어떻게하냐..

class ChoosePayMethodActivity : AppCompatActivity(),PaymentMethodClickListener {

lateinit var mtts: TextToSpeech
private lateinit var binding: ActivityChoosePayMethodBinding
private var selectedPaymentMethod: PaymentMethod? = null

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


    //화면 정보 읽기
    if (soundState) {
        onSpeech("결제 수단 선택 화면입니다")
    }

    val paymentMethods = listOf(
        PaymentMethod(R.drawable.img_hana_bankbook, "하나은행", "0123456-0123456"),
        PaymentMethod(R.drawable.img_hana_bankbook2, "하나은행", "0123456-0123456"),
        PaymentMethod(R.drawable.img_kookmin_bankbook, "국민은행", "0123456-0123456"),
        //여기서 입력한 정보가 PayConfirmationActivity로 넘어가게 됨

    )

    val adapter = PaymentMethodAdapter(paymentMethods, this)
    binding.viewPagerCard.adapter = adapter
    binding.viewPagerCard.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    /*
        binding.viewPagerCard.adapter = ViewPagerAdapter(getCardList()) // Create the adapter
        binding.viewPagerCard.orientation = ViewPager2.ORIENTATION_HORIZONTAL // Set the orientation
    */

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

        if (selectedPaymentMethod != null) {
            //인증 방법 선택 화면으로 넘어가기
            val intent = Intent(this, AuthWayActivity::class.java)

            //SharedPrefrence에 데이터 저장하기
            val sharedPreferences = getSharedPreferences("sp3", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            //은행이름과 계좌번호 저장 : paymentMethod 데이터 클래스 참고
            editor.putString("selectedAccountName", selectedPaymentMethod!!.bank)
            editor.putString("selectedAccountNumber", selectedPaymentMethod!!.accountNumber)
            editor.apply()

            //은행별로 다른 소리 출력
            onSpeech(selectedPaymentMethod!!.bank )
            startActivity(intent)


        } else {
            //결제수단 하나 골라야 한다고 알려주기
            Toast.makeText(this, "결제 수단을 선택해주세요.", Toast.LENGTH_SHORT).show()
            onSpeech("결제 수단을 선택해주세요.")
        }
    }

}

/*    // Items for the ViewPager
    private fun getCardList(): ArrayList<Int> {
        return arrayListOf(
            R.drawable.img_card_kookmin,
            R.drawable.img_card_shinhan,
            R.drawable.img_card_hana,
            R.drawable.img_card_woori
        )
    }*/
override fun onPaymentMethodClick(paymentMethod: PaymentMethod) {

    for (i in 0 until binding.viewPagerCard.childCount) {
        val cardView = binding.viewPagerCard.getChildAt(i)
        cardView.setBackgroundResource(0)
    }

    // Set background for the selected view
    val selectedPosition = binding.viewPagerCard.currentItem
    val selectedView = binding.viewPagerCard.getChildAt(selectedPosition)
    selectedView?.setBackgroundResource(R.drawable.selected_payment_border)

    // 클릭된 결제 수단에 따라 다음 화면으로 이동하는 코드
    selectedPaymentMethod = paymentMethod
    binding.nextBtn.isEnabled = true

/*    //하나은행 계좌 이미지를 누르면
    when (paymentMethod.bank) {
        "하나은행" -> {
            //통장 이미지 클릭 시 처리- 인증화면으로 넘어가기
            val intent = Intent(this, AuthWayActivity::class.java)
            // 인텐트에 필요한 데이터를 넘겨줄 수 있음 (예: 계좌 번호 등)
            intent.putExtra("accountNumber", paymentMethod.accountNumber)
            startActivity(intent)
        }


    }*/



}
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}

