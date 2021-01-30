package com.cmpe352group4.buyo.ui.vendor

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.homepage.HomepageFragment
import com.cmpe352group4.buyo.viewmodel.VendorViewModel
import com.cmpe352group4.buyo.vo.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_vendor_add_product.*
import kotlinx.android.synthetic.main.fragment_vendor_add_stock_value.*
import javax.inject.Inject

class AddStockValuesFragment: BaseFragment() {
    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val vendorViewModel: VendorViewModel by viewModels {
        viewModelFactory
    }

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
        private const val MODE = "mode"
        fun newInstance(combinations : String, product : String, att_names : String, mode : String) = AddStockValuesFragment().apply {
            arguments = Bundle().apply {
                putString(COMBINATIONS, combinations)
                putString(PRODUCT, product)
                putString(ATT_NAMES, att_names)
                putString(MODE, mode)
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

        val fragment_mode = arguments?.getString(AddStockValuesFragment.MODE) ?: ""


        btn_vendorAddProductStock_Complete.setOnClickListener {
            // Send Backend request here and return to my products page

            var newProductInfo : MutableList<ProductInfo> = mutableListOf()

            for ((options, stockValue) in stockValues){
                var opt_list = options.split("-")

                var product_info = ProductInfo(attributes = mutableListOf(), stockValue = stockValue)

                for (i in 0..opt_list.size-1){
                    var attribute = Attribute(name = att_names[i], value = opt_list[i])
                    product_info.attributes.add(attribute)
                }

                newProductInfo.add(product_info)
            }

            product.productInfos = newProductInfo



            val addProduct = AddProduct(
                id = product.id,
                category = product.category,
                name =  product.name,
                imageUrl = product.imageUrl,
                rating = product.rating,
                price = product.price,
                originalPrice = product.originalPrice,
                brand = product.brand,
                productInfos = product.productInfos,
                vendorId = sharedPref.getUserId() ?: "",
                description = product.description,
                materials = null,
                comments = product.comments,
                filterCriterias = product.filterCriterias
            )

            if (fragment_mode == "add"){
                Log.v("StockValuesMode", "ADD")
                vendorViewModel.onAddProduct(sharedPref.getUserId() ?: "", addProduct)

                vendorViewModel.addProduct.observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.SUCCESS && it.data != null) {
                        val myToast = Toast.makeText(
                            context,
                            "You have successfully added your product!",
                            Toast.LENGTH_SHORT
                        )
                        myToast.setGravity(Gravity.BOTTOM, 0, 200)
                        myToast.show()
                        dispatchLoading()

                        activity?.onBackPressed()
//                        navigationManager?.onReplace(
//                            HomepageFragment.newInstance(),
//                            TransactionType.Replace, true
//                        )
                    } else if (it.status == Status.ERROR) {
                        dispatchLoading()
                    } else if (it.status == Status.LOADING) {
                        showLoading()
                    }
                })

                Log.v("FinalizedProduct", addProduct.toString())

            }else if (fragment_mode == "edit"){
                Log.v("StockValuesMode", "EDIT")
                vendorViewModel.onUpdateProduct(product)

                vendorViewModel.updateProduct.observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.SUCCESS && it.data != null) {
                        val myToast = Toast.makeText(
                            context,
                            "You have successfully updated your product!",
                            Toast.LENGTH_SHORT
                        )
                        myToast.setGravity(Gravity.BOTTOM, 0, 200)
                        myToast.show()
                        dispatchLoading()

                        navigationManager?.onReplace(
                            HomepageFragment.newInstance(),
                            TransactionType.Replace, true
                        )
                    } else if (it.status == Status.ERROR) {
                        Log.e("StockValuesUpdateResp", "ERROR")
                        Log.e("StockValuesUpdateResp", it.message)
                        dispatchLoading()
                    } else if (it.status == Status.LOADING) {
                        showLoading()
                    }
                })

                Log.v("FinalizedProduct", product.toString())

            }else{
                Log.e("AddStockValue", "Unknown type!")
            }




        }

    }
}