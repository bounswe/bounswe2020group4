package com.cmpe352group4.buyo.ui.productDetail

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
import com.cmpe352group4.buyo.viewmodel.ProductViewModel
import com.cmpe352group4.buyo.vo.Comment
import com.cmpe352group4.buyo.vo.Product
import com.cmpe352group4.buyo.vo.ReportRequest
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_report_comment.*
import javax.inject.Inject

class ProductCommentReportFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val productViewModel: ProductViewModel by viewModels {
        viewModelFactory
    }

    val gson = Gson()

    companion object {
        private const val COMMENT = "comment_obj"
        private const val PRODUCT = "product_obj"
        fun newInstance(comment : String, product : String) = ProductCommentReportFragment().apply {
            arguments = Bundle().apply {
                putString(COMMENT, comment)
                putString(PRODUCT, product)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_report_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val comment_str = arguments?.getString(COMMENT) ?: ""

        val product_str = arguments?.getString(PRODUCT) ?: ""

        if (comment_str != "") {
            val comment = gson.fromJson(comment_str, Comment::class.java)

            tv_productCommentsReport_user.text = comment.owner.username

            tv_productCommentsReport_rating.text = comment.rating

            tv_productCommentsReport_text.text = comment.text


            btn_productCommentsReport_send.setOnClickListener {
                val message = et_productCommentsReport_reason.text.toString()
                val comment_id = comment.id

                productViewModel.onReportComment(ReportRequest(id = comment_id, message = message))
                productViewModel.reportComment.observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.SUCCESS && it.data != null){

                        val myToast = Toast.makeText(
                            context,
                            "Your report has been sent!",
                            Toast.LENGTH_SHORT
                        )
                        myToast.setGravity(Gravity.BOTTOM, 0, 200)
                        myToast.show()

                        activity?.onBackPressed()

                        dispatchLoading()
                    } else if (it.status == Status.ERROR){
                        dispatchLoading()
                    }else if (it.status == Status.LOADING){
                        showLoading()
                    }

                })


            }

        }
        else if (product_str !=  ""){
            val product = gson.fromJson(product_str, Product::class.java)

            tv_productCommentsReport_user.text = product.name
            tv_productCommentsReport_rating.text = product.rating.toString()
            tv_productCommentsReport_text.text = product.description

            et_productCommentsReport_reason.hint = "Please type down what is wrong with this product."

            btn_productCommentsReport_send.setOnClickListener {
                val message = et_productCommentsReport_reason.text.toString()
                val product_id = product.id

                productViewModel.onReportProduct(ReportRequest(id = product_id, message = message))
                productViewModel.reportProduct.observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.SUCCESS && it.data != null){

                        val myToast = Toast.makeText(
                            context,
                            "Your report has been sent!",
                            Toast.LENGTH_SHORT
                        )
                        myToast.setGravity(Gravity.BOTTOM, 0, 200)
                        myToast.show()

                        activity?.onBackPressed()

                        dispatchLoading()
                    } else if (it.status == Status.ERROR){
                        dispatchLoading()
                    }else if (it.status == Status.LOADING){
                        showLoading()
                    }

                })


            }

        }


        btn_productCommentsReport.setOnClickListener {
            activity?.onBackPressed()
        }


    }


}