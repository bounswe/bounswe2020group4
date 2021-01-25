package com.cmpe352group4.buyo.ui.notification

import com.cmpe352group4.buyo.ui.orderpage.OrderAdapter

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
import com.cmpe352group4.buyo.ui.orderpage.OrderPageFragmentVendor
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.viewmodel.NotificationViewModel
import com.cmpe352group4.buyo.vo.*
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_order_page.*
import javax.inject.Inject


class NotificationFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPref


    private val notificationViewModel: NotificationViewModel by viewModels {
        viewModelFactory
    }


    companion object {
        fun newInstance() = NotificationFragment()
    }

    private val notificationAdapter by lazy {
        NotificationAdapter(mutableListOf()
        ) { notification:NotificationRV ->
            if (notification.targetProductID != null) {
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(notification.targetProductID!!),
                    TransactionType.Replace, true
                )
            } else if (sharedPref.getUserType() == "vendor") {
                navigationManager?.onReplace(
                    OrderPageFragmentVendor.newInstance(),
                    TransactionType.Replace, true
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_notifications.adapter = notificationAdapter
        rv_notifications.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

        backButtonListener()
        //dummyFillRV()

        observeNotification()
        if (!sharedPref.getUserId().isNullOrEmpty()) {
            notificationViewModel.onFetchNotifications(sharedPref.getUserId()!!, sharedPref.getUserType()!!)
        }

    }

    private fun backButtonListener() {
        notification_back_button.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!sharedPref.getUserId().isNullOrEmpty()) {
            notificationViewModel.onFetchNotifications(sharedPref.getUserId()!!, sharedPref.getUserType()!!)
        }

    }


    private fun dummyFillRV(){
        val list = mutableListOf<NotificationRV>(
            NotificationRV("Summary 1", "Cancel Order", "2020-12-28T08:08:37.507Z", null),
            NotificationRV("Summary 2", "Cancel Order", "2020-12-28T08:08:38.507Z", null)
        )


        notificationAdapter.submitList(list)
    }


    private fun observeNotification () {
        notificationViewModel.notifications.observe(viewLifecycleOwner, Observer {

            if (it.status == Status.SUCCESS && it.data != null) {
                val list = mutableListOf<NotificationRV>()
                for (notification_item in it.data.notifications.items) {
                    if (notification_item.name == "Discount") {
                        val itemRV = NotificationRV(notification_item.summary, notification_item.name,
                            notification_item.startTime, notification_item.target)
                        list.add(itemRV)
                    } else {
                        val itemRV = NotificationRV(notification_item.summary, notification_item.name,
                            notification_item.startTime, null)
                        list.add(itemRV)
                    }
                }
                notificationAdapter.submitList(list)
                dispatchLoading()
            } else if (it.status == Status.ERROR) {
                dispatchLoading()
            } else if (it.status == Status.LOADING) {
                showLoading()
            }
        })
    }

}