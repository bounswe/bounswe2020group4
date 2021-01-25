package com.cmpe352group4.buyo.ui.vendor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.viewmodel.ProductViewModel
import com.cmpe352group4.buyo.viewmodel.SearchViewModel
import com.cmpe352group4.buyo.viewmodel.VendorViewModel
import com.cmpe352group4.buyo.vo.Product
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_vendor_product_list.*
import javax.inject.Inject

class VendorProductListFragment: BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val gson = Gson()

    private val sampleProductsViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }
    private val productViewModel: ProductViewModel by viewModels {
        viewModelFactory
    }

    private val vendorViewModel : VendorViewModel by viewModels {
        viewModelFactory
    }

    private val vendorProductListAdapter by lazy {
        VendorProductListAdapter(mutableListOf(), {seeProduct ->
            navigationManager?.onReplace(
                ProductDetailContentFragment.newInstance(seeProduct.id),
                TransactionType.Replace, true
            )
        },
        {editProduct ->

            var product_detail : Product

            productViewModel.onFetchProductById(editProduct.id)
            productViewModel.productDetail.observe(viewLifecycleOwner, Observer {
                Log.d("LikedProdStParse", "$it.status")
                if (it.status == Status.SUCCESS && it.data != null){

                    product_detail = it.data.result

                    navigationManager?.onReplace(
                        AddProductFragment.newInstance(mode = "edit", product = gson.toJson(product_detail), categories = product_detail.category.joinToString(",")),
                        TransactionType.Replace, true
                    )

                    dispatchLoading()
                } else if (it.status == Status.ERROR){
                    dispatchLoading()
                }else if (it.status == Status.LOADING){
                    showLoading()
                }
            })

        },
            {product ->

                navigationManager?.onReplace(
                    DeleteProductFragment.newInstance(id = product.id, name = product.name),
                    TransactionType.Replace, true
                )
            })
    }

    companion object {
        fun newInstance() = VendorProductListFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_vendor_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val decorator = DividerItemDecoration(rv_vendorProductList.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rv_vendorProductList.context, R.drawable.divider_drawable)!!)
        rv_vendorProductList.addItemDecoration(decorator)

        rv_vendorProductList.adapter = vendorProductListAdapter

        rv_vendorProductList.layoutManager = LinearLayoutManager(this.context)


        vendorViewModel.onFetchVendorProducts(sharedPref.getUserId() ?: "")

        vendorViewModel.vendorProducts.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null){
                vendorProductListAdapter.submitList(it.data.result.productList as MutableList<Product>)
                dispatchLoading()
            } else if (it.status == Status.ERROR){
                dispatchLoading()
            }else if (it.status == Status.LOADING){
                showLoading()
            }
        })


        /*

        sampleProductsViewModel.onFetchSearchResultbyKeyword("d", emptyMap<String, String>())

        sampleProductsViewModel.searchResult.observe(viewLifecycleOwner, Observer {

            if (it.status == Status.SUCCESS && it.data != null){
                vendorProductListAdapter.submitList(it.data.products.productList as MutableList<Product>)

                dispatchLoading()
            } else if (it.status == Status.ERROR){
                dispatchLoading()
            }else if (it.status == Status.LOADING){
                showLoading()
            }
        })
        */

        btn_vendorProductList_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}