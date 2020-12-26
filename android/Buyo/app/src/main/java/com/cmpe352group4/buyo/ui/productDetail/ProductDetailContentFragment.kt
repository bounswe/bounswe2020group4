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
import com.bumptech.glide.Glide
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.viewmodel.ProductViewModel
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.viewmodel.WishListViewModel
import com.cmpe352group4.buyo.vo.LikeResponse
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.fragment_product_detail_content.*
import javax.inject.Inject

class ProductDetailContentFragment : BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val productViewModel: ProductViewModel by viewModels {
        viewModelFactory
    }


    private val wishListViewModel: WishListViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        private const val PRODUCT_ID = "product_id"
        fun newInstance(productID: String) = ProductDetailContentFragment().apply {
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

        return inflater.inflate(R.layout.fragment_product_detail_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var productId = arguments?.getString(PRODUCT_ID) ?: ""

        var WishListProducts: List<Product>?

        var prod_ids : List<String>? = null

        var product : Product? = null


        iv_ProductDetailFav.tag = R.drawable.ic_product_disliked


        // GET LIKED PRODUCTS IF A USER LOGGED IN

        if(sharedPref.getUserId().isNullOrEmpty()){
            Log.i("ProductResponse", "Guest User")
            prod_ids = emptyList()

        }else {
            wishListViewModel.onFetchWishListProducts(sharedPref.getUserId() ?: "")

            wishListViewModel.wishListProducts.observe(viewLifecycleOwner, Observer {
                Log.d("LikedProdStFetch", "$it.status")
                if (it.status == Status.SUCCESS && it.data != null) {

                    WishListProducts = it.data.products as MutableList<Product>

                    prod_ids = WishListProducts?.map{it.id}

                    Log.d("LikedProdFetch", "$prod_ids")

                    parse(prod_ids, product)

                    dispatchLoading()
                } else if (it.status == Status.ERROR) {
                    dispatchLoading()
                } else if (it.status == Status.LOADING) {
                    showLoading()
                }
            })
        }


        // GET PRODUCT RESULT

        productViewModel.onFetchProductById(productId)
        productViewModel.productDetail.observe(viewLifecycleOwner, Observer {
            Log.d("LikedProdStParse", "$it.status")
            if (it.status == Status.SUCCESS && it.data != null){

                product = it.data.result

                parse(prod_ids, product)
                dispatchLoading()
            } else if (it.status == Status.ERROR){
                dispatchLoading()
            }else if (it.status == Status.LOADING){
                showLoading()
            }

        })


        //LIKING / UNLIKING

        iv_ProductDetailFav.setOnClickListener {
            if (sharedPref.getUserId().isNullOrEmpty()) {
                val myToast = Toast.makeText(
                    context,
                    "You need to Login first!",
                    Toast.LENGTH_SHORT
                )
                myToast.setGravity(Gravity.BOTTOM, 0, 200)
                myToast.show()
            } else {
                if (iv_ProductDetailFav.tag == R.drawable.ic_product_disliked) {
                    wishListViewModel.onPostWhislistUpdate(LikeResponse(sharedPref.getUserId() ?: "", productId))

                    wishListViewModel.statusUnlike.observe(viewLifecycleOwner, Observer {
                        if (it.status == Status.SUCCESS && it.data != null) {

                            iv_ProductDetailFav.setImageResource(R.drawable.ic_product_liked)
                            iv_ProductDetailFav.tag = R.drawable.ic_product_liked

                            dispatchLoading()
                        } else if (it.status == Status.ERROR) {
                            dispatchLoading()
                        } else if (it.status == Status.LOADING) {
                            showLoading()
                        }
                    })

                } else if (iv_ProductDetailFav.tag == R.drawable.ic_product_liked) {


                    wishListViewModel.onPostWhislistUpdate(LikeResponse(sharedPref.getUserId() ?: "", productId))

                    wishListViewModel.statusUnlike.observe(viewLifecycleOwner, Observer {
                        if (it.status == Status.SUCCESS && it.data != null) {

                            iv_ProductDetailFav.setImageResource(R.drawable.ic_product_disliked)
                            iv_ProductDetailFav.tag = R.drawable.ic_product_disliked

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

        // ADD CART AND OTHER BUTTONS

        btnProductDetailCart.setOnClickListener {
            if(sharedPref.getUserId().isNullOrEmpty()){
                Toast.makeText(context, "You need to login first", Toast.LENGTH_LONG).show()
            }else{
                //Toast.makeText(context, "Added to your cart!", Toast.LENGTH_LONG).show()
                navigationManager?.onReplace(
                    AddCartFragment.newInstance(productId),
                    TransactionType.Replace, true
                )
            }
        }
        btnProductDetailComments.setOnClickListener {
            navigationManager?.onReplace(
                ProductDetailCommentsFragment.newInstance(productId),
                TransactionType.Replace, true
            )
        }
        btnProductDetailBack.setOnClickListener {
            activity?.onBackPressed()
        }

        Log.d("LikedProdEnd", "$prod_ids")
        parse(prod_ids, product)
    }

    override fun onResume() {
        super.onResume()

        if(sharedPref.getUserId().isNullOrEmpty()){

        }else{
            wishListViewModel.onFetchWishListProducts(sharedPref.getUserId() ?: "")

        }

    }

    fun parse(prod_ids: List<String>?, product : Product? ){
        if (prod_ids != null && product != null){
            Log.d("LikedProdParse", "$prod_ids")

            if(prod_ids != null){
                if(prod_ids!!.contains(product!!.id)){
                    if (iv_ProductDetailFav.tag == R.drawable.ic_product_disliked){
                        iv_ProductDetailFav.setImageResource(R.drawable.ic_product_liked)
                        iv_ProductDetailFav.tag = R.drawable.ic_product_liked
                    }
                } else{
                    if (iv_ProductDetailFav.tag == R.drawable.ic_product_liked){
                        iv_ProductDetailFav.setImageResource(R.drawable.ic_product_disliked)
                        iv_ProductDetailFav.tag = R.drawable.ic_product_disliked
                    }
                }
            }


            tvProductDetailName.text = product!!.name
            tvProductDetailVendor.text = product!!.name

            if (product!!.price != product!!.originalPrice) {
                tvProductDetailInfoCampaign.text = "DISCOUNT: Buy this product for " + product!!.price + " instead of " + product!!.originalPrice + "."
            }
            else{
                tvProductDetailInfoCampaign.visibility = View.INVISIBLE
            }

            tvProductDetailInfoBrand.text = "Brand: " + product!!.brand


            var stockStatusColor = ""

//            for (color in product!!.stockValue){
//                stockStatusColor += color.key + " (" + color.value.toString() + ")\n"
//            }
//
//            tvProductDetailInfoColors.text = "Available colors: (stocks) \n" + stockStatusColor
//
//            tvProductDetailInfoSizes.text = "Available sizes: (stocks) \n in progress..."

            tvProductDetailPrice.text = product!!.price.toString() + " TL"
            rbProductDetailRating.rating = product!!.rating.toFloat()
            Glide.with(this)
                .load(product!!.imageUrl).centerCrop()
                .into(ivProductDetailImage)
        }

    }

}