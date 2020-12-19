package com.cmpe352group4.buyo.ui.cart

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.cmpe352group4.buyo.R
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.util.extensions.inflate
import com.cmpe352group4.buyo.vo.CartProduct
import kotlinx.android.synthetic.main.item_cart.view.*

class CartAdapter (
    private val cartList: MutableList<CartProduct>,
    // This callback's parameter can be changed
    val clickDeleteItemFromCartCallback: (CartProduct) -> Unit
): RecyclerView.Adapter<CartAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_cart))
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    fun submitList(list: MutableList<CartProduct>) {
        this.cartList.clear()
        this.cartList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val view = view

        fun bind(modal: CartProduct) {

            var vendorName = "Vendor: " + modal.vendor
            var productName = "Product: " + modal.name
            var productInfo = "Brand: " + modal.brand + "\n" +
                              "Size: " + modal.size + "\n" +
                              "Color: " + modal.color
            itemView.tv_vendor.text = vendorName
            itemView.tv_productName.text = productName
            itemView.tv_productInfo.text = productInfo

            //Delete item from cart
            itemView.iv_delete.setOnClickListener { clickDeleteItemFromCartCallback.invoke(modal) }
            // Vendor'a tıklanıp vendor profile'a gidilebilir it may be implemented later
            // Same as vendor, we can direct user to product detail page by clicking product image or name.
            //Amount field may be added later and it needs endpoints too.
        }

    }

}