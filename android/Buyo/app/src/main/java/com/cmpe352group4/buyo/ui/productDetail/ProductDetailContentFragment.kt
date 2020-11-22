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
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import kotlinx.android.synthetic.main.fragment_product_detail_comments.*
import kotlinx.android.synthetic.main.fragment_product_detail_content.*
import javax.inject.Inject

class ProductDetailContentFragment : BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

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
            if(sharedPref.getUserId().isNullOrEmpty()){
                Toast.makeText(context, "You need to login first", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "Added to your cart!", Toast.LENGTH_LONG).show()
            }
        }
        btnProductDetailComments.setOnClickListener {
            navigationManager?.onReplace(
                ProductDetailCommentsFragment.newInstance(),
                TransactionType.Replace, true
            )
        }
        btnProductDetailBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}