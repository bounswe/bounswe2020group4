package com.cmpe352group4.buyo.ui.productList

import android.os.Bundle
import android.util.Log
import android.view.Gravity
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
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.viewmodel.CategoryViewModel
import com.cmpe352group4.buyo.viewmodel.ProductViewModel
import com.cmpe352group4.buyo.viewmodel.SearchViewModel
import com.cmpe352group4.buyo.viewmodel.WishListViewModel
import com.cmpe352group4.buyo.vo.LikeResponse
import kotlinx.android.synthetic.main.fragment_product_list.*
import com.cmpe352group4.buyo.vo.Product
import com.cmpe352group4.buyo.vo.Vendor
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_product_detail_content.*
import kotlinx.android.synthetic.main.item_product_list_recycler_view.*
import kotlinx.android.synthetic.main.item_product_list_recycler_view.view.*
import java.util.ArrayList
import javax.inject.Inject


class ProductListFragment : BaseFragment(){

    @Inject
    lateinit var sharedPref: SharedPref


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val productListViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    private val wishListViewModel: WishListViewModel by viewModels {
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
        ProductListAdapter(mutableListOf(), sharedPref,
            { product ->
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(product.id),
                    TransactionType.Replace, true
                )
            },
            { product, itemView ->
                if (sharedPref.getUserId().isNullOrEmpty()) {
                    Log.v("ListRV","Guest User")
                    val myToast = Toast.makeText(
                        context,
                        "You need to Login first!",
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                } else {
                    wishListViewModel.onPostWhislistUpdate(LikeResponse(sharedPref.getUserId() ?: "", product.id))

                    wishListViewModel.statusUnlike.observe(viewLifecycleOwner, Observer{
                        if (it.status == Status.SUCCESS && it.data != null) {

                            dispatchLoading()
                        } else if (it.status == Status.ERROR) {
                            dispatchLoading()
                        } else if (it.status == Status.LOADING) {
                            showLoading()
                        }
                    })
                    if (itemView.iv_productListRecyclerView_Fav.tag == R.drawable.ic_product_disliked) {
                        itemView.iv_productListRecyclerView_Fav.setImageResource(R.drawable.ic_product_liked)
                        itemView.iv_productListRecyclerView_Fav.tag = R.drawable.ic_product_liked
                    }
                    else if (itemView.iv_productListRecyclerView_Fav.tag == R.drawable.ic_product_liked) {
                        itemView.iv_productListRecyclerView_Fav.setImageResource(R.drawable.ic_product_disliked)
                        itemView.iv_productListRecyclerView_Fav.tag = R.drawable.ic_product_disliked
                    }
                }
            },{ toastText ->
                val myToast = Toast.makeText(
                    context,
                    toastText,
                    Toast.LENGTH_SHORT
                )
                myToast.setGravity(Gravity.BOTTOM, 0, 200)
                myToast.show()

            }
        )
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

        var fetchedProducts: List<Product>? = null


        // SEARCH API CALL

        if (keyword == ""){ // Category call

            val categoryList = category.split(",").toList()

            var query_string = "["

            for (cat in categoryList){
                query_string = query_string + "\""+ cat + "\","
            }

            query_string = query_string.dropLast(1) + "]"
            
            productListViewModel.onFetchSearchResultbyCategory(query_string)

            productListViewModel.categoryResult.observe(viewLifecycleOwner, Observer {
                if (it.status == Status.SUCCESS && it.data != null){
                    Log.v("Products of the keyword", it.data.products.toString())

                    tv_productListCategoryName.text = categoryList.joinToString(separator = "/")

                    fetchedProducts = it.data.products


                    if(sharedPref.getUserId().isNullOrEmpty()){
                        Log.i("ProductList", "Guest User")
                        productListAdapter.submitList(fetchedProducts as MutableList<Product>)
                    }else{
                        Log.i("Liked", "Sending Request")
                        wishListViewModel.onFetchWishListProducts(sharedPref.getUserId() ?: "")

                        wishListViewModel.wishListProducts.observe(viewLifecycleOwner, Observer {
                            if (it.status == Status.SUCCESS && it.data != null) {

                                productListAdapter.WishListProducts = it.data.products as MutableList<Product>

                                productListAdapter.submitList(fetchedProducts as MutableList<Product>)

                                dispatchLoading()
                            } else if (it.status == Status.ERROR) {
                                dispatchLoading()
                            } else if (it.status == Status.LOADING) {
                                showLoading()
                            }
                        })

                    }



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

                    fetchedProducts = it.data.products


                    if(sharedPref.getUserId().isNullOrEmpty()){
                        Log.i("ProductList", "Guest User")
                        productListAdapter.submitList(fetchedProducts as MutableList<Product>)
                    }else{
                        Log.i("Liked", "Sending Request")
                        wishListViewModel.onFetchWishListProducts(sharedPref.getUserId() ?: "")

                        wishListViewModel.wishListProducts.observe(viewLifecycleOwner, Observer {
                            if (it.status == Status.SUCCESS && it.data != null) {

                                productListAdapter.WishListProducts = it.data.products as MutableList<Product>

                                productListAdapter.submitList(fetchedProducts as MutableList<Product>)

                                dispatchLoading()
                            } else if (it.status == Status.ERROR) {
                                dispatchLoading()
                            } else if (it.status == Status.LOADING) {
                                showLoading()
                            }
                        })

                    }

                    dispatchLoading()
                } else if (it.status == Status.ERROR){
                    dispatchLoading()
                }else if (it.status == Status.LOADING){
                    showLoading()
                }
            })


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
                        newInstance(keyword = keyword),
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

        // FILTER SORT

        btn_ProductListSort.setOnClickListener {
            navigationManager?.onReplace(
                ListSortFilterFragment(),
                TransactionType.Replace, true
            )
        }



    }



}