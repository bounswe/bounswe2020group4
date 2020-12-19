package com.cmpe352group4.buyo.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
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
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.viewmodel.CartViewModel
import com.cmpe352group4.buyo.vo.CartProduct
import com.cmpe352group4.buyo.widgets.navigation_bar.NavigationBar
import kotlinx.android.synthetic.main.fragment_cart.*
import javax.inject.Inject

class CartPageFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val cartViewModel: CartViewModel by activityViewModels {
        viewModelFactory
    }

    @Inject
    lateinit var sharedPref: SharedPref


    companion object {
        fun newInstance() = CartPageFragment()
    }

    private val cartAdapter by lazy {
        CartAdapter(mutableListOf(),
            { modal ->
                // Delete item from cart
            }
            )
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

        if(checkLoginState()){
            //cartViewModel.onFetchCartInfo(sharedPref.getUserId()?.toInt() ?: -1)
            clEmptyCart.visible = true
        }

        initializeAdapter()
        setListeners()
        observeData()


    }

    override fun onResume() {
        super.onResume()
        if(checkLoginState()){
            //cartViewModel.onFetchCartInfo(sharedPref.getUserId()?.toInt() ?: -1)
            clEmptyCart.visible = true
        }
    }

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

        loginButton.setOnClickListener {
            (activity as MainActivity).changeActiveTab(NavigationBar.PROFILE_INDEX)
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
            // Go to checkout page
        }
    }

    private fun observeData() {
        cartViewModel.cartInfo.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null) {
                if(it.data.products.isNullOrEmpty()){
                    clEmptyCart.visible = true
                    clNonEmptyCart.visible = false
                }else{
                    clEmptyCart.visible = false
                    clNonEmptyCart.visible = true

                    tv_product_price_dollar.text = (it.data.totalOriginalPrice.toString() + "$")
                    tv_discount_dollar.text = ((it.data.totalPrice?.minus(it.data.totalOriginalPrice)).toString() + "$")
                    tv_final_price.text = (it.data.totalPrice.toString() + "$")

                    cartAdapter.submitList(it.data.products as MutableList<CartProduct>)
                }

                dispatchLoading()
            } else if (it.status == Status.ERROR) {
                dispatchLoading()
            } else if (it.status == Status.LOADING) {
                showLoading()
            }
        })
    }

}