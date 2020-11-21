package com.cmpe352group4.buyo.ui

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
import com.cmpe352group4.buyo.ui.productList.ProductListFragment
import com.cmpe352group4.buyo.viewmodel.ExampleViewModel
import kotlinx.android.synthetic.main.fragment_example.*
import javax.inject.Inject

class ExampleFragment : BaseFragment() {

    // TODO DELETE THIS FRAGMENT

    // Following two variables are also needed for end point implementation in Fragment

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val exampleViewModel: ExampleViewModel by viewModels {
        viewModelFactory
    }


    companion object {
        fun newInstance() = ExampleFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextFragment.setOnClickListener {
            navigationManager?.onReplace(
                EmptyFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        // Example end point implementation in fragment
//        exampleViewModel.onFetchProductById(productId)
//
//        exampleViewModel.productDetail.observe(viewLifecycleOwner, Observer {
//            if (it.status == Status.SUCCESS && it.data != null) {
//
//                product_name.text = it.data?.name
//                product_price.text = it.data?.price
//
//                dispatchLoading()
//            } else if (it.status == Status.ERROR) {
//                dispatchLoading()
//            } else if (it.status == Status.LOADING) {
//                showLoading()
//            }
//        })


        btnHomePage.setOnClickListener {
            navigationManager?.onReplace(
                HomepageFragment.newInstance(),
                TransactionType.Replace, true
            )
        }
    }
}