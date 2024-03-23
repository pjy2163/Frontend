package com.example.graduation.transfer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduation.R


class TransferHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_history)

        // RecyclerView 초기화
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        //더미데이터 (내정보>결제내역에 뜨는 데이터들)
        val dataList = mutableListOf<TransferHistoryAdapter.DataModel>()
        dataList.add(
            TransferHistoryAdapter.DataModel(
                day = "2024-03-05",
                receiverName = "김유나",
                moneyAmount = "8500원",
                /*time = "10:41",*/
                status = "송금 완료"
        ))

        dataList.add(
            TransferHistoryAdapter.DataModel(
                day = "2024-03-05",
                receiverName = "송지호",
                moneyAmount = "11000원",
                /*time = "10:41",*/
                status = "송금 완료"
        ))

        dataList.add(
            TransferHistoryAdapter.DataModel(
                day = "2024-03-04",
                receiverName = "윤지민",
                moneyAmount = "5000원",
                /*time = "10:41",*/
                status = "송금 완료"
        ))

        dataList.add(
            TransferHistoryAdapter.DataModel(
                day = "2024-03-01",
                receiverName = "신민영",
                moneyAmount = "2500원",
                /*time = "10:41",*/
                status = "송금 완료"
        ))

        dataList.add(
            TransferHistoryAdapter.DataModel(
                day = "2024-02-27",
                receiverName = "이영선",
                moneyAmount = "50000원",
                /*time = "10:41",*/
                status = "송금 완료"
        ))

        dataList.add(
            TransferHistoryAdapter.DataModel(
                day = "2024-02-26",
                receiverName = "김민호",
                moneyAmount = "1500원",
                /*time = "10:41",*/
                status = "송금 완료"
        ))

        // LinearLayoutManager를 사용하여 수직 방향으로 아이템을 배치
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // 어댑터 설정
        val adapter = TransferHistoryAdapter(dataList)
        recyclerView.adapter = adapter
    }
}