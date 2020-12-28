package com.cmpe352group4.buyo.ui.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmpe352group4.buyo.R
import kotlinx.android.synthetic.main.square_product_item.view.*
import com.cmpe352group4.buyo.vo.Product

class ProductAdapter(
    var Products: MutableList<Product>,
    val clickCallback: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.square_product_item, parent, false)
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

            itemView.tv_squareProductItemName.text = modal.name
            itemView.tv_squareProductItemPrice.text = modal.price.toString() + " TL"
            Glide.with(itemView.context)
                .load(modal.imageUrl).centerCrop()
                .into(itemView.iv_squareProductImage)

            itemView.setOnClickListener { clickCallback.invoke(modal) }

        }

    }
}