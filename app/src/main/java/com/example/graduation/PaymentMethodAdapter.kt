package com.example.graduation


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class PaymentMethodAdapter(
    private val paymentMethods: List<PaymentMethod>,
    private val clickListener: PaymentMethodClickListener
) : RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.bank_account_iv)
        val textView: TextView = itemView.findViewById(R.id.bank_name_tv)
  /*      val bankbookNumber: TextView = itemView.findViewById(R.id.account_number_tv)
*/
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    if (selectedPosition != position) {
                        //이미 선택되어있던 아이템이 있으면 해제
                        notifyItemChanged(selectedPosition)

                        // 클릭된 아이템 선택
                        selectedPosition = position
                        notifyItemChanged(position)


                        val paymentMethod = paymentMethods[position]
                        clickListener.onPaymentMethodClick(paymentMethod)
                    }
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

        //선택된 아이템 하이라이트
        holder.itemView.isSelected = selectedPosition == position
    }

    override fun getItemCount(): Int = paymentMethods.size
}
