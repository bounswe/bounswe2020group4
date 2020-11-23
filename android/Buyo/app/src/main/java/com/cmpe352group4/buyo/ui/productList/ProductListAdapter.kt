package com.cmpe352group4.buyo.ui.productList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.viewmodel.WishListViewModel
import kotlinx.android.synthetic.main.item_product_list_recycler_view.view.*
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.fragment_product_detail_content.*
import kotlinx.android.synthetic.main.fragment_product_detail_content.view.*
import kotlinx.android.synthetic.main.fragment_wish_list.*
import javax.inject.Inject


class ProductListAdapter(
    var Products: MutableList<Product>,
    val clickCallback: (Product) -> Unit
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

        val view = itemView



        fun bind(modal: Product) {

            itemView.tv_productListRecyclerView_Info.text = "Brand: " + modal.brand + " / Vendor: " + modal.vendor.name + " / Vendor Rating : " + modal.vendor.rating.toString()
            itemView.tv_productListRecyclerView_Name.text = modal.name
            itemView.tv_productListRecyclerView_Rate.text = "Rating: " + modal.rating.toString()
            itemView.tv_productListRecyclerView_Price.text = modal.price.toString() + " TL"
            Glide.with(itemView.context)
                .load(modal.imageUrl).centerCrop()
                .into(itemView.iv_productListRecyclerView_Image)

            itemView.setOnClickListener { clickCallback.invoke(modal) }

            val prod_ids = WishListProducts?.map{it.id}

            Log.i("Liked Prods", prod_ids.toString())


            if (prod_ids != null) {
                if(prod_ids.contains(modal.id) ){

                    Log.i("ProductList", "This is liked " +modal.id.toString())


                    itemView.tb_productListRecyclerView_Fav.setOnCheckedChangeListener { button, isChecked ->
                        if (isChecked) {
                            Log.i("ProductList", "Already Liked")
                        } else {
                            Log.i("ProductList", "Liking")
                            button.toggle()
                            notifyDataSetChanged()
                        }
                    }
                }else{
                    Log.i("ProductList", "This is not liked " +modal.id.toString())
                    itemView.tb_productListRecyclerView_Fav.setOnCheckedChangeListener { button, isChecked ->
                        if (isChecked) {
                            Log.i("ProductList", "Unliking")
                            button.toggle()
                            notifyDataSetChanged()
                        } else {
                            Log.i("ProductList", "Already unliked")
                        }
                    }
                }
            }

        }

    }
}