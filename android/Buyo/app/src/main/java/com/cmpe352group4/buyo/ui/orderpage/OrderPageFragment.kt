package com.cmpe352group4.buyo.ui.orderpage

import android.os.Bundle
import android.util.Log
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
import com.cmpe352group4.buyo.ui.orderpage.endpoint_framents.UpdateStatusFragment
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.viewmodel.OrderViewModel
import com.cmpe352group4.buyo.vo.*
import kotlinx.android.synthetic.main.fragment_order_page.*
import javax.inject.Inject

// data -> MapOf<String, Order>,  string is the order no

class OrderPageFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPref

    private val ordersViewModelCustomer: OrderViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance() = OrderPageFragment()
    }

    private val orderAdapter by lazy {
        OrderAdapter(mutableListOf(),
            { order ->
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(order.productId),
                    TransactionType.Replace, true
                )
            },
            { order -> firstButtonOrderItemListener(order) },
            { order -> secondButtonOrderItemListener(order) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_order_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_paid_items.adapter = orderAdapter
        rv_paid_items.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

        backButtonListener()
        observeOrderData()
        if (!sharedPref.getUserId().isNullOrEmpty()) {
            ordersViewModelCustomer.onFetchOrders(sharedPref.getUserId()!!)
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
            ordersViewModelCustomer.onFetchOrders(sharedPref.getUserId()!!)
        }
    }

    private fun firstButtonOrderItemListener(order: OrderProductRV) {
        // TODO message vendor
    }

    private fun secondButtonOrderItemListener(order: OrderProductRV) {
        if(order.status=="Pending"){
            // Cancel order
            navigationManager?.onReplace(
                UpdateStatusFragment.newInstance(sharedPref.getUserId()!!, order.orderID,
                    order.orderedProductId, "customer", "Cancelled"),
                TransactionType.Replace, false
            )
        }
        else if(order.status.startsWith("Delivered") || order.status=="Returned") {
            // Add comment
            navigationManager?.onReplace(
                ProductDetailContentFragment.newInstance(order.productId),
                TransactionType.Replace, true
            )
        }
    }

    private fun observeOrderData () {
        ordersViewModelCustomer.orderMap.observe(viewLifecycleOwner, Observer {

            if (it.status == Status.SUCCESS && it.data != null) {
                val ordersListRV = mutableListOf<OrderProductRV>()
                for ((order_id, order) in it.data) {
                    for (product in order.products) {
                        val orderRV =
                            OrderProductRV(order_id, order.address, order.date, product.productId,
                                product.name, product.imageUrl, product.price, product.vendor,
                                product.quantity, product.attributes, product.status, product.orderedProductId)
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