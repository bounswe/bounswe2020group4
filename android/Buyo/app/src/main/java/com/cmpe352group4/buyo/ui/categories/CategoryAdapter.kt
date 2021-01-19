package com.cmpe352group4.buyo.ui.categories

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.util.extensions.inflate
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.vo.Category
import kotlinx.android.synthetic.main.item_categories.view.*


class CategoryAdapter (
    private val categoryList: MutableList<Category>,
    val clickCallback: (String) -> Unit,
    val clickSubCategoryCallBack: (Int) -> Unit
): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_categories))
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
            if(modal.subcategories.isNullOrEmpty()){
                itemView.iv_openSubCategories.visible = false
            }
            itemView.tv_categoryName.text = modal.name
            itemView.tv_categoryName.setOnClickListener { clickCallback.invoke(modal.path) }
            itemView.iv_openSubCategories.setOnClickListener{ clickSubCategoryCallBack.invoke(position) }

        }

    }

}