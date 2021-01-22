package com.cmpe352group4.buyo.ui.vendor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.vo.Attribute
import com.cmpe352group4.buyo.vo.Product
import com.cmpe352group4.buyo.vo.ProductInfo
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_vendor_add_product.*
import kotlinx.android.synthetic.main.fragment_vendor_add_stock_value.*

class AddStockValuesFragment: BaseFragment() {

    private val stockValues : MutableMap<String, Int> = mutableMapOf()


    private val addStockValuesAdapter by lazy {
        AddStockValuesAdapter(
            mutableListOf()
        ){ optionsList, stockValue ->
            stockValues[optionsList] = stockValue
        }
    }

    companion object {
        private const val COMBINATIONS = "combinations"
        private const val PRODUCT = "product"
        private const val ATT_NAMES = "attribute_names"
        fun newInstance(combinations : String, product : String, att_names : String) = AddStockValuesFragment().apply {
            arguments = Bundle().apply {
                putString(COMBINATIONS, combinations)
                putString(PRODUCT, product)
                putString(ATT_NAMES, att_names)
            }
        }

    }

    val gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_vendor_add_stock_value, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val decorator = DividerItemDecoration(rv_vendorAddStockValue.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rv_vendorAddStockValue.context, R.drawable.divider_drawable)!!)
        rv_vendorAddStockValue.addItemDecoration(decorator)

        rv_vendorAddStockValue.adapter = addStockValuesAdapter

        rv_vendorAddStockValue.layoutManager = LinearLayoutManager(this.context)

        var all_combinations = arguments?.getString(COMBINATIONS) ?: ""
        var combinations_list = gson.fromJson(all_combinations, List::class.java)
        addStockValuesAdapter.submitList(combinations_list as MutableList<List<String>>)

        var att_names_meta = arguments?.getString(ATT_NAMES) ?: ""

        var att_names = att_names_meta.split("%")

        var product_json = arguments?.getString(PRODUCT) ?: ""

        var product = gson.fromJson(product_json, Product::class.java)

        btn_vendorAddProductStock_Back.setOnClickListener {
            activity?.onBackPressed()
        }

        btn_vendorAddProductStock_Complete.setOnClickListener {
            // Send Backend request here and return to my products page

            for ((options, stockValue) in stockValues){
                var opt_list = options.split("-")

                var product_info = ProductInfo(attributes = mutableListOf(), stockValue = stockValue)

                for (i in 0..opt_list.size-1){
                    var attribute = Attribute(name = att_names[i], value = opt_list[i])
                    product_info.attributes.add(attribute)
                }

                product.productInfos.add(product_info)
            }

            Log.v("FinalizedProduct", product.toString())

        }

    }
}