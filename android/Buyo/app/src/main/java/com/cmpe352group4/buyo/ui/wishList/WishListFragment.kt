package com.cmpe352group4.buyo.ui.wishList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.MainActivity
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.NavigationManager
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.fragment_wish_list.*
import javax.inject.Inject

class WishListFragment: BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    companion object {
        fun newInstance() = WishListFragment()
    }

    private val wishListAdapter by lazy {
        WishListAdapter(mutableListOf(),
            { productID ->
                //Delete functionality
                Log.v("berkay", "delete")
            },
            { productID ->
                /*navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(productID),
                    TransactionType.Replace, true
                )*/
                Log.v("berkay", "detail")
            })

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wish_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(sharedPref.getUserId().isNullOrEmpty()){
            cl_nonlogin.visible = true
            cl_wishlist.visible = false
        }else{
            cl_nonlogin.visible = false
            cl_wishlist.visible = true
        }

        loginButton.setOnClickListener {
            (activity as MainActivity).onItemSelected(4)
        }

        btnOrderWishList.setOnClickListener{
            TODO()
        }

        btnFilterWishList.setOnClickListener{
            TODO()
        }

        /*
        // RECYCLER VIEW
        var dummyComment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."

        var productList = mutableListOf(
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = dummyComment, productName = "MyItemName1",
                productID = 1,
                productNumComments = 0,
                productRate = 1.1,
                productPrice = "0.0",
                productReleaseDate = "01.01.2020"),
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = dummyComment,
                productName = "MyItemName2",
                productID = 2,
                productNumComments = 0,
                productRate = 1.1,
                productPrice = "0.0",
                productReleaseDate = "01.01.2020" ),
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = dummyComment,
                productName = "MyItemName3",
                productID = 3,
                productNumComments = 0,
                productRate = 1.1,
                productPrice = "0.0",
                productReleaseDate = "01.01.2020" ),
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = dummyComment,
                productName = "MyItemName4",
                productID = 4,
                productNumComments = 0,
                productRate = 1.1,
                productPrice = "0.0",
                productReleaseDate = "01.01.2020" )
        )


        wishListAdapter.submitList(productList)
        rvWishListProducts.adapter = wishListAdapter
        rvWishListProducts.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

         */

//        spWishList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                adapterView: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                TODO("Not yet implemented")
//            }
//        }
    }



}