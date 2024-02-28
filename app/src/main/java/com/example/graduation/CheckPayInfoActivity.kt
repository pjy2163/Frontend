package com.example.graduation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.graduation.databinding.ActivityCheckPayInfoBinding
import java.util.Locale

class CheckPayInfoActivity : AppCompatActivity() {
    lateinit var mtts:TextToSpeech
    private lateinit var binding: ActivityCheckPayInfoBinding
    private var productDatas = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckPayInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 결제할 품목 더미데이터
        productDatas.apply {
            add(Product(1, "스타벅스", "아이스 아메리카노", 4500))
            add(Product(2, "이디야", "카페라떼", 4000))
            add(Product(3, "도미노 피자", "페퍼로니 피자", 20000))
            add(Product(4, "포라임", "양지 쌀국수", 12000))
            add(Product(5, "두끼 떡볶이", "성인", 12000))
            add(Product(6, "베스킨라빈스", "뉴욕 치즈 케이크", 4500))
            add(Product(7, "쉑쉑버거", "머쉬룸 버거", 15000))
        }


        //결제정보 나타내기- 11번가 핸드폰거치대
        val product = productDatas[0]
        binding.productPlaceTv.text = "결제업체명: ${product.storeName}"
        binding.productNameTv.text = "상품명: ${product.productName}"
        binding.productPriceTv.text = "가격: ${product.price} 원"


        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }

        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferences.getBoolean("soundState", false)

        if (soundState) {
            onSpeech("결제 정보 확인 화면입니다")
        }

        binding.prevBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.nextBtn.setOnClickListener{
            if (soundState) {
                onSpeech(binding.nextBtn.text)
            }

            val intent = Intent(this, ChoosePayMethodActivity::class.java)

            //상품 정보 담기
            val product = productDatas[0]

            //SharedPrefrence에 데이터 저장하기
            val sharedPreferences = getSharedPreferences("sp2", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("storeName", product.storeName)
            editor.putString("productName", product.productName)
            editor.putInt("price", product.price)
            editor.apply()

            startActivity(intent)
        }
    }
    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }


}
