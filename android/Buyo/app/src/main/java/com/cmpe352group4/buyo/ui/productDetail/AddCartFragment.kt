package com.cmpe352group4.buyo.ui.productDetail

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.viewmodel.ProductViewModel
import com.cmpe352group4.buyo.vo.ParsedAttribute
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.fragment_add_cart.*
import kotlinx.android.synthetic.main.fragment_product_list_filter_sort.*
import javax.inject.Inject

class AddCartFragment: BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref


    companion object {
        private const val PRODUCT_ID = "product_id"
        fun newInstance(productID: String) = AddCartFragment().apply {
            arguments = Bundle().apply {
                putString(PRODUCT_ID, productID)
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val productViewModel: ProductViewModel by viewModels {
        viewModelFactory
    }

    private var order_details : MutableMap<String, String> = mutableMapOf()

    private val AddCartAdapter by lazy {
        AddCartAdapter(mutableListOf(), sharedPref
        ) { featureName, featureValue ->
            order_details[featureName] = featureValue
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_add_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var productId = arguments?.getString(AddCartFragment.PRODUCT_ID) ?: ""

        var product : Product? = null

        var parsed_attributes : MutableList<ParsedAttribute> = mutableListOf()

        productViewModel.onFetchProductById(productId)

        productViewModel.productDetail.observe(viewLifecycleOwner, Observer {
            Log.d("LikedProdStParse", "$it.status")
            if (it.status == Status.SUCCESS && it.data != null){

                product = it.data.result

                parsed_attributes = extract_features(product!!)

                dispatchLoading()
            } else if (it.status == Status.ERROR){
                dispatchLoading()
            }else if (it.status == Status.LOADING){
                showLoading()
            }

        })

        if(parsed_attributes.size != 0){
            AddCartAdapter.Attributes = parsed_attributes


            // RECYCLER VIEW

            val decorator = DividerItemDecoration(
                rv_addCartFilters.context,
                LinearLayoutManager.VERTICAL
            )
            decorator.setDrawable(
                ContextCompat.getDrawable(
                    rv_addCartFilters.context,
                    R.drawable.divider_drawable
                )!!
            )
            rv_addCartFilters.addItemDecoration(decorator)

            rv_addCartFilters.adapter = AddCartAdapter

            rv_addCartFilters.layoutManager = LinearLayoutManager(this.context)


            // AMOUNT

            var amount : Int

            sb_addCartAmountBar.max = 100


            sb_addCartAmountBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seek: SeekBar?, progress: Int, fromUser: Boolean) {
                    tv_addCartCurrentAmount.text = progress.toString()
                }

                override fun onStartTrackingTouch(seek: SeekBar?) {
                    if (seek != null) {
                        tv_addCartCurrentAmount.text = seek.progress.toString()
                    }
                }

                override fun onStopTrackingTouch(seek: SeekBar?) {
                    if (seek != null) {
                        tv_addCartCurrentAmount.text = seek.progress.toString()
                        amount = seek.progress
                    }
                }

            })


            // SEND Backend Request Here Using order_details and amount

            btn_addCart.setOnClickListener {

                if (order_details.size == rv_addCartFilters.adapter!!.itemCount){
                    // Send Request here
                    activity?.onBackPressed()

                    val myToast = Toast.makeText(
                        context,
                        "${productId} successfully added to your cart !",
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                }
            }
        }

    }

    fun extract_features(product: Product): MutableList<ParsedAttribute> {
        var dummy_parsed_attribute1 = ParsedAttribute(
            att_name = "RAM", att_value = listOf(
                "16gb",
                "8gb",
                "4gb"
            )
        )
        var dummy_parsed_attribute2 = ParsedAttribute(
            att_name = "COLOR", att_value = listOf(
                "RED",
                "GREEN",
                "BLUE"
            )
        )


        var dummy_attributes = mutableListOf<ParsedAttribute>(
            dummy_parsed_attribute1,
            dummy_parsed_attribute2
        )
        return dummy_attributes
    }
}