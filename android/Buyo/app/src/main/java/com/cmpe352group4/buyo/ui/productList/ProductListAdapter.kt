package com.cmpe352group4.buyo.ui.productList

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.viewmodel.WishListViewModel
import com.cmpe352group4.buyo.vo.LikeResponse
import kotlinx.android.synthetic.main.item_product_list_recycler_view.view.*
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.fragment_product_detail_content.*
import kotlinx.android.synthetic.main.fragment_product_detail_content.view.*
import kotlinx.android.synthetic.main.fragment_wish_list.*
import javax.inject.Inject


class ProductListAdapter(
    var Products: MutableList<Product>,
    val sharedPref : SharedPref,
    val clickCallback: (Product) -> Unit,
    val likeCallback: (Product, View) -> Unit
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>(){

    var WishListProducts: List<Product>? = null


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

        fun bind(modal: Product) {
            itemView.iv_productListRecyclerView_Fav.tag = R.drawable.ic_product_disliked
            itemView.iv_productListRecyclerView_Cart.tag = R.drawable.ic_add2cart

            itemView.tv_productListRecyclerView_Info.text = "Brand: " + modal.brand + " / Vendor: " + modal.vendor.name + " / Vendor Rating : " + modal.vendor.rating.toString()
            itemView.tv_productListRecyclerView_Name.text = modal.name
            itemView.tv_productListRecyclerView_Rate.text = "Rating: " + modal.rating.toString()
            itemView.tv_productListRecyclerView_Price.text = modal.price.toString() + " TL"
            Glide.with(itemView.context)
                .load(modal.imageUrl).centerCrop()
                .into(itemView.iv_productListRecyclerView_Image)

            itemView.setOnClickListener { clickCallback.invoke(modal) }

            val prod_ids = WishListProducts?.map{it.id}


            if(prod_ids != null){
                if(prod_ids!!.contains(modal.id)){
                    if (itemView.iv_productListRecyclerView_Fav.tag == R.drawable.ic_product_disliked){
                        itemView.iv_productListRecyclerView_Fav.setImageResource(R.drawable.ic_product_liked)
                        itemView.iv_productListRecyclerView_Fav.tag = R.drawable.ic_product_liked
                    }
                } else{
                    if (itemView.iv_productListRecyclerView_Fav.tag == R.drawable.ic_product_liked){
                        itemView.iv_productListRecyclerView_Fav.setImageResource(R.drawable.ic_product_disliked)
                        itemView.iv_productListRecyclerView_Fav.tag = R.drawable.ic_product_disliked
                    }
                }
            }

            itemView.iv_productListRecyclerView_Fav.setOnClickListener { likeCallback.invoke(modal, itemView)}

            itemView.iv_productListRecyclerView_Cart.setOnClickListener {
                if (sharedPref.getUserId().isNullOrEmpty()) {
                    Log.v("ListRV","Guest User")

                } else {

                    // TODO : SEND BACKEND REQUEST HERE

                    if (it.iv_productListRecyclerView_Cart.tag == R.drawable.ic_add2cart){
                        it.iv_productListRecyclerView_Cart.setImageResource(R.drawable.ic_remove_from_cart)
                        it.iv_productListRecyclerView_Cart.tag = R.drawable.ic_remove_from_cart
                    }
                    else if (it.iv_productListRecyclerView_Cart.tag == R.drawable.ic_remove_from_cart){
                        it.iv_productListRecyclerView_Cart.setImageResource(R.drawable.ic_add2cart)
                        it.iv_productListRecyclerView_Cart.tag = R.drawable.ic_add2cart
                    }
                }
            }





        }

    }
}