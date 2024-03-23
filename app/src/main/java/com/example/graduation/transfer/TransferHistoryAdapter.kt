package com.example.graduation.transfer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.graduation.PayHistoryAdapter
import com.example.graduation.R

class TransferHistoryAdapter (private val dataList: List<DataModel>) : RecyclerView.Adapter<TransferHistoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 아이템 뷰의 각 요소에 대한 참조를 제공합니다.
        val dayTextView: TextView = itemView.findViewById(R.id.day_tv)
        val receiverNameTv: TextView = itemView.findViewById(R.id.receiver_name_tv)
        val moneyAmountTv: TextView = itemView.findViewById(R.id.money_amount_tv)
/*        val timeTv: TextView = itemView.findViewById(R.id.time_tv)*/
        val statusTv: TextView = itemView.findViewById(R.id.status_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 새로운 뷰를 생성
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transfer_history, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 데이터를 아이템 뷰에 바인딩합니다.
        val data = dataList[position]

        holder.dayTextView.text = data.day
        holder.receiverNameTv.text = data.receiverName
        holder.moneyAmountTv.text = data.moneyAmount
      /*  holder.timeTv.text = data.time*/
        holder.statusTv.text = data.status
    }

    override fun getItemCount(): Int {
        // 데이터 세트의 크기를 반환합니다.
        return dataList.size
    }

    // 데이터 모델 클래스
    data class DataModel(
        val day: String,
        val receiverName: String,
        val moneyAmount: String,
/*        val time: String,*/
        val status: String
    )
}

