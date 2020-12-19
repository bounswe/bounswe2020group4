package com.cmpe352group4.buyo.ui.wishList

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
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.MainActivity
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.viewmodel.WishListViewModel
import com.cmpe352group4.buyo.vo.LikeResponse
import com.cmpe352group4.buyo.vo.Product
import com.cmpe352group4.buyo.widgets.navigation_bar.NavigationBar
import kotlinx.android.synthetic.main.fragment_wish_list.*
import javax.inject.Inject

class WishListFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val wishListViewModel: WishListViewModel by viewModels {
        viewModelFactory
    }

    private var newWishListProducts: List<Product>? = null

    @Inject
    lateinit var sharedPref: SharedPref

    companion object {
        fun newInstance() = WishListFragment()
    }

    private val wishListAdapter by lazy {
        WishListAdapter(mutableListOf(),
            { productID ->
                wishListViewModel.onPostWhislistUpdate(LikeResponse( sharedPref.getUserId()?.toInt() ?: -1, productID))
                Log.v("berkay", "delete")
            },
            { productID ->
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(productID),
                    TransactionType.Replace, true
                )
            })

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wish_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(sharedPref.getUserId().isNullOrEmpty()){
            cl_nonlogin.visible = true
            cl_wishlist.visible = false
        }else{
            cl_nonlogin.visible = false
            cl_wishlist.visible = true
            wishListViewModel.onFetchWishListProducts(sharedPref.getUserId()?.toInt() ?: -1)
        }

        wishListViewModel.wishListProducts.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null) {

                wishListAdapter.submitList(it.data.products as MutableList<Product>)

                dispatchLoading()
            } else if (it.status == Status.ERROR) {
                dispatchLoading()
            } else if (it.status == Status.LOADING) {
                showLoading()
            }
        })

        wishListViewModel.statusUnlike.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null) {

                Log.v("berkay", it.data.toString())

                dispatchLoading()
            } else if (it.status == Status.ERROR) {
                dispatchLoading()
            } else if (it.status == Status.LOADING) {
                showLoading()
            }
        })


        loginButton.setOnClickListener {
            (activity as MainActivity).changeActiveTab(NavigationBar.PROFILE_INDEX)
        }

        btnOrderWishList.setOnClickListener{
            TODO()
        }

        btnFilterWishList.setOnClickListener{
            TODO()
        }

        btnDeleteWishlist.setOnClickListener {
            TODO()
        }


        rvWishListProducts.adapter = wishListAdapter
        rvWishListProducts.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val decorator = DividerItemDecoration(rvWishListProducts.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rvWishListProducts.context, R.drawable.divider_drawable)!!)
        rvWishListProducts.addItemDecoration(decorator)


//        spWishList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                adapterView: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }

    }

    override fun onResume() {
        super.onResume()
        if(sharedPref.getUserId().isNullOrEmpty()){
            cl_nonlogin.visible = true
            cl_wishlist.visible = false
        }else{
            cl_nonlogin.visible = false
            cl_wishlist.visible = true
            wishListViewModel.onFetchWishListProducts(sharedPref.getUserId()?.toInt() ?: -1)
        }
    }

}