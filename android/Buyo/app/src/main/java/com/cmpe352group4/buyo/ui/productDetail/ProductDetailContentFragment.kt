package com.cmpe352group4.buyo.ui.productDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import kotlinx.android.synthetic.main.fragment_product_detail_comments.*
import kotlinx.android.synthetic.main.fragment_product_detail_content.*

class ProductDetailContentFragment : BaseFragment() {
    companion object {
        private const val PRODUCT_ID = "product_id"
        fun newInstance(productID: Int) = ProductDetailContentFragment().apply {
            arguments = Bundle().apply {
                putInt(PRODUCT_ID, productID)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_product_detail_content, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var productId = arguments?.getInt(PRODUCT_ID) ?: -1

        // Request Backend and fill the xml.
        // TODO

        //tvProductDetailName.text = productId.toString()

        btnProductDetailCart.setOnClickListener {
            Toast.makeText(context, "Added to your cart!", Toast.LENGTH_LONG).show()
        }
        btnProductDetailComments.setOnClickListener {
            navigationManager?.onReplace(
                ProductDetailCommentsFragment.newInstance(),
                TransactionType.Replace, true
            )
        }
        btnProductDetailBack.setOnClickListener {
            //TODO
        }
    }
}