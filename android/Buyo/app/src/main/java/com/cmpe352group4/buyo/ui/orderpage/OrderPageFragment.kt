package com.cmpe352group4.buyo.ui.orderpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.vo.*


val dummyOrders = mutableListOf(
    Order(
        orderID="S000001",
        orderDate="2020-09-01",
        deliverDate="2020-09-05",
        isDelivered=true,
        product=Product(
            category = listOf("A","B"),
            sizes = listOf("A","B"),
            colors = null,
            name = "LCW Kid Pyjama",
            id = "P00001",
            imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4692240/l_20202-0weg94z1-g4y_a.jpg",
            rating = 5.0,
            price = 45.0,
            originalPrice = 50.0,
            brand = "LCW",
            stockValue = mapOf("S" to 4, "M" to 6,  "L" to 10),
            vendor = Vendor(id = "12", name = "AyseTeyze", rating = 3.21),
            description = "Very nice kid pyjama"
        ),
        address="Rumelihisarı Mah. 19 Sok. No:14/4 Sarıyer/Istanbul"
    ),
    Order(
        orderID="S000001",
        orderDate="2020-09-01",
        deliverDate=null,
        isDelivered=false,
        product=Product(
            category = listOf("A","B"),
            sizes = listOf("A","B"),
            colors = null,
            name = "LCW Men Sweatshirt",
            id = "P00002",
            imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/productimages/20192/1/3891903/l_20192-9wr187z8-mgl_a.jpg",
            rating = 5.0,
            price = 60.0,
            originalPrice = 70.0,
            brand = "LCW",
            stockValue = mapOf("S" to 4, "M" to 6,  "L" to 10),
            vendor = Vendor(id = "12", name = "AyseTeyze", rating = 3.21),
            description = "Very nice sweatshirt"
        ),
        address="Rumelihisarı Mah. 19 Sok. No:14/4 Sarıyer/Istanbul"
    )
)

class OrderPageFragment : BaseFragment() {

    companion object {
        fun newInstance() = OrderPageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_order_page, container, false)
    }


}