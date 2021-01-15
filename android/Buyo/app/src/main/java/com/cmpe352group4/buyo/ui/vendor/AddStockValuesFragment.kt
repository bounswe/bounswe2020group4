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
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_vendor_add_product.*
import kotlinx.android.synthetic.main.fragment_vendor_add_stock_value.*

class AddStockValuesFragment: BaseFragment() {

    private val addStockValuesAdapter by lazy {
        AddStockValuesAdapter(
            mutableListOf()
        ){

        }
    }

    companion object {
        private const val COMBINATIONS = "combinations"
        fun newInstance(combinations : String) = AddStockValuesFragment().apply {
            arguments = Bundle().apply {
                putString(COMBINATIONS, combinations)
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

        Log.v("AddStockValuesFragment", all_combinations)
        var combinations_list = gson.fromJson(all_combinations, List::class.java)
        Log.v("AddStockValuesFragment", combinations_list.toString())
        addStockValuesAdapter.submitList(combinations_list as MutableList<List<String>>)


        btn_vendorAddProductStock_Back.setOnClickListener {
            activity?.onBackPressed()
        }

        btn_vendorAddProductStock_Complete.setOnClickListener {
            // Send Backend request here and return to my products page
        }

    }
}