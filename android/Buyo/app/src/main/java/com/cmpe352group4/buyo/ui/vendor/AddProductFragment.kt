package com.cmpe352group4.buyo.ui.vendor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.viewmodel.ProductViewModel
import com.cmpe352group4.buyo.viewmodel.VendorViewModel
import com.cmpe352group4.buyo.vo.FilterCriterias
import com.cmpe352group4.buyo.vo.ParsedAttribute
import com.cmpe352group4.buyo.vo.Product
import com.cmpe352group4.buyo.vo.Vendor
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_product_detail_content.*
import kotlinx.android.synthetic.main.fragment_vendor_add_product.*
import kotlinx.android.synthetic.main.fragment_vendor_add_stock_value.*
import java.io.IOException
import javax.inject.Inject

class AddProductFragment : BaseFragment() {
    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val vendorViewModel: VendorViewModel by viewModels {
        viewModelFactory
    }

    private var imageData: ByteArray? = null

    companion object {
        private const val MODE = "add_or_edit_mode"
        private const val PRODUCT = "product_if_edit"
        private const val CATEGORY = "product_category"
        fun newInstance(mode : String, product : String, categories : String?) = AddProductFragment().apply {
            arguments = Bundle().apply {
                putString(MODE, mode)
                putString(PRODUCT, product)
                putString(CATEGORY, categories)
            }
        }
    }

    var attributes : MutableMap<String, List<String>> = mutableMapOf()

    private val addProductAdapter by lazy {
        AddProductAdapter(
            mutableListOf()
        ){
            attributes[it.att_name] = it.att_value
        }
    }


    val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_vendor_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recycler view

        val decorator = DividerItemDecoration(rv_vendorAddProductAttributes.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rv_vendorAddProductAttributes.context, R.drawable.divider_drawable)!!)
        rv_vendorAddProductAttributes.addItemDecoration(decorator)

        rv_vendorAddProductAttributes.adapter = addProductAdapter


        rv_vendorAddProductAttributes.layoutManager = LinearLayoutManager(this.context)

        val fragment_mode = arguments?.getString(MODE) ?: ""

        val category_path = arguments?.getString(CATEGORY) ?: ""

        // Create a new product object
        var current_product = Product(
            id = "000", // Cant
            category = category_path.split(","), // Done
            name = "ProductName", // Done
            imageUrl = "some_url", // Not yet
            rating = 0.0, // Cant
            price = 0.0, // Done
            originalPrice = 0.0, // Done
            filterCriterias = emptyList(), // Done
            comments = emptyList(), // Cant
            brand = "brand", // Done
            description = "description", // Done
            vendor = Vendor(id= sharedPref.getUserId() ?: "", rating = 0.0, name = "vendor_name"), // Not yet
            productInfos = mutableListOf(), // Next Fragment
            materials = null // Aborted
        )

        // NAME
        tiet_vendorAddProduct_Name.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                current_product.name = text.toString()
            }

            override fun afterTextChanged(editable: Editable?) {
                current_product.name = editable.toString()
            }

        })
        // DESCRIPTION
        tiet_vendorAddProduct_desc.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                current_product.description = text.toString()
            }

            override fun afterTextChanged(editable: Editable?) {
                current_product.description = editable.toString()
            }

        })

        // PRICE
        tiet_vendorAddProduct_Price.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    current_product.price = text.toString().toDouble()
                    //tiet_vendorAddProduct_oriPrice.setText(current_product.price.toString())
                }catch (e: Exception){
                }

            }

            override fun afterTextChanged(editable: Editable?) {
                try {
                    current_product.price = editable.toString().toDouble()
                    //tiet_vendorAddProduct_oriPrice.setText(current_product.price.toString())
                }catch (e : Exception){
                }

            }

        })

        // ORIGINAL PRICE
        tiet_vendorAddProduct_oriPrice.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    current_product.originalPrice = text.toString().toDouble()
                }catch (e: Exception){
                }

            }

            override fun afterTextChanged(editable: Editable?) {
                try {
                    current_product.originalPrice = editable.toString().toDouble()
                }catch (e : Exception){
                }

            }

        })

        // BRAND
        tiet_vendorAddProduct_Brand.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                current_product.brand = text.toString()
            }

            override fun afterTextChanged(editable: Editable?) {
                current_product.brand = editable.toString()
            }

        })


        if (fragment_mode == "add"){ // Add mode

            val category_path = arguments?.getString(CATEGORY) ?: ""

            Log.v("VendorAddProduct", "CategoryPath: $category_path")

            tv_vendorAddProductCategoryName.text = category_path.split(",").joinToString("->")
            var attNum : Int

            // Get total number of attributes
            tiet_vendorAddProduct_AttNum.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
                    Log.v("VendorAddProduct", "beforeTextChanged: $text")
                }

                override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.v("VendorAddProduct", "onTextChanged: $text")
                    val parsedList = mutableListOf<ParsedAttribute>()
                    try {
                        attNum = text.toString().toInt()

                        for (i in 1..attNum){
                            parsedList.add(ParsedAttribute(att_name = "", att_value = mutableListOf()))
                        }

                        addProductAdapter.submitList(parsedList)


                    }catch (e: Exception){

                    }
                }

                override fun afterTextChanged(editable: Editable?) {
                    Log.v("VendorAddProduct", "afterTextChanged: "+editable.toString())
                }

            })


        }
        else if (fragment_mode == "edit"){ // Edit mode

            val productJSON = arguments?.getString(PRODUCT) ?: ""

            val product = gson.fromJson(productJSON, Product::class.java)
            Log.v("EditProduct", product.toString())

            // Parse product
            tv_vendorAddProductCategoryName.text = product.category.joinToString("->")

            Glide.with(this)
                .load(product.imageUrl).centerCrop()
                .into(uploaded_image)

            tiet_vendorAddProduct_Name.setText(product.name)

            tiet_vendorAddProduct_desc.setText(product.description)

            tiet_vendorAddProduct_Price.setText(product.price.toString())

            tiet_vendorAddProduct_oriPrice.setText(product.originalPrice.toString())

            tiet_vendorAddProduct_Brand.setText(product.brand)

            tiet_vendorAddProduct_AttNum.setText(product.filterCriterias?.size.toString())

            var default_atts = product.filterCriterias?.size

            var parsedList = mutableListOf<ParsedAttribute>()

            Log.v("EditProduct", product.filterCriterias.toString())

            // Parse the current attributes

            for (criteria in product.filterCriterias!!){
                parsedList.add(ParsedAttribute(att_name = criteria.name, att_value = criteria.possibleValues))
            }

            addProductAdapter.submitList(parsedList)

            tiet_vendorAddProduct_AttNum.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {

                    try {
                        var attNum = text.toString().toInt()

                        if (default_atts != null) {
                            if (default_atts < attNum) { // If num of attributes increases
                                Log.v("AddProductxDefaultAtts", default_atts.toString())
                                Log.v("AddProductxNewAtts", attNum.toString())
                                parsedList = mutableListOf<ParsedAttribute>()
                                for (criteria in product.filterCriterias!!){
                                    parsedList.add(ParsedAttribute(att_name = criteria.name, att_value = criteria.possibleValues))
                                }
                                var diff = attNum - default_atts

                                for (i in 1..diff) { // add empty attributes
                                    parsedList.add(
                                        ParsedAttribute(
                                            att_name = "",
                                            att_value = mutableListOf()
                                        )
                                    )
                                }
                                Log.v("AddProductxParsedList", parsedList.toString())
                                addProductAdapter.submitList(parsedList)
                            }
                            else if (default_atts > attNum){ // If number of attributes decreases
                                Log.v("AddProductxDefaultAtts", default_atts.toString())
                                Log.v("AddProductxNewAtts", attNum.toString())

                                var diff = default_atts - attNum

                                parsedList = parsedList.subList(0, attNum) // discard last n attributes

                                Log.v("AddProductxParsedList", parsedList.toString())
                                addProductAdapter.submitList(parsedList)
                            }
                            else if (default_atts ==  attNum){ // Attributes num does not change -> re-parse attributes
                                parsedList = mutableListOf<ParsedAttribute>()
                                for (criteria in product.filterCriterias!!){
                                    parsedList.add(ParsedAttribute(att_name = criteria.name, att_value = criteria.possibleValues))
                                }
                                addProductAdapter.submitList(parsedList)
                            }
                        }

                    }catch (e: Exception){

                    }


                }

                override fun afterTextChanged(editable: Editable?) {

                }

            })

            current_product = product

        }
        else{
            Log.e("VendorAddProduct", "UnknownMode")
        }

        btn_vendorAddProduct_Next.setOnClickListener {

            // If image daha is not null then upload it and write its url
            if (imageData != null){
                vendorViewModel.onUploadImage(image = imageData)

                vendorViewModel.imageUrl.observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.SUCCESS && it.data != null) {

                        current_product.imageUrl = it.data.urls[0]

                        Log.v("ImageURL", current_product.imageUrl)


                        dispatchLoading()
                    } else if (it.status == Status.ERROR) {
                        Log.v("ImageURL", "ERROR")
                        Log.v("ImageURL", it.message.toString())
                        dispatchLoading()
                    } else if (it.status == Status.LOADING) {
                        Log.v("ImageURL", "Loading")
                        showLoading()
                    }
                })

            }
            else{ // if no image provided then put BUYO logo as default

                if (fragment_mode == "add" && current_product.imageUrl == "some_url"){
                    current_product.imageUrl = "https://elasticbeanstalk-us-east-2-334058266782.s3.amazonaws.com/images/16115134390620"
                }


                Log.v("ImageURL","image data is null")

            }



            if (current_product.imageUrl == "some_url"){
                val myToast = Toast.makeText(
                    context,
                    "Please update an image!",
                    Toast.LENGTH_SHORT
                )
                myToast.setGravity(Gravity.BOTTOM, 0, 200)
                myToast.show()
            }else{ // If the url of the image is set

                var current_attributes : MutableMap<String, List<String>> = mutableMapOf()

                // get the entered attributes
                for ( (key, value) in attributes){
                    if (addProductAdapter.getList().contains(key)){
                        current_attributes[key] = value
                    }
                }

                attributes = current_attributes

                Log.v("AddProductxAttribute", attributes.toString())

                var all_options : MutableList<Set<String>> = mutableListOf()

                var att_names : MutableList<String> = mutableListOf()

                // change the filter criterias of the product

                var filter_criterias : MutableList<FilterCriterias> = mutableListOf()
                for ((att_name, att_list ) in attributes){
                    all_options.add(att_list.toSet())
                    att_names.add(att_name)
                    val original_name = att_name.replace("\\s".toRegex(), "").toLowerCase()
                    filter_criterias.add(FilterCriterias(name = original_name, displayName = att_name, possibleValues = att_list))
                }

                // FILTER CRITERIAS
                current_product.filterCriterias = filter_criterias

                // Create all the possible attribute combinations by taking cartesian product of them

                var combinations: Set<List<*>> = mutableSetOf()
                if (all_options.size >= 3){
                    combinations = cartesianProduct(all_options[0],all_options[1], *all_options.subList(2, all_options.size).toTypedArray())
                }
                else if (all_options.size == 2){
                    combinations = cartesianProduct(all_options[0],all_options[1])
                }
                else if (all_options.size == 1){
                    var one_att : MutableList<List<String>> = mutableListOf()
                    for ((_, att_list ) in attributes){
                        for (att in att_list){
                            one_att.add(listOf(att))
                        }
                    }
                    combinations = one_att.toSet()
                }

                var new_combinations = combinations.toList()

                if (current_product.name == "ProductName"
                    || current_product.price == 0.0
                    || current_product.originalPrice == 0.0
                    || current_product.description == "description"
                    || current_product.brand == "brand"
                    || (current_product.filterCriterias as MutableList<FilterCriterias>).size == 0){
                    val myToast = Toast.makeText(
                        context,
                        "Please fill all the fields!",
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                } else if (all_options.size == 0){
                    val myToast = Toast.makeText(
                        context,
                        "Please enter the empty attribute fields.",
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                }
                else { // If there is not any empty attribute then proceed
                    navigationManager?.onReplace(
                        AddStockValuesFragment.newInstance(gson.toJson(new_combinations), gson.toJson(current_product), att_names.joinToString("%"), mode = fragment_mode),
                        TransactionType.Replace, true
                    )
                }
            }



        }

        btn_vendorAddProduct_Back.setOnClickListener {
            activity?.onBackPressed()
        }

        btn_vendorAddProduct_Image.setOnClickListener {
            launchGallery()


        }

    }

    // Load image from gallery functions

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 999)
    }

    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = context?.contentResolver?.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 999) {
            val uri = data?.data
            if (uri != null) {
                uploaded_image.setImageURI(uri)
                createImageData(uri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // Create all the combinations of the attributes
    fun cartesianProduct(a: Set<*>, b: Set<*>, vararg sets: Set<*>): Set<List<*>> =
        (setOf(a, b).plus(sets))
            .fold(listOf(listOf<Any?>())) { acc, set ->
                acc.flatMap { list -> set.map { element -> list + element } }
            }
            .toSet()

}