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
            var date = "Order Date: " + modal.orderDate.split("T")[0]

            itemView.tv_customer_id.text = customerId
            itemView.tv_order_no.text = orderNo
            itemView.iv_productPhoto.loadFromURL(modal.imageUrl)
            itemView.tv_productName.text = productName
            itemView.tv_addressInfo.text = address
            itemView.tv_price.text = price
            itemView.tv_date.text = date
            itemView.tv_status.text = "Status: " + orderStatus

            // possible states = Pending, Approved, Shipped, Delivered at <date>, Cancelled by the customer, Cancelled by the vendor
            // "#E53C38" light red
            // "#a4c639" light green
            // "#fedebe" light orange

            if (orderStatus=="Pending") {
                itemView.tv_header.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.tv_status.setTextColor(Color.BLACK)
                itemView.tv_customer_id.setTextColor(Color.BLACK)

                itemView.first_button.visibility = View.VISIBLE
                itemView.first_button.text = "Approve"
                itemView.first_button.setBackgroundColor(Color.parseColor("#a4c639")) // light green
                itemView.first_button.setTextColor(Color.WHITE)

                itemView.second_button.visibility = View.VISIBLE
                itemView.second_button.text = "Reject"
                itemView.second_button.setBackgroundColor(Color.parseColor("#E53C38")) // light red
                itemView.second_button.setTextColor(Color.WHITE)
            }
            else if (orderStatus=="Approved") {
                itemView.tv_header.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.tv_status.setTextColor(Color.BLACK)
                itemView.tv_customer_id.setTextColor(Color.BLACK)

                itemView.first_button.visibility = View.VISIBLE
                itemView.first_button.text = "Shipped"
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
                itemView.first_button.text = "Message Customer"
                itemView.first_button.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.first_button.setTextColor(Color.BLACK)

                itemView.second_button.visibility = View.GONE
            }
            else if (orderStatus.startsWith("Delivered")) {
                itemView.tv_header.setBackgroundColor(Color.parseColor("#a4c639")) // light green
                itemView.tv_status.setTextColor(Color.BLACK)
                itemView.tv_customer_id.setTextColor(Color.BLACK)

                itemView.first_button.visibility = View.VISIBLE
                itemView.first_button.text = "Message Customer"
                itemView.first_button.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.first_button.setTextColor(Color.BLACK)

                itemView.second_button.visibility = View.GONE
            }
            else if(orderStatus=="Returned"){
                itemView.tv_header.setBackgroundColor(Color.parseColor("#E53C38")) // light red
                itemView.tv_status.setTextColor(Color.WHITE)
                itemView.tv_customer_id.setTextColor(Color.WHITE)

                itemView.first_button.visibility = View.VISIBLE
                itemView.first_button.text = "Message Customer"
                itemView.first_button.setBackgroundColor(Color.parseColor("#fedebe")) // light orange
                itemView.first_button.setTextColor(Color.BLACK)

                itemView.second_button.visibility = View.GONE
            }
            else {
                itemView.tv_header.setBackgroundColor(Color.parseColor("#E53C38")) // light red
                itemView.tv_status.setTextColor(Color.WHITE)
                itemView.tv_customer_id.setTextColor(Color.WHITE)

                itemView.first_button.visibility = View.VISIBLE
                itemView.first_button.text = "Message Customer"
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