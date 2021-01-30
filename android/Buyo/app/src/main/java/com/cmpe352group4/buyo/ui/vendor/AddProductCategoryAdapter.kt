package com.cmpe352group4.buyo.ui.vendor

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.util.extensions.inflate
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.vo.Category
import kotlinx.android.synthetic.main.item_categories.view.*
import kotlinx.android.synthetic.main.item_categories.view.tv_categoryName
import kotlinx.android.synthetic.main.item_vendor_add_product_categories.view.*

class AddProductCategoryAdapter (
    private val categoryList: MutableList<Category>,
    val clickCallback: (String) -> Unit,
    val clickSubCategoryCallBack: (Int) -> Unit
    ): RecyclerView.Adapter<AddProductCategoryAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(parent.inflate(R.layout.item_vendor_add_product_categories))
        }

        override fun getItemCount(): Int {
            return categoryList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(categoryList[position], position)
        }

        fun submitList(list: MutableList<Category>) {
            this.categoryList.clear()
            this.categoryList.addAll(list)
            notifyDataSetChanged()
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            val view = view

            fun bind(modal: Category, position: Int) {

                // Get the category of the added product

                if(modal.subcategories.isNullOrEmpty()){
                    itemView.iv_vendorAddProductCategories_item.visible = false
                }
                itemView.tv_vendorAddProductCategories_item.text = modal.name
                itemView.tv_vendorAddProductCategories_item.setOnClickListener { clickCallback.invoke(modal.path) }
                itemView.iv_vendorAddProductCategories_item.setOnClickListener{ clickSubCategoryCallBack.invoke(position) }

            }

        }
}