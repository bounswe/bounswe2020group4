package com.cmpe352group4.buyo.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.ui.productList.ProductListFragment
import com.cmpe352group4.buyo.vo.Product
import com.cmpe352group4.buyo.vo.Vendor
import kotlinx.android.synthetic.main.fragment_homepage.*

class HomepageFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomepageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SEARCH

        searchBarSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                if (keyword == ""){
                    return false
                }
                else {
                    navigationManager?.onReplace(
                        ProductListFragment.newInstance(keyword = keyword),
                        TransactionType.Replace, true
                    )
                    return true
                }

            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })


        // Recommendation RV
        var recommendedProductList = mutableListOf(
            Product(
                category = listOf("Bebek","Kiz Bebek","Takim"),
                sizes = null,
                colors = listOf(),
                name = "Kiz Bebek Takim 2'li",
                id = 10000,
                imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4353680/l_20202-0w9038z1-qca_a.jpg",
                rating = 1.95,
                price = 35.99,
                originalPrice = 35.99,
                brand = "Koton",
                vendor = Vendor("Ahmet",3.22)
            ),
            Product(
                category = listOf("Bebek","Kiz Bebek","Takim"),
                sizes = null,
                colors = listOf(),
                name = "Kiz Bebek Takim 2'li",
                id = 10000,
                imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4353680/l_20202-0w9038z1-qca_a.jpg",
                rating = 1.95,
                price = 35.99,
                originalPrice = 35.99,
                brand = "Koton",
                vendor = Vendor("Ahmet",3.22)
            ),
            Product(
                category = listOf("Bebek","Kiz Bebek","Takim"),
                sizes = null,
                colors = listOf(),
                name = "Kiz Bebek Takim 2'li",
                id = 10000,
                imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4353680/l_20202-0w9038z1-qca_a.jpg",
                rating = 1.95,
                price = 35.99,
                originalPrice = 35.99,
                brand = "Koton",
                vendor = Vendor("Ahmet",3.22)
            ),
            Product(
                category = listOf("Bebek","Kiz Bebek","Takim"),
                sizes = null,
                colors = listOf(),
                name = "Kiz Bebek Takim 2'li",
                id = 10000,
                imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4353680/l_20202-0w9038z1-qca_a.jpg",
                rating = 1.95,
                price = 35.99,
                originalPrice = 35.99,
                brand = "Koton",
                vendor = Vendor("Ahmet",3.22)
            )
        )

        val recommendedProductListAdapter by lazy {
            ProductAdapter(recommendedProductList) { product ->
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(product.id),
                    TransactionType.Replace, true
                )
            }
        }

        recommendationsRecyclerView.adapter = recommendedProductListAdapter
        recommendationsRecyclerView.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        )

        // Discount RV
        var discountProductList = mutableListOf(
            Product(
                category = listOf("Bebek","Kiz Bebek","Takim"),
                sizes = null,
                colors = listOf(),
                name = "Kiz Bebek Takim 2'li",
                id = 10000,
                imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4353680/l_20202-0w9038z1-qca_a.jpg",
                rating = 1.95,
                price = 35.99,
                originalPrice = 35.99,
                brand = "Koton",
                vendor = Vendor("Ahmet",3.22)
            ),
            Product(
                category = listOf("Bebek","Kiz Bebek","Takim"),
                sizes = null,
                colors = listOf(),
                name = "Kiz Bebek Takim 2'li",
                id = 10000,
                imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4353680/l_20202-0w9038z1-qca_a.jpg",
                rating = 1.95,
                price = 35.99,
                originalPrice = 35.99,
                brand = "Koton",
                vendor = Vendor("Ahmet",3.22)
            ),
            Product(
                category = listOf("Bebek","Kiz Bebek","Takim"),
                sizes = null,
                colors = listOf(),
                name = "Kiz Bebek Takim 2'li",
                id = 10000,
                imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4353680/l_20202-0w9038z1-qca_a.jpg",
                rating = 1.95,
                price = 35.99,
                originalPrice = 35.99,
                brand = "Koton",
                vendor = Vendor("Ahmet",3.22)
            ),
            Product(
                category = listOf("Bebek","Kiz Bebek","Takim"),
                sizes = null,
                colors = listOf(),
                name = "Kiz Bebek Takim 2'li",
                id = 10000,
                imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4353680/l_20202-0w9038z1-qca_a.jpg",
                rating = 1.95,
                price = 35.99,
                originalPrice = 35.99,
                brand = "Koton",
                vendor = Vendor("Ahmet",3.22)
            )
        )

        val discountProductListAdapter by lazy {
            ProductAdapter(discountProductList) { product ->
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(product.id),
                    TransactionType.Replace, true
                )
            }
        }

        discountRecyclerView.adapter = discountProductListAdapter
        discountRecyclerView.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        )
    }
}