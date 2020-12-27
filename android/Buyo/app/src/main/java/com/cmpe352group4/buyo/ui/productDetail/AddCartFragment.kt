package com.cmpe352group4.buyo.ui.productDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.cmpe352group4.buyo.viewmodel.CartViewModel
import com.cmpe352group4.buyo.vo.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_cart.*
import javax.inject.Inject

class AddCartFragment : BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref


    companion object {
        private const val PRODUCT = "product_obj"
        fun newInstance(product: Product?) = AddCartFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PRODUCT, product)
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val cartViewModel: CartViewModel by viewModels {
        viewModelFactory
    }
    val gson = Gson()

    private var order_details: MutableMap<String, String> = mutableMapOf()

    private val AddCartAdapter by lazy {
        AddCartAdapter(
            mutableListOf(), sharedPref
        ) { featureName, featureValue ->
            order_details[featureName] = featureValue
            Log.v("AddCartFragment", order_details.toString())
            updateMax()
        }
    }

    var productInfos : List<ProductInfo>? = null

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


        val product: Product = arguments?.getSerializable(PRODUCT) as Product

        productInfos = product.productInfos

        Log.v("AddCartFragment", product.toString())

        AddCartAdapter.submitList(product.filterCriterias as MutableList<FilterCriterias>)

        Log.v("AddCartFragment", order_details.toString())

        btn_addCart.setOnClickListener {

            if(et_addCartAmount.text.toString() == ""){
                Toast.makeText(context, "Please enter a number as amount!", Toast.LENGTH_SHORT).show()
            }else{
                val current_amount = et_addCartAmount.text.toString().toInt()

                if (current_amount > updateMax()){
                    Toast.makeText(context, "Sorry, we don't have that much product :(", Toast.LENGTH_SHORT).show()
                }else{
                    val att_list = mutableListOf<Attribute>()

                    for (att in order_details) {
                        att_list.add(Attribute(name = att.key, value = att.value))
                    }
                    Log.v("AddCartFragment", att_list.toString())
                    Log.v("AddCartFragment", current_amount.toString())
                    Log.v("AddCartFragmentUser", sharedPref.getUserId() ?: "")
                    Log.v("AddCartFragmentProd", product.id)
                    Log.v("AddCartFragmentJSON", gson.toJson(AddCartInfo(att_list, current_amount)))


                    cartViewModel.onAdd(AddCartRequest(customerId = sharedPref.getUserId() ?: "", productId = product.id, productInfo = gson.toJson(AddCartInfo(att_list, current_amount))))

                    cartViewModel.addCartResponse.observe(viewLifecycleOwner, Observer {
                        if (it.status == Status.SUCCESS && it.data != null) {

                            Toast.makeText(context, "${product.name} successfully added to your cart!", Toast.LENGTH_SHORT).show()

                            dispatchLoading()
                        } else if (it.status == Status.ERROR) {
                            dispatchLoading()
                        } else if (it.status == Status.LOADING) {
                            showLoading()
                        }
                    })

                }
            }



        }

    }

    fun updateMax(): Int {

        var att_list = mutableListOf<Attribute>()

        for (att in order_details) {
            att_list.add(Attribute(name = att.key, value = att.value))
        }

        var order_names = att_list.map { it.name }.sorted()
        var order_values = att_list.map { it.value }.sorted()

        //Log.v("AddCartFragment", order_names.toString())
        //Log.v("AddCartFragment", order_values.toString())

        if (productInfos != null){
            for (prod_info in productInfos!!){
                val current_prod_info_atts= prod_info.attributes

                var current_names = current_prod_info_atts.map { it.name }.sorted()
                var current_values = current_prod_info_atts.map { it.value }.sorted()

                if (current_names == order_names &&  current_values ==  order_values){
                    //Log.v("AddCartFragment", "There is a match: ${prod_info.stockValue}")
                    tv_addCartMaxAmount.text = "Max: ${prod_info.stockValue}"
                    return prod_info.stockValue
                }

            }
        }
        return 0
    }

}