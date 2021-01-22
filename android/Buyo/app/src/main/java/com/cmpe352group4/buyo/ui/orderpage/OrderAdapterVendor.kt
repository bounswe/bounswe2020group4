package com.cmpe352group4.buyo.ui.orderpage
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.cmpe352group4.buyo.R
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.util.extensions.inflate
import com.cmpe352group4.buyo.util.extensions.loadFromURL
import com.cmpe352group4.buyo.vo.OrderProductVendorRV
import kotlinx.android.synthetic.main.item_orders_vendor.view.*

class OrderAdapterVendor (
    private val orderList: MutableList<OrderProductVendorRV>,
    val clickCallback: (OrderProductVendorRV) -> Unit,
    val firstButtonCallback: (OrderProductVendorRV) -> Unit,
    val secondButtonCallback: (OrderProductVendorRV) -> Unit
): RecyclerView.Adapter<OrderAdapterVendor.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_orders_vendor))
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    fun submitList(list: MutableList<OrderProductVendorRV>) {
        this.orderList.clear()
        this.orderList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(modal: OrderProductVendorRV) {

            var customerId = "Customer ID: " + modal.customerID
            var orderNo = "Order No: " + modal.orderID
            var productName = modal.name
            var price = (modal.price*modal.quantity).toString() + "₺" + " - " + "Amount: " +
                    modal.quantity.toString()
            var address = modal.address
            var orderStatus = modal.status
            var attributes = modal.attributes

            itemView.tv_customer_id.text = customerId
            itemView.tv_order_no.text = orderNo
            itemView.iv_productPhoto.loadFromURL(modal.imageUrl)
            itemView.tv_productName.text = productName
            itemView.tv_addressInfo.text = address
            itemView.tv_price.text = price

            if (orderStatus=="Pending") {
                var date = "Order Date: " + modal.orderDate.split("T")[0]
                itemView.tv_date.text = date
                itemView.tv_header.setBackgroundColor(Color.parseColor("#E53C38")) // light red
                itemView.tv_status.text = "Status: Pending"
            } else {
                var date = "Order Date: " + modal.orderDate.split("T")[0]
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
            itemView.dropdown_detail.setOnClickListener{
                if (itemView.dropdown_detail.text.toString() == "v") {
                    itemView.product_detail.visibility = View.VISIBLE
                    itemView.dropdown_detail.text = "ᴧ"
                    itemView.product_detail.text = ""
                    for (attr in attributes){
                        itemView.product_detail.text = itemView.product_detail.text.toString() +
                                attr.name + ": " + attr.value + "\n"
                    }
                    itemView.product_detail.text = itemView.product_detail.text.toString().dropLast(1)
                } else {
                    itemView.product_detail.visibility = View.GONE
                    itemView.dropdown_detail.text = "v"
                }
            }
        }
    }
}