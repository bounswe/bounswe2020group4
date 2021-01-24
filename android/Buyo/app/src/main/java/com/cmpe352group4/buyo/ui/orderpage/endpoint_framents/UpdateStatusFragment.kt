package com.cmpe352group4.buyo.ui.orderpage.endpoint_framents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.orderpage.OrderPageFragment
import com.cmpe352group4.buyo.ui.orderpage.OrderPageFragmentVendor
import com.cmpe352group4.buyo.viewmodel.OrderViewModel

import javax.inject.Inject


class UpdateStatusFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPref

    private val updateStatusViewModel: OrderViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        private const val USER_ID = "user_id"
        private const val ORDER_ID = "order_id"
        private const val ORDERED_PRODUCT_ID = "ordered_product_id"
        private const val USER_TYPE = "user_type"
        private const val NEW_STATUS = "new_status"
        fun newInstance(userId: String, orderId: String, orderedProductId: String, userType: String, newStatus: String)
                = UpdateStatusFragment().apply {
            arguments = Bundle().apply {
                putString(USER_ID, userId)
                putString(ORDER_ID, orderId)
                putString(ORDERED_PRODUCT_ID, orderedProductId)
                putString(USER_TYPE, userType)
                putString(NEW_STATUS, newStatus)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_update_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userId = arguments?.getString(USER_ID)!!
        var orderedProductId = arguments?.getString(ORDERED_PRODUCT_ID)!!
        var orderId = arguments?.getString(ORDER_ID)!!
        var userType = arguments?.getString(USER_TYPE)!!
        var newStatus = arguments?.getString(NEW_STATUS)!!

        observe()
        updateStatusViewModel.onUpdateStatus(userId, orderId, orderedProductId, userType, newStatus)

    }

    private fun observe () {
        updateStatusViewModel.updateStatus.observe(viewLifecycleOwner, Observer {

            if (it.status == Status.SUCCESS && it.data != null) {
                if (arguments?.getString(USER_TYPE)!! == "vendor") {
                    navigationManager?.onReplace(
                        OrderPageFragmentVendor.newInstance(),
                        TransactionType.Replace, false
                    )
                }
                else if (arguments?.getString(USER_TYPE)!! == "customer"){
                    navigationManager?.onReplace(
                        OrderPageFragment.newInstance(),
                        TransactionType.Replace, false
                    )
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