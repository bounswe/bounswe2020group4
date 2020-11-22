package com.cmpe352group4.buyo.ui.wishList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.viewmodel.WishListViewModel
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.fragment_wish_list.*
import javax.inject.Inject

class WishListFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val wishListViewModel: WishListViewModel by activityViewModels {
        viewModelFactory
    }

    private var newWishListProducts: List<Product>? = null


    companion object {
        fun newInstance() = WishListFragment()
    }

    private val wishListAdapter by lazy {
        WishListAdapter(mutableListOf(),
            { productID ->
                //Remove product from wish list functionality

                Log.v("berkay", "delete")
            },
            { productID ->
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(productID),
                    TransactionType.Replace, true
                )
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

        btnOrderWishList.setOnClickListener{
            TODO()
        }

        btnFilterWishList.setOnClickListener{
            TODO()
        }

        btnDeleteWishlist.setOnClickListener {
            TODO()
        }

        // RECYCLER VIEW
        //var dummyComment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."

        /*var productList = mutableListOf(
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
        )*/

        //These will be in if statement
        /*wishListViewModel.onFetchWishListProducts(true)

        wishListViewModel.wishListProducts.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null) {

                wishListAdapter.submitList(it.data.products as MutableList<Category>)

                dispatchLoading()
            } else if (it.status == Status.ERROR) {
                dispatchLoading()
            } else if (it.status == Status.LOADING) {
                showLoading()
            }
        })*/

        rvWishListProducts.adapter = wishListAdapter
        rvWishListProducts.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val decorator = DividerItemDecoration(rvWishListProducts.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rvWishListProducts.context, R.drawable.divider_drawable)!!)
        rvWishListProducts.addItemDecoration(decorator)

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