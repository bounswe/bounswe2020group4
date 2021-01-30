package com.cmpe352group4.buyo.ui.vendor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.item_vendor_product_list.view.*

class VendorProductListAdapter(
    var Products: MutableList<Product>,
    val callbackSeeProduct : (Product) -> Unit,
    val callbackEditProduct : (Product) -> Unit,
    val callbackDeleteProduct : (Product) -> Unit
) : RecyclerView.Adapter<VendorProductListAdapter.VendorProductListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorProductListAdapter.VendorProductListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vendor_product_list, parent, false)

        return VendorProductListViewHolder(view)
    }

    override fun onBindViewHolder(holder: VendorProductListAdapter.VendorProductListViewHolder, position: Int) {
        holder.bind(Products[position])
    }

    override fun getItemCount(): Int {
        return Products.size
    }

    fun submitList(list: MutableList<Product>) {
        this.Products.clear()
        this.Products.addAll(list)
        notifyDataSetChanged()
    }


    inner class VendorProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(modal: Product) {
            Glide.with(itemView.context)
                .load(modal.imageUrl).centerCrop()
                .into(itemView.iv_vendorProductList_image)

            itemView.tv_vendorProductList_name.text = modal.name
            itemView.tv_vendorProductList_price.text = modal.price.toString()
            itemView.tv_vendorProductList_rate.text = modal.rating.toString()
            itemView.tv_vendorProductList_info.text = modal.brand + " - Ori. Price: " + modal.originalPrice.toString() + "\n" +
                    modal.description

            itemView.setOnClickListener {
                callbackSeeProduct.invoke(modal)
            }

            itemView.iv_vendorProductList_editIcon.setOnClickListener {
                callbackEditProduct.invoke(modal)
            }

            itemView.iv_vendorProductList_deleteIcon.setOnClickListener {
                callbackDeleteProduct.invoke(modal)
            }

        }
    }
}