//TODO:두번 송금하면 먼저 송금한 데이터가 날아가버리는 문제
//홍길동-윤진아 송금하면 홍길동 사라짐 윤진아만 남음

package com.example.graduation.transfer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduation.MainActivity
import com.example.graduation.R
import com.example.graduation.databinding.ActivityTransferHistoryBinding
import com.example.graduation.transfer.TransferHistoryAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class TransferHistoryActivity : AppCompatActivity() {



    private val dataList = mutableListOf<TransferHistoryAdapter.DataModel>()
    /*
        private lateinit var TransferRecordBinding: ActivityTransferConfirmationBinding
        //TransferRecordBinding은 TransferConfirmationActivity에 담긴 기록을 송금내역화면(TransferHistoryActivity)로 가져오기 위함
    */

    private lateinit var binding: ActivityTransferHistoryBinding
    lateinit var mtts: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransferHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mtts = TextToSpeech(this) { //모든 글자를 소리로 읽어주는 tts
            mtts.language = Locale.KOREAN //언어:한국어
        }
        // SharedPreferences에서 소리 on/off 상태 불러오기
        val sharedPreferencesVoice =getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val soundState = sharedPreferencesVoice.getBoolean("soundState", false)

        // 현재 날짜 가져오기
        val currentDate = getCurrentDate()

        // SharedPreferences 객체 생성
        val sharedPreferences = getSharedPreferences("transferInfo", Context.MODE_PRIVATE)

        // 데이터 불러오기
        val savedReceiverName = sharedPreferences.getString("recipientName", "")
        val savedMoneyAmount = sharedPreferences.getString("moneyAmount", "")

        // RecyclerView 초기화
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        //더미데이터 (내정보> 송금내역에 뜨는 데이터들)
        val dataList = mutableListOf<TransferHistoryAdapter.DataModel>()

        // 받는 사람의 이름과 송금 금액이 null이 아닐 때 dataList에 추가
        if (savedReceiverName != null && savedMoneyAmount != null) {
            // 현재 날짜 가져오기
            val currentDate = getCurrentDate()

            // dataList에 추가
            dataList.add(
                TransferHistoryAdapter.DataModel(
                    day = currentDate,
                    receiverName = savedReceiverName,
                    moneyAmount = savedMoneyAmount,
                    status = "송금 완료"
                )
            )
        } else {
            Toast.makeText(this@TransferHistoryActivity, "받는 사람의 정보가 없습니다.", Toast.LENGTH_SHORT).show()
        }



        dataList.add(
            TransferHistoryAdapter.DataModel(
                day = "2024-03-19",
                receiverName = "김유나",
                moneyAmount = "8500",
                /*time = "10:41",*/
                status = "송금 완료"
            )
        )

        dataList.add(
            TransferHistoryAdapter.DataModel(
                day = "2024-03-15",
                receiverName = "송지호",
                moneyAmount = "11000",
                /*time = "10:41",*/
                status = "송금 완료"
            )
        )

        dataList.add(
            TransferHistoryAdapter.DataModel(
                day = "2024-03-14",
                receiverName = "윤지민",
                moneyAmount = "5000",
                /*time = "10:41",*/
                status = "송금 완료"
            )
        )

        dataList.add(
            TransferHistoryAdapter.DataModel(
                day = "2024-03-11",
                receiverName = "신민영",
                moneyAmount = "2500",
                /*time = "10:41",*/
                status = "송금 완료"
            )
        )

        dataList.add(
            TransferHistoryAdapter.DataModel(
                day = "2024-03-09",
                receiverName = "이영선",
                moneyAmount = "50000",
                /*time = "10:41",*/
                status = "송금 완료"
            )
        )

        dataList.add(
            TransferHistoryAdapter.DataModel(
                day = "2024-03-07",
                receiverName = "김민호",
                moneyAmount = "1500",
                /*time = "10:41",*/
                status = "송금 완료"
            )
        )

        // LinearLayoutManager를 사용하여 수직 방향으로 아이템을 배치
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // 어댑터 설정
        val adapter = TransferHistoryAdapter(dataList)
        recyclerView.adapter = adapter

        binding.prevBtn.setOnClickListener {
            if (soundState) {
                onSpeech(binding.prevBtn.text)
            }
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
    // 현재 날짜 가져오는 메서드
    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun onSpeech(text: CharSequence) {
        mtts.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

}