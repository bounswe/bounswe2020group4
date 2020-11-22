package com.cmpe352group4.buyo.ui.productDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product_detail_comments.*
import kotlinx.android.synthetic.main.fragment_product_detail_content.*
import javax.inject.Inject

class ProductDetailContentFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val productViewModel: ProductViewModel by viewModels {
        viewModelFactory
    }


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


        // Backend request
        productViewModel.onFetchProductById(productId)
        productViewModel.productDetail.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null){
                tvProductDetailName.text = it.data.result.name
                tvProductDetailVendor.text = it.data.result.vendor.name
                tvProductDetailInfo.text = "PRODUCT INFO PRODUCT INFO PRODUCT INFO PRODUCT INFO "
                tvProductDetailPrice.text = it.data.result.price.toString() + " TL"
                rbProductDetailRating.rating = it.data.result.rating.toFloat()
                Glide.with(this)
                    .load(it.data.result.imageUrl).centerCrop()
                    .into(ivProductDetailImage)

                dispatchLoading()
            } else if (it.status == Status.ERROR){
                dispatchLoading()
            }else if (it.status == Status.LOADING){
                showLoading()
            }

        })

        tbProductDetailFav.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The toggle is enabled
            } else {
                // The toggle is disabled
            }
        }


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
            activity?.onBackPressed()
        }
    }
}