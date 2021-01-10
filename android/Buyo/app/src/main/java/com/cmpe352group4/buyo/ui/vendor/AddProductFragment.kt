package com.cmpe352group4.buyo.ui.vendor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.vo.ParsedAttribute
import com.cmpe352group4.buyo.vo.Product
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_product_detail_comments.*
import kotlinx.android.synthetic.main.fragment_vendor_add_product.*

class AddProductFragment : BaseFragment() {

    companion object {
        private const val MODE = "add_or_edit_mode"
        private const val PRODUCT = "product_if_edit"
        fun newInstance(mode : String, product : String) = AddProductFragment().apply {
            arguments = Bundle().apply {
                putString(MODE, mode)
                putString(PRODUCT, product)
            }
        }
    }

    private val addProductAdapter by lazy {
        AddProductAdapter(
            mutableListOf()
        ){

        }
    }


    val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_vendor_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val decorator = DividerItemDecoration(rv_vendorAddProductAttributes.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rv_vendorAddProductAttributes.context, R.drawable.divider_drawable)!!)
        rv_vendorAddProductAttributes.addItemDecoration(decorator)

        rv_vendorAddProductAttributes.adapter = addProductAdapter


        rv_vendorAddProductAttributes.layoutManager = LinearLayoutManager(this.context)

        val fragment_mode = arguments?.getString(MODE) ?: ""

        if (fragment_mode == "add"){

            var attNum : Int

            tiet_vendorAddProduct_AttNum.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
                    Log.v("VendorAddProduct", "beforeTextChanged: $text")
                }

                override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.v("VendorAddProduct", "onTextChanged: $text")
                    val parsedList = mutableListOf<ParsedAttribute>()
                    try {
                        attNum = text.toString().toInt()

                        for (i in 1..attNum){
                            parsedList.add(ParsedAttribute(att_name = "", att_value = mutableListOf()))
                        }

                        addProductAdapter.submitList(parsedList)


                    }catch (e: Exception){

                    }
                }

                override fun afterTextChanged(editable: Editable?) {
                    Log.v("VendorAddProduct", "afterTextChanged: "+editable.toString())
                }

            })


        }
        else if (fragment_mode == "edit"){

            val productJSON = arguments?.getString(PRODUCT) ?: ""

            val product = gson.fromJson(productJSON, Product::class.java)

            tiet_vendorAddProduct_AttNum.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
                    TODO("Not yet implemented")
                }

                override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                    TODO("Not yet implemented")
                }

                override fun afterTextChanged(editable: Editable?) {
                    TODO("Not yet implemented")
                }

            })


        }
        else{
            Log.e("VendorAddProduct", "UnknownMode")
        }


    }

}