package com.cmpe352group4.buyo.ui.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.viewmodel.ProductViewModel
import com.cmpe352group4.buyo.vo.Comment
import kotlinx.android.synthetic.main.fragment_product_detail_comments.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import javax.inject.Inject

class ProductDetailCommentsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val productViewModel: ProductViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        private const val PRODUCT_ID = "product_id"
        fun newInstance(productID: String) = ProductDetailCommentsFragment().apply {
            arguments = Bundle().apply {
                putString(PRODUCT_ID, productID)
            }
        }
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

        var comments = mutableListOf<Comment>(
            Comment(user = getString(R.string.dummyCustomerName), text = getString(R.string.dummyComment), rating= "2.5"),
            Comment(user = getString(R.string.dummyCustomerName), text = getString(R.string.dummyComment), rating= "2.5"),
            Comment(user = getString(R.string.dummyCustomerName), text = getString(R.string.dummyComment), rating= "2.5"),
            Comment(user = getString(R.string.dummyCustomerName), text = getString(R.string.dummyComment), rating= "2.5"),
            Comment(user = getString(R.string.dummyCustomerName), text = getString(R.string.dummyComment), rating= "2.5")
        )


        val decorator = DividerItemDecoration(rv_ProductComments.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rv_ProductComments.context, R.drawable.divider_drawable)!!)
        rv_ProductComments.addItemDecoration(decorator)

        rv_ProductComments.adapter = ProductCommentsAdapter(comments)

        rv_ProductComments.layoutManager = LinearLayoutManager(this.context)

    }
}