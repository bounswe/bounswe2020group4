package com.cmpe352group4.buyo.ui.wishList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.fragment_wish_list.*
import kotlinx.android.synthetic.main.item_wish_list_recycler_view.*

class WishListFragment: BaseFragment() {

    companion object {
        fun newInstance() = WishListFragment()
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

        btnOrderWishList.setOnClickListener{
            TODO()
        }

        btnFilterWishList.setOnClickListener{
            TODO()
        }

        btnDeleteWishlist.setOnClickListener{
            TODO()
        }

        btnRemoveProductFromWishList.setOnClickListener{
            TODO()
        }
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

        /*
        val wishListProductListAdapter by lazy {
            WishListAdapter(productList) { product ->
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(product.productID),
                    TransactionType.Replace, true
                )
            }
        }

        rvWishListProducts.adapter = wishListProductListAdapter */
        rvWishListProducts.layoutManager = LinearLayoutManager(this.context)

        spWishList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")
            }
        }
    }



}