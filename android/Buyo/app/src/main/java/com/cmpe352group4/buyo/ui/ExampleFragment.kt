package com.cmpe352group4.buyo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import kotlinx.android.synthetic.main.fragment_example.*

class ExampleFragment : BaseFragment() {

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
        btnProductDetail.setOnClickListener {
            // TODO
        }
    }

}