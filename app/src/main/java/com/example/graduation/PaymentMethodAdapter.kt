package com.example.graduation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PaymentMethodAdapter(  private val paymentMethods: List<PaymentMethod>,
private val clickListener: PaymentMethodClickListener
) : RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val bankbookNumber: TextView = itemView.findViewById(R.id.account_number_tv)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val paymentMethod = paymentMethods[position]
                    clickListener.onPaymentMethodClick(paymentMethod)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_payment_method, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paymentMethod = paymentMethods[position]
        holder.imageView.setImageResource(paymentMethod.imageResId)
        holder.textView.text = paymentMethod.bank
    }

    override fun getItemCount(): Int = paymentMethods.size
}
