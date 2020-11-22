package com.cmpe352group4.buyo.ui.productList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmpe352group4.buyo.R
import kotlinx.android.synthetic.main.item_product_list_recycler_view.view.*
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.fragment_product_detail_content.*
import kotlinx.android.synthetic.main.fragment_product_detail_content.view.*


class ProductListAdapter(
    var Products: MutableList<Product>,
    val clickCallback: (Product) -> Unit
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list_recycler_view, parent, false)
        return ProductListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
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

    inner class ProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val view = itemView

        fun bind(modal: Product) {

            itemView.tv_productListRecyclerView_Info.text = "PRODUCT INFO"
            itemView.tv_productListRecyclerView_Name.text = modal.name
            itemView.tv_productListRecyclerView_Rate.text = modal.rating.toString()
            itemView.tv_productListRecyclerView_Price.text = modal.price.toString() + " TL"
            Glide.with(itemView.context)
                .load(modal.imageUrl).centerCrop()
                .into(itemView.iv_productListRecyclerView_Image)

            itemView.setOnClickListener { clickCallback.invoke(modal) }
        }

    }
}