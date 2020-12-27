package com.cmpe352group4.buyo.ui.productDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.viewmodel.ProductViewModel
import com.cmpe352group4.buyo.vo.Comment
import com.cmpe352group4.buyo.vo.CommentOwner
import com.cmpe352group4.buyo.vo.Product
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

        var productId = arguments?.getString(ProductDetailCommentsFragment.PRODUCT_ID) ?: ""

        var comments : List<Comment>? = null

        productViewModel.onFetchProductById(productId)
        productViewModel.productDetail.observe(viewLifecycleOwner, Observer {
            Log.d("LikedProdStParse", "$it.status")
            if (it.status == Status.SUCCESS && it.data != null){

                comments = it.data.result.comments

                dispatchLoading()
            } else if (it.status == Status.ERROR){
                dispatchLoading()
            }else if (it.status == Status.LOADING){
                showLoading()
            }

        })

        if (comments == null  || comments!!.size == 0){
            comments = mutableListOf(
                Comment(id = "-1", text = "Buy this product immediately and make the first comment !", rating= ":(", owner = CommentOwner(username = "No Comments Yet", email = "dummy@gmail.com" ))
            )
        }


        /*comments = mutableListOf(
            Comment(id = "1234", text = getString(R.string.dummyComment), rating= "2.5", owner = CommentOwner(username = getString(R.string.dummyCustomerName), email = "dummy@gmail.com" )),
            Comment(id = "1234", text = getString(R.string.dummyComment), rating= "2.5", owner = CommentOwner(username = getString(R.string.dummyCustomerName), email = "dummy@gmail.com" )),
            Comment(id = "1234", text = getString(R.string.dummyComment), rating= "2.5", owner = CommentOwner(username = getString(R.string.dummyCustomerName), email = "dummy@gmail.com" )),
            Comment(id = "1234", text = getString(R.string.dummyComment), rating= "2.5", owner = CommentOwner(username = getString(R.string.dummyCustomerName), email = "dummy@gmail.com" )),
            Comment(id = "1234", text = getString(R.string.dummyComment), rating= "2.5", owner = CommentOwner(username = getString(R.string.dummyCustomerName), email = "dummy@gmail.com" ))
        )*/


        val decorator = DividerItemDecoration(rv_ProductComments.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rv_ProductComments.context, R.drawable.divider_drawable)!!)
        rv_ProductComments.addItemDecoration(decorator)

        rv_ProductComments.adapter = ProductCommentsAdapter(comments as MutableList<Comment>)

        rv_ProductComments.layoutManager = LinearLayoutManager(this.context)

    }
}