package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.viewpager2.widget.ViewPager2
import com.example.graduation.databinding.ActivityChoosePayMethodBinding
import java.util.Locale

class ChoosePayMethodActivity : AppCompatActivity() {

lateinit var mtts: TextToSpeech
private lateinit var binding: ActivityChoosePayMethodBinding

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


        // 추가적인 이미지와 텍스트 쌍을 여기에 추가
    )

    val adapter = PaymentMethodAdapter(paymentMethods)
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
                0 -> binding.indicator0IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_purple))
                1 -> binding.indicator1IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_purple))
                2 -> binding.indicator2IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_purple))
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

    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}

