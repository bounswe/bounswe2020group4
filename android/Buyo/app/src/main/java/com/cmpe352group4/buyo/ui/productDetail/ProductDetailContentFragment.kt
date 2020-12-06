package com.cmpe352group4.buyo.ui.productDetail

import android.graphics.drawable.Drawable
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
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.viewmodel.WishListViewModel
import com.cmpe352group4.buyo.vo.LikeResponse
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.fragment_product_detail_comments.*
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

    private var isToggleChangedByUser = true

    private val wishListViewModel: WishListViewModel by viewModels {
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

        iv_ProductDetailFav.tag = R.drawable.ic_product_disliked


        // Backend request

        var WishListProducts: List<Product>? = null

        var prod_ids : List<Int>? = null


        // GET LIKED PRODUCTS IF A USER LOGGED IN

        if(sharedPref.getUserId().isNullOrEmpty()){
            Log.i("ProductList", "Guest User")

        }else {
            wishListViewModel.onFetchWishListProducts(sharedPref.getUserId()?.toInt() ?: -1)

            wishListViewModel.wishListProducts.observe(viewLifecycleOwner, Observer {
                if (it.status == Status.SUCCESS && it.data != null) {

                    WishListProducts = it.data.products as MutableList<Product>

                    prod_ids = WishListProducts?.map{it.id}

                    dispatchLoading()
                } else if (it.status == Status.ERROR) {
                    dispatchLoading()
                } else if (it.status == Status.LOADING) {
                    showLoading()
                }
            })
        }


        // PARSE PRODUCT

        productViewModel.onFetchProductById(productId)
        productViewModel.productDetail.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null){

                if(prod_ids != null){
                    if(prod_ids!!.contains(it.data.result.id)){
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


                tvProductDetailName.text = it.data.result.name
                tvProductDetailVendor.text = it.data.result.vendor.name
                /*
                tvProductDetailInfo.text = "Brand: " + it.data.result.brand + "\n" +
                        "Vendor Rating: " + it.data.result.vendor.rating.toString() + "\n" +
                        "Category: " + it.data.result.category.toString() + "\n" +
                        "Available sizes: " + it.data.result.sizes + "\n" +
                        "Available colors: " + it.data.result.colors.toString() + "\n" +
                        "Rating: " + it.data.result.rating + "\n" +
                        "Original Price: " + it.data.result.originalPrice.toString() + "\n" +
                        "Current Price: " + it.data.result.price.toString() + "\n"

                 */

                if (it.data.result.price != it.data.result.originalPrice) {
                    tvProductDetailInfoCampaign.text = "DISCOUNT: Buy this product for " + it.data.result.price + " instead of " + it.data.result.originalPrice + "."
                }
                else{
                    tvProductDetailInfoCampaign.visibility = View.INVISIBLE
                }

                tvProductDetailInfoBrand.text = "Brand: " + it.data.result.brand


                var stockStatusColor = ""

                for (color in it.data.result.stockValue){
                    stockStatusColor += color.key + " (" + color.value.toString() + ")\n"
                }

                tvProductDetailInfoColors.text = "Available colors: \n" + stockStatusColor

                tvProductDetailInfoSizes.text = "Available sizes: \n in progress..."

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



        //LIKING / UNLIKING

        iv_ProductDetailFav.setOnClickListener {
            if (sharedPref.getUserId().isNullOrEmpty()) {
                Toast.makeText(context, "You need to login first", Toast.LENGTH_LONG).show()
            } else {
                if (iv_ProductDetailFav.tag == R.drawable.ic_product_disliked) {
                    iv_ProductDetailFav.setImageResource(R.drawable.ic_product_liked)
                    iv_ProductDetailFav.tag = R.drawable.ic_product_liked
                    wishListViewModel.onPostWhislistUpdate(
                        LikeResponse(
                            sharedPref.getUserId()?.toInt() ?: -1, productId
                        )
                    )

                    wishListViewModel.statusUnlike.observe(viewLifecycleOwner, Observer {
                        if (it.status == Status.SUCCESS && it.data != null) {

                            /*Toast.makeText(
                                context,
                                "Product is added to your wishlist!",
                                Toast.LENGTH_SHORT
                            ).show()*/

                            dispatchLoading()
                        } else if (it.status == Status.ERROR) {
                            dispatchLoading()
                        } else if (it.status == Status.LOADING) {
                            showLoading()
                        }
                    })

                } else if (iv_ProductDetailFav.tag == R.drawable.ic_product_liked) {
                    iv_ProductDetailFav.setImageResource(R.drawable.ic_product_disliked)
                    iv_ProductDetailFav.tag = R.drawable.ic_product_disliked

                    wishListViewModel.onPostWhislistUpdate(
                        LikeResponse(
                            sharedPref.getUserId()?.toInt() ?: -1, productId
                        )
                    )

                    wishListViewModel.statusUnlike.observe(viewLifecycleOwner, Observer {
                        if (it.status == Status.SUCCESS && it.data != null) {

                            /*Toast.makeText(
                                context,
                                "Product is removed from your wishlist!",
                                Toast.LENGTH_SHORT
                            ).show()*/

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

        /*

        tbProductDetailFav.setOnCheckedChangeListener { favButton, isChecked ->
            if (!isToggleChangedByUser) {
                isToggleChangedByUser = true
            } else {
                if (sharedPref.getUserId().isNullOrEmpty()) {
                    Toast.makeText(context, "You need to login first", Toast.LENGTH_LONG).show()
                    isToggleChangedByUser = false
                    favButton.toggle()
                } else {
                    if (isChecked) {
                        wishListViewModel.onPostWhislistUpdate(
                            LikeResponse(
                                sharedPref.getUserId()?.toInt() ?: -1, productId
                            )
                        )

                        wishListViewModel.statusUnlike.observe(viewLifecycleOwner, Observer {
                            if (it.status == Status.SUCCESS && it.data != null) {

                                /*Toast.makeText(
                                    context,
                                    "Product is added to your wishlist!",
                                    Toast.LENGTH_SHORT
                                ).show()*/

                                dispatchLoading()
                            } else if (it.status == Status.ERROR) {
                                dispatchLoading()
                            } else if (it.status == Status.LOADING) {
                                showLoading()
                            }
                        })

                    } else {
                        wishListViewModel.onPostWhislistUpdate(
                            LikeResponse(
                                sharedPref.getUserId()?.toInt() ?: -1, productId
                            )
                        )

                        wishListViewModel.statusUnlike.observe(viewLifecycleOwner, Observer {
                            if (it.status == Status.SUCCESS && it.data != null) {

                                /*Toast.makeText(
                                    context,
                                    "Product is removed from your wishlist!",
                                    Toast.LENGTH_SHORT
                                ).show()*/

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


         */

        // ADD CART AND OTHER BUTTONS

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