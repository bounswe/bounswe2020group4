package com.cmpe352group4.buyo.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
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
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.viewmodel.CartViewModel
import com.cmpe352group4.buyo.vo.*
import com.cmpe352group4.buyo.vo.Attribute
import com.cmpe352group4.buyo.widgets.navigation_bar.NavigationBar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_cart.*
import javax.inject.Inject

class CartPageFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val cartViewModel: CartViewModel by activityViewModels {
        viewModelFactory
    }
    private val gson = Gson()
    var deletedProductName = ""
    @Inject
    lateinit var sharedPref: SharedPref


    companion object {
        fun newInstance() = CartPageFragment()
    }

    private val cartAdapter by lazy {
        CartAdapter(mutableListOf()
        ) { modal ->
            deletedProductName = modal.name
            cartViewModel.onRemove(RemoveCartRequest(customerId = sharedPref.getUserId() ?: "", productId = modal.id, productInfo = gson.toJson(RemoveCartInfo(modal.attributes))))
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(sharedPref.getUserType().equals("vendor")){
            tv_nonlogin.text = "You need to login as a customer."
            loginButton.text = "Profile Page"
            cl_non_login.visible = true
        }else{
            tv_nonlogin.text = "You need to login first"
            loginButton.text = "Login Page"
            if(checkLoginState()){
                cartViewModel.onFetchCartInfo(sharedPref.getUserId() ?: "")
            }
            initializeAdapter()
            setListeners()
            observeData()
        }

        loginButton.setOnClickListener {
            (activity as MainActivity).changeActiveTab(NavigationBar.PROFILE_INDEX)
        }

    }

    override fun onResume() {
        super.onResume()
        if(sharedPref.getUserType().equals("vendor")){
            tv_nonlogin.text = "You need to login as a customer."
            loginButton.text = "Profile Page"
            cl_non_login.visible = true
        }else{
            tv_nonlogin.text = "You need to login first"
            loginButton.text = "Login Page"
            clEmptyCart.visible = false
            if(checkLoginState()){
                cartViewModel.onFetchCartInfo(sharedPref.getUserId() ?: "")
            }
        }
    }

//    private fun dummyData() {
//        clNonEmptyCart.visible = true
//        tv_product_price_dollar.text = "144.99 ₺"
//        tv_discount_dollar.text = "45 ₺"
//        tv_final_price.text = "Total: 119.99 ₺"
//
//        dummyCartProduct.forEach { product ->
//            if(product.productInfo.size > 1 ){
//                product.productInfo.forEach {
//                    var newProduct = product.copy(productInfo = listOf(it))
//                    finalCartProducts.add(newProduct)
//                }
//            }else{
//                finalCartProducts.add(product)
//            }
//        }
//        cartAdapter.submitList(finalCartProducts)
//    }

    private fun checkLoginState(): Boolean {
        return if(sharedPref.getUserId().isNullOrEmpty()){
            cl_non_login.visible = true
            false
        }else{
            cl_non_login.visible = false
            true
        }
    }

    private fun initializeAdapter() {
        val decorator = DividerItemDecoration(rv_cart.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rv_cart.context, R.drawable.divider_drawable)!!)
        rv_cart.addItemDecoration(decorator)
        rv_cart.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv_cart.adapter = cartAdapter
    }

    private fun setListeners() {

        // If cart is empty, 'LET'S SHOP' button will direct user to homepage
        bt_emptyCart.setOnClickListener {
            (activity as MainActivity).changeActiveTab(NavigationBar.HOME_INDEX)
        }

        // Checkout tab price detail
        tv_final_price.setOnClickListener {
            cl_checkoutDetail.visible = true
            panelDim.visible = true
        }
        panelDim.setOnClickListener {
            panelDim.visible = false
            cl_checkoutDetail.visible = false
        }


        tv_checkout.setOnClickListener {
            navigationManager?.onReplace(
                CheckoutPageFragment.newInstance(),
                TransactionType.Replace, true
            )
        }
    }

    private fun observeData() {
        cartViewModel.cartInfo.removeObservers(viewLifecycleOwner)
        cartViewModel.cartInfo.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null) {
                if(it.data.products.products.isNullOrEmpty()){
                    clEmptyCart.visible = true
                    clNonEmptyCart.visible = false
                }else{
                    clEmptyCart.visible = false
                    clNonEmptyCart.visible = true

                    // After changes in model, we don't need anymore
//                    it.data.products.products?.forEach { product ->
//                        if(product.productInfo.size > 1 ){
//                            product.productInfo.forEach {
//                                var newProduct = product.copy(productInfo = listOf(it))
//                                finalCartProducts.add(newProduct)
//                            }
//                        }else{
//                            finalCartProducts.add(product)
//                        }
//                    }

                    tv_product_price_dollar.text = (it.data.products.totalPrice.toString() + "₺")
                    tv_discount_dollar.text = ((it.data.products.totalPrice.minus(it.data.products.discountedPrice)).toString() + "₺")
                    tv_final_price.text = ("Total: " + it.data.products.discountedPrice.plus(20).toString() + "₺")

                    cartAdapter.submitList(it.data.products.products as MutableList<CartProduct>)
                }

                dispatchLoading()
            } else if (it.status == Status.ERROR) {
                dispatchLoading()
            } else if (it.status == Status.LOADING) {
                showLoading()
            }
        })

        cartViewModel.removeCartResponse.removeObservers(viewLifecycleOwner)
        cartViewModel.removeCartResponse.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null) {
                cartViewModel.onFetchCartInfo(sharedPref.getUserId() ?: "")
                //Toast.makeText(context, "$deletedProductName successfully deleted from your cart!", Toast.LENGTH_SHORT).show()

                dispatchLoading()
            } else if (it.status == Status.ERROR) {
                dispatchLoading()
            } else if (it.status == Status.LOADING) {
                showLoading()
            }
        })
    }

}
