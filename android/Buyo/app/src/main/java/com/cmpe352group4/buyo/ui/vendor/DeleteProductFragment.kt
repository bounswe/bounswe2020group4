package com.cmpe352group4.buyo.ui.vendor

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
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.homepage.HomepageFragment
import com.cmpe352group4.buyo.viewmodel.VendorViewModel
import com.cmpe352group4.buyo.vo.DeleteProductEndpoint
import kotlinx.android.synthetic.main.fragment_delete_product.*
import javax.inject.Inject

class DeleteProductFragment: BaseFragment() {
    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val vendorViewModel : VendorViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        private const val PRODUCTID = "product_id"
        private const val PRODUCTNAME = "product_name"
        fun newInstance(id : String, name : String) = DeleteProductFragment().apply {
            arguments = Bundle().apply {
                putString(PRODUCTID, id)
                putString(PRODUCTNAME, name)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_delete_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var product_id = arguments?.getString(DeleteProductFragment.PRODUCTID) ?: ""
        var product_name = arguments?.getString(DeleteProductFragment.PRODUCTNAME) ?: ""

        tv_deleteProduct_text.text = "Are you sure you want to delete " + product_name + "?"

        btn_deleteProduct_back.setOnClickListener {
            activity?.onBackPressed()
        }

        btn_deleteProduct_confirm.setOnClickListener {
            vendorViewModel.onDeleteProduct(DeleteProductEndpoint(vendorID = sharedPref.getUserId().toString(), productID = product_id))

            vendorViewModel.deleteProduct.observe(viewLifecycleOwner, Observer {
                if (it.status == Status.SUCCESS && it.data != null) {
                    val myToast = Toast.makeText(
                        context,
                        "You have successfully deleted " + product_name + "!",
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                    dispatchLoading()

                    navigationManager?.onReplace(
                        VendorProductListFragment.newInstance(),
                        TransactionType.Replace, false
                    )

                } else if (it.status == Status.ERROR) {
                    val myToast = Toast.makeText(
                        context,
                        "You have successfully deleted " + product_name + "!",
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                    dispatchLoading()

                    navigationManager?.onReplace(
                        VendorProductListFragment.newInstance(),
                        TransactionType.Replace, false
                    )
                } else if (it.status == Status.LOADING) {
                    showLoading()
                }
            })
        }


    }
}