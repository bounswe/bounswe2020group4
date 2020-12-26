package com.cmpe352group4.buyo.ui.cart

import android.os.Bundle
import android.util.Log
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
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.viewmodel.CartViewModel
import com.cmpe352group4.buyo.vo.*
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
    var finalCartProducts = mutableListOf<CartProduct>()
    val dummyCartProduct = mutableListOf(
        CartProduct(name = "Erkek Bebek Bugs Bunny Desenli Takim 2'li",
            id = "1",
            imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4692240/l_20202-0weg94z1-g4y_a.jpg",
            rating = 2.15,
            price = 64.99,
            originalPrice = 74.99,
            brand = "Watsons",
            vendor = Vendor(id = "12", name = "Pablos", rating = 2.43),
            productInfo = listOf(
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "red"), Attribute(att_name = "size", att_value = "L")
                ), stockValue = 2),
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "blue"), Attribute(att_name = "size", att_value = "M")
                ), stockValue = 4)
            )
        ),
        CartProduct(name = "Slim Fit Jogger Esofman Alti",
            id = "2",
            imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/productimages/20192/1/3891903/l_20192-9wr187z8-mgl_a.jpg",
            rating = 1.01,
            price = 35.00,
            originalPrice = 70.00,
            brand = "Koton",
            vendor = Vendor(id = "12", name = "AyseTeyze", rating = 3.21),
            productInfo = listOf(
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "red"), Attribute(att_name = "size", att_value = "L")
                ), stockValue = 1)
            )
        )
    )

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
            clNonEmptyCart.visible = true
            tv_product_price_dollar.text = "144.99 $"
            tv_discount_dollar.text = "45 $"
            tv_final_price.text = "Total: 119.99 $"

            dummyCartProduct.forEach { product ->
                if(product.productInfo.size > 1 ){
                    product.productInfo.forEach {
                        var newProduct = product.copy(productInfo = listOf(it))
                        finalCartProducts.add(newProduct)
                    }
                }else{
                    finalCartProducts.add(product)
                }
            }
            Log.i("cart", finalCartProducts.toString())
            cartAdapter.submitList(finalCartProducts)
        }

        initializeAdapter()
        setListeners()
        observeData()


    }

    override fun onResume() {
        super.onResume()
        if(checkLoginState()){
            //cartViewModel.onFetchCartInfo(sharedPref.getUserId()?.toInt() ?: -1)
            clNonEmptyCart.visible = true
            tv_product_price_dollar.text = "144.99 $"
            tv_discount_dollar.text = "45 $"
            tv_final_price.text = "Total: 119.99 $"

            dummyCartProduct.forEach { product ->
                if(product.productInfo.size > 1 ){
                    product.productInfo.forEach {
                        var newProduct = product.copy(productInfo = listOf(it))
                        finalCartProducts.add(newProduct)
                    }
                }else{
                    finalCartProducts.add(product)
                }
            }
            Log.i("cart", finalCartProducts.toString())
            cartAdapter.submitList(finalCartProducts)
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
            navigationManager?.onReplace(
                CheckoutPageFragment.newInstance(),
                TransactionType.Replace, true
            )
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


                    it.data.products?.forEach { product ->
                        if(product.productInfo.size > 1 ){
                            product.productInfo.forEach {
                                var newProduct = product.copy(productInfo = listOf(it))
                                finalCartProducts.add(newProduct)
                            }
                        }else{
                            finalCartProducts.add(product)
                        }
                    }

                    tv_product_price_dollar.text = (it.data.totalOriginalPrice.toString() + "$")
                    tv_discount_dollar.text = ((it.data.totalPrice?.minus(it.data.totalOriginalPrice)).toString() + "$")
                    tv_final_price.text = (it.data.totalPrice.toString() + "$")

                    cartAdapter.submitList(finalCartProducts)
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
