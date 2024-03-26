package com.example.graduation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PayHistoryAdapter (private val dataList: List<DataModel>) : RecyclerView.Adapter<PayHistoryAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            // 아이템 뷰의 각 요소에 대한 참조를 제공합니다.
            val dayTextView: TextView = itemView.findViewById(R.id.day_tv)
            val productPlaceTextView: TextView = itemView.findViewById(R.id.product_place_tv)
            val productPriceTextView: TextView = itemView.findViewById(R.id.product_price_tv)
      /*      val timeTextView: TextView = itemView.findViewById(R.id.time_tv)*/
            val statusTextView: TextView = itemView.findViewById(R.id.status_tv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // 새로운 뷰를 생성
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pay_history, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // 데이터를 아이템 뷰에 바인딩합니다.
            val data = dataList[position]

            holder.dayTextView.text = data.day
            holder.productPlaceTextView.text = data.productPlace
            holder.productPriceTextView.text = data.productPrice.toString()
           /* holder.timeTextView.text = data.time*/
            holder.statusTextView.text = data.status
        }

        override fun getItemCount(): Int {
            // 데이터 세트의 크기를 반환합니다.
            return dataList.size
        }

        // 데이터 모델 클래스
        data class DataModel(
            val day: String,
            val productPlace: String,
            val productPrice:Int,
            /*val time: String,*/
            val status: String
        )
    }

