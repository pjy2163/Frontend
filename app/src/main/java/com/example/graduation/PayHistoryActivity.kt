package com.example.graduation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PayHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_history)

        // RecyclerView 초기화
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        //더미데이터 (내정보>결제내역에 뜨는 데이터들)
        val dataList = mutableListOf<PayHistoryAdapter.DataModel>()
        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-03-05",
            productPlace = "스타벅스",
            productPrice = "4500원",
            time = "13:45",
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            // 또 다른 결제 내역 데이터
            day = "2024-03-03",
            productPlace = "쿠팡",
            productPrice = "32000원",
            time = "9:40",
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-03-01",
            productPlace = "11번가",
            productPrice = "5000원",
            time = "10:42",
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-02-27",
            productPlace = "신세계",
            productPrice = "42000원",
            time = "17:31",
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-02-20",
            productPlace = "포라임",
            productPrice = "9000원",
            time = "12:10",
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-02-17",
            productPlace = "포돈",
            productPrice = "14000원",
            time = "10:48",
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-02-15",
            productPlace = "GS25",
            productPrice = "1700원",
            time = "9:01",
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-02-10",
            productPlace = "교촌치킨",
            productPrice = "15300원",
            time = "22:04",
            status = "결제 완료"
        ))

        dataList.add(PayHistoryAdapter.DataModel(
            day = "2024-02-10",
            productPlace = "서브웨이",
            productPrice = "11200원",
            time = "12:07",
            status = "결제 완료"
        ))

        // LinearLayoutManager를 사용하여 수직 방향으로 아이템을 배치
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // 어댑터 설정
        val adapter = PayHistoryAdapter(dataList)
        recyclerView.adapter = adapter
    }
}