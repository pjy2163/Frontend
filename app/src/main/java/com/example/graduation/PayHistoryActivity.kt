package com.example.graduation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduation.databinding.ActivityPayHistoryBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PayHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //업체명과 금액 가져와서 텍스트뷰에 반영
        var spPay = getSharedPreferences("PayInfo", Context.MODE_PRIVATE)
        var storeName = spPay.getString("storeName", "") ?: ""
        /*var price = spPay.getInt("price", 0)*/
        var price = spPay.getString("price", "0")?.toIntOrNull() ?: 0

        // RecyclerView 초기화
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        //더미데이터 (내정보>결제내역에 뜨는 데이터들)
        val dataList = mutableListOf<PayHistoryAdapter.DataModel>()

        // 받는 사람의 이름과 송금 금액이 null이 아닐 때 dataList에 추가
        if ( storeName != null &&price != null) {
            // 현재 날짜 가져오기
            var currentDate = getCurrentDate()

            // dataList에 추가
            dataList.add(
                PayHistoryAdapter.DataModel(
                    day = currentDate,
                    productPlace = storeName,
                    productPrice = price,
                    status = "결제 완료"
                )
            )
        } else {
            Toast.makeText(this@PayHistoryActivity, "결제 기록이 없습니다.", Toast.LENGTH_SHORT).show()
        }
        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-03-21",
            productPlace = "GS25",
            productPrice = 12000,
            /* time = "13:45",*/
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-03-20",
            productPlace = "피자헛",
            productPrice = 28500,
           /* time = "13:45",*/
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-03-20",
            productPlace = "쿠팡",
            productPrice = 32000,
           /* time = "9:40",*/
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-03-18",
            productPlace = "11번가",
            productPrice = 5000,
         /*   time = "10:42",*/
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-03-11",
            productPlace = "신세계",
            productPrice = 42000,
    /*        time = "17:31",*/
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-02-20",
            productPlace = "포라임",
            productPrice = 9000,
      /*      time = "12:10",*/
            status = "결제 완료"
        ))


        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-02-15",
            productPlace = "GS25",
            productPrice = 1700,
          /*  time = "9:01",*/
            status = "결제 완료"
        ))


        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-02-10",
            productPlace = "서브웨이",
            productPrice = 11200,
         /*   time = "12:07",*/
            status = "결제 완료"
        ))

        // LinearLayoutManager를 사용하여 수직 방향으로 아이템을 배치
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // 어댑터 설정
        val adapter = PayHistoryAdapter(dataList)
        recyclerView.adapter = adapter

        binding.prevBtn.setOnClickListener {
          /*  if (soundState) {
                onSpeech(binding.prevBtn.text)
            }*/
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    // 현재 날짜 가져오는 메서드
    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

}