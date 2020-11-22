package com.cmpe352group4.buyo.ui.productList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
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
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.viewmodel.CategoryViewModel
import com.cmpe352group4.buyo.viewmodel.ProductViewModel
import com.cmpe352group4.buyo.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_product_list.*
import com.cmpe352group4.buyo.vo.Product
import com.cmpe352group4.buyo.vo.Vendor
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_product_detail_content.*
import kotlinx.android.synthetic.main.item_product_list_recycler_view.*
import java.util.ArrayList
import javax.inject.Inject


class ProductListFragment : BaseFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val productListViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }


    companion object {
        private const val KEYWORD = "search_keyword"
        private const val CATEGORY_PATH = "category_path"
        fun newInstance(keyword: String? = "", path: String? = "") = ProductListFragment().apply {
            arguments = Bundle().apply {
                putString(CATEGORY_PATH, path)
                putString(KEYWORD, keyword)
            }
        }
    }

    private val productListAdapter by lazy {
        ProductListAdapter(mutableListOf()
        ) { product ->
            navigationManager?.onReplace(
                ProductDetailContentFragment.newInstance(product.id),
                TransactionType.Replace, true
            )

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val keyword = arguments?.getString(KEYWORD) ?: "empty"

        val category = arguments?.getString(CATEGORY_PATH) ?: "empty"


        // SEARCH API CALL

        if (keyword == ""){ // Category call

            val categoryList = category.split(",").toList()

            //Log.v("ProductList C", categoryList.toString())

            var query_string = "["

            for (cat in categoryList){
                query_string = query_string + "\""+ cat + "\","
            }

            query_string = query_string.dropLast(1) + "]"

            //Log.v("ProductList S", query_string)

            productListViewModel.onFetchSearchResultbyCategory(query_string)

            productListViewModel.categoryResult.observe(viewLifecycleOwner, Observer {
                if (it.status == Status.SUCCESS && it.data != null){
                    Log.v("Products of the keyword", it.data.products.toString())

                    tv_productListCategoryName.text = categoryList.joinToString(separator = "/")


                    productListAdapter.submitList(it.data.products as MutableList<Product>)

                    dispatchLoading()
                } else if (it.status == Status.ERROR){
                    dispatchLoading()
                }else if (it.status == Status.LOADING){
                    showLoading()
                }
            })


        }else if(category == ""){ // Keyword call
            productListViewModel.onFetchSearchResultbyKeyword(keyword)

            productListViewModel.searchResult.observe(viewLifecycleOwner, Observer {

                if (it.status == Status.SUCCESS && it.data != null){
                    Log.v("Products of the keyword", it.data.products.toString())

                    tv_productListCategoryName.text = keyword

                    productListAdapter.submitList(it.data.products as MutableList<Product>)

                    dispatchLoading()
                } else if (it.status == Status.ERROR){
                    dispatchLoading()
                }else if (it.status == Status.LOADING){
                    showLoading()
                }
            })


        }else{
            Log.i("ProductList", "Product list page got neither keyword nor category.")
            Log.i("ProductListK" , keyword)

            Log.i("ProductListC" , category)
        }



        // BACK BUTTON

        btnProductListBack.setOnClickListener {
            activity?.onBackPressed()
        }


        // SEARCH BAR

        sv_productListSearchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                if (keyword == "") {
                    return false
                } else {
                    navigationManager?.onReplace(
                        ProductListFragment.newInstance(keyword = keyword),
                        TransactionType.Replace, true
                    )
                    return true
                }
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })




        // RECYCLER VIEW

        val decorator = DividerItemDecoration(rv_ProductList.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rv_ProductList.context, R.drawable.divider_drawable)!!)
        rv_ProductList.addItemDecoration(decorator)

        rv_ProductList.adapter = productListAdapter

        rv_ProductList.layoutManager = LinearLayoutManager(this.context)


        // ORDER SPINNER

        sp_ProductListSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (adapterView?.getItemAtPosition(position).toString() == "Name") {
                    var prodList = productListAdapter.Products
                    prodList.sortBy { product -> product.name }
                    productListAdapter.notifyDataSetChanged()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Price (Highest)") {
                    var prodList = productListAdapter.Products
                    prodList.sortBy { product -> product.price }
                    productListAdapter.notifyDataSetChanged()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Rating") {
                    var prodList = productListAdapter.Products
                    prodList.sortBy { product -> product.rating }
                    productListAdapter.notifyDataSetChanged()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)") {

                } else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        // FILTER SPINNER

        sp_ProductListFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (adapterView?.getItemAtPosition(position).toString() == "Category") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Category") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Vendor") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Price Range") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position)
                        .toString() == "Category Specific"
                ) {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

}