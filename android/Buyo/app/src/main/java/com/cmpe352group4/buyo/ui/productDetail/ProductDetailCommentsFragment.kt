package com.cmpe352group4.buyo.ui.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment

class ProductDetailCommentsFragment : BaseFragment() {
    companion object {
        fun newInstance() = ProductDetailCommentsFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_product_detail_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}