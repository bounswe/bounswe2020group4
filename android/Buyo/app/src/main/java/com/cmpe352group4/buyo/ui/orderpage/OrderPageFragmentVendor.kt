package com.cmpe352group4.buyo.ui.orderpage

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.EmptyFragment
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.viewmodel.OrderViewModel
import com.cmpe352group4.buyo.viewmodel.SearchViewModel
import com.cmpe352group4.buyo.vo.*
import kotlinx.android.synthetic.main.fragment_order_page.*
import kotlinx.android.synthetic.main.fragment_order_page.orderpage_back_button
import kotlinx.android.synthetic.main.fragment_order_page_vendor.*
import javax.inject.Inject


class OrderPageFragmentVendor : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPref

    private val ordersViewModel: OrderViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance() = OrderPageFragmentVendor()
    }

    private val orderAdapter by lazy {
        OrderAdapterVendor(mutableListOf(),
            { order ->
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(order.productId),
                    TransactionType.Replace, true
                )
            },
            { order ->
                val myToast = Toast.makeText(
                    context,
                    "Approve " + order.orderID,
                    Toast.LENGTH_SHORT
                )
                myToast.setGravity(Gravity.BOTTOM, 0, 200)
                myToast.show()
                navigationManager?.onReplace(
                    EmptyFragment.newInstance(),
                    TransactionType.Replace, true
                )
            },
            { order ->
                if (order.status != "Reject") {
                    val myToast = Toast.makeText(
                        context,
                        "Comment on " + order.name,
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                    navigationManager?.onReplace(
                        EmptyFragment.newInstance(),
                        TransactionType.Replace, true
                    )
                } else {
                    val myToast = Toast.makeText(
                        context,
                        "Cancel " + order.name,
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                    navigationManager?.onReplace(
                        EmptyFragment.newInstance(),
                        TransactionType.Replace, true
                    )
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_order_page_vendor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_order_items.adapter = orderAdapter
        rv_order_items.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

        backButtonListener()
        observeOrderData()
        if (!sharedPref.getUserId().isNullOrEmpty()) {
            ordersViewModel.onFetchOrders(sharedPref.getUserId()!!)
        }
    }

    private fun backButtonListener() {
        orderpage_back_button.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!sharedPref.getUserId().isNullOrEmpty()) {
            ordersViewModel.onFetchOrders(sharedPref.getUserId()!!)
        }
    }

    private fun observeOrderData () {
        ordersViewModel.orderMapVendor.observe(viewLifecycleOwner, Observer {

            if (it.status == Status.SUCCESS && it.data != null) {
                val ordersListRV = mutableListOf<OrderProductVendorRV>()
                for ((order_id, order) in it.data) {
                    for (product in order.products) {
                        val orderRV =
                            OrderProductVendorRV(order_id, product.customerId, order.address, order.date, product.productId,
                                product.name, product.imageUrl, product.price,
                                product.quantity, product.attributes, product.status)
                        ordersListRV.add(orderRV)
                    }
                }

                orderAdapter.submitList(ordersListRV)

                dispatchLoading()
            } else if (it.status == Status.ERROR) {
                dispatchLoading()
            } else if (it.status == Status.LOADING) {
                showLoading()
            }
        })
    }
}