import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.graduation.PaymentMethod
import com.example.graduation.PaymentMethodClickListener
import com.example.graduation.R

class PaymentMethodAdapter(
    private val paymentMethods: List<PaymentMethod>,
    private val clickListener: PaymentMethodClickListener
) : RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder>() {

    inner class PaymentMethodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bankLogoIv: ImageView = itemView.findViewById(R.id.bank_logo_iv)
        val korBankNameTv: TextView = itemView.findViewById(R.id.bank_name_tv)
        val engBankNameTv: TextView = itemView.findViewById(R.id.bank_eng_name_tv)
        val bankbookNumberTv: TextView = itemView.findViewById(R.id.account_number_tv)
        val checkImg: ImageView = itemView.findViewById(R.id.selected_check_iv)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onPaymentMethodClick(paymentMethods[position])
                }
            }
        }

        fun bind(paymentMethod: PaymentMethod) {
            bankLogoIv.setImageResource(paymentMethod.imageResId)
            engBankNameTv.text = paymentMethod.engBank
            korBankNameTv.text = paymentMethod.korBank
            bankbookNumberTv.text = " (${paymentMethod.accountNumber})"

            //카드 선택 유무에 따라 체크 표시 색깔 변경 (회색->노랑->회색)
            checkImg.setImageResource(
                if (paymentMethod.isSelected) R.drawable.img_check_yellow
                else R.drawable.img_check_grey
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_payment_method, parent, false)
        return PaymentMethodViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentMethodViewHolder, position: Int) {
        val paymentMethod = paymentMethods[position]
        holder.bind(paymentMethod)
    }

    override fun getItemCount(): Int = paymentMethods.size
}
