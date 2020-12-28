package com.cmpe352group4.buyo.ui.orderpage
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.cmpe352group4.buyo.R
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.util.extensions.inflate
import com.cmpe352group4.buyo.util.extensions.loadFromURL
import com.cmpe352group4.buyo.vo.OrderProductRV
import kotlinx.android.synthetic.main.item_cart.view.iv_productPhoto
import kotlinx.android.synthetic.main.item_cart.view.tv_productName
import kotlinx.android.synthetic.main.item_cart.view.tv_vendor
import kotlinx.android.synthetic.main.item_orders.view.*

class OrderAdapter (
    private val paidOrderList: MutableList<OrderProductRV>,
    val clickCallback: (OrderProductRV) -> Unit,
    val firstButtonCallback: (OrderProductRV) -> Unit,
    val secondButtonCallback: (OrderProductRV) -> Unit
): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_orders))
    }

    override fun getItemCount(): Int {
        return paidOrderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(paidOrderList[position])
    }

    fun submitList(list: MutableList<OrderProductRV>) {
        this.paidOrderList.clear()
        this.paidOrderList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(modal: OrderProductRV) {

            var vendorName = "Vendor: " + modal.vendor.name
            var orderNo = "Order No: " + modal.orderID
            var productName = modal.name
            var price = "$" + modal.price.toString()
            var address = modal.address
            var isDelivered = modal.status

            itemView.tv_vendor.text = vendorName
            itemView.tv_order_no.text = orderNo
            itemView.iv_productPhoto.loadFromURL(modal.imageUrl)
            itemView.tv_productName.text = productName
            itemView.tv_addressInfo.text = address
            itemView.tv_price.text = price

            if (isDelivered=="Pending") {
                var date = "Order Date: " + modal.orderDate
                itemView.tv_date.text = date
                itemView.tv_header.setBackgroundColor(Color.parseColor("#E53C38")) // light red
                itemView.tv_status.text = "Status: Not Delivered"
            } else {
                var date = "Order Date: " + modal.orderDate
                itemView.tv_date.text = date
                itemView.tv_header.setBackgroundColor(Color.parseColor("#a4c639")) // light green
                itemView.btn_cancel_order.text = "Add Comment"
                itemView.btn_cancel_order.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.btn_cancel_order.setTextColor(Color.BLACK)
                itemView.tv_status.text = "Status: Delivered"
            }
            itemView.setOnClickListener { clickCallback.invoke(modal) }
            itemView.btn_message_vendor.setOnClickListener { firstButtonCallback.invoke(modal) }
            itemView.btn_cancel_order.setOnClickListener { secondButtonCallback.invoke(modal) }
        }
    }
}