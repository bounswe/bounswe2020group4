package com.cmpe352group4.buyo.ui.orderpage
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.cmpe352group4.buyo.R
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.util.extensions.inflate
import com.cmpe352group4.buyo.util.extensions.loadFromURL
import com.cmpe352group4.buyo.vo.OrderProductRV
import kotlinx.android.synthetic.main.item_orders.view.*
import kotlinx.android.synthetic.main.item_orders.view.dropdown_detail
import kotlinx.android.synthetic.main.item_orders.view.first_button
import kotlinx.android.synthetic.main.item_orders.view.iv_productPhoto
import kotlinx.android.synthetic.main.item_orders.view.product_detail
import kotlinx.android.synthetic.main.item_orders.view.second_button
import kotlinx.android.synthetic.main.item_orders.view.tv_addressInfo
import kotlinx.android.synthetic.main.item_orders.view.tv_date
import kotlinx.android.synthetic.main.item_orders.view.tv_header
import kotlinx.android.synthetic.main.item_orders.view.tv_order_no
import kotlinx.android.synthetic.main.item_orders.view.tv_price
import kotlinx.android.synthetic.main.item_orders.view.tv_productName
import kotlinx.android.synthetic.main.item_orders.view.tv_status
import kotlinx.android.synthetic.main.item_orders_vendor.view.*

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
            var price = (modal.price*modal.quantity).toString() + "₺" + " - " + "Amount: " +
                                                                    modal.quantity.toString()
            var address = modal.address
            var orderStatus = modal.status
            var attributes = modal.attributes
            var date = "Order Date: " + modal.orderDate.split("T")[0]

            itemView.tv_vendor.text = vendorName
            itemView.tv_order_no.text = orderNo
            itemView.iv_productPhoto.loadFromURL(modal.imageUrl)
            itemView.tv_productName.text = productName
            itemView.tv_addressInfo.text = address
            itemView.tv_price.text = price
            itemView.tv_date.text = date
            itemView.tv_status.text = "Status: " + orderStatus

            if (orderStatus=="Pending") {
                itemView.tv_header.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.tv_status.setTextColor(Color.BLACK)
                itemView.tv_customer_id.setTextColor(Color.BLACK)

                itemView.first_button.visibility = View.VISIBLE
                itemView.first_button.text = "Message Vendor"
                itemView.first_button.setBackgroundColor(Color.parseColor("#a4c639")) // light green
                itemView.first_button.setTextColor(Color.WHITE)

                itemView.second_button.visibility = View.VISIBLE
                itemView.second_button.text = "Cancel"
                itemView.second_button.setBackgroundColor(Color.parseColor("#E53C38")) // light red
                itemView.second_button.setTextColor(Color.WHITE)
            }
            else if (orderStatus=="Approved") {
                itemView.tv_header.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.tv_status.setTextColor(Color.BLACK)
                itemView.tv_customer_id.setTextColor(Color.BLACK)

                itemView.first_button.visibility = View.VISIBLE
                itemView.first_button.text = "Message Vendor"
                itemView.first_button.setBackgroundColor(Color.parseColor("#a4c639")) // light green
                itemView.first_button.setTextColor(Color.WHITE)

                itemView.second_button.visibility = View.VISIBLE
                itemView.second_button.text = "Cancel"
                itemView.second_button.setBackgroundColor(Color.parseColor("#E53C38")) // light red
                itemView.second_button.setTextColor(Color.WHITE)
            }
            else if (orderStatus=="Shipped") {
                itemView.tv_header.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.tv_status.setTextColor(Color.BLACK)
                itemView.tv_customer_id.setTextColor(Color.BLACK)

                itemView.first_button.visibility = View.VISIBLE
                itemView.first_button.text = "Message Vendor"
                itemView.first_button.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.first_button.setTextColor(Color.BLACK)

                itemView.second_button.visibility = View.GONE
            }
            else if (orderStatus.startsWith("Delivered")) {
                itemView.tv_header.setBackgroundColor(Color.parseColor("#a4c639")) // light green
                itemView.tv_status.setTextColor(Color.BLACK)
                itemView.tv_customer_id.setTextColor(Color.BLACK)

                itemView.first_button.visibility = View.VISIBLE
                itemView.first_button.text = "Message Vendor"
                itemView.first_button.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.first_button.setTextColor(Color.BLACK)

                itemView.second_button.visibility = View.VISIBLE
                itemView.second_button.text = "Add Comment"
                itemView.second_button.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.second_button.setTextColor(Color.BLACK)
            }
            else {
                itemView.tv_header.setBackgroundColor(Color.parseColor("#E53C38")) // light red
                itemView.tv_status.setTextColor(Color.WHITE)
                itemView.tv_customer_id.setTextColor(Color.WHITE)

                itemView.first_button.visibility = View.VISIBLE
                itemView.first_button.text = "Message Vendor"
                itemView.first_button.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.first_button.setTextColor(Color.BLACK)

                itemView.second_button.visibility = View.GONE
            }



            itemView.setOnClickListener { clickCallback.invoke(modal) }
            itemView.first_button.setOnClickListener { firstButtonCallback.invoke(modal) }
            itemView.second_button.setOnClickListener { secondButtonCallback.invoke(modal) }
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