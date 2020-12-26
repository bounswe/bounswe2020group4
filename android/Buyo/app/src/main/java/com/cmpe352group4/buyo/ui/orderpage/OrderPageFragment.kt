package com.cmpe352group4.buyo.ui.orderpage

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.ui.EmptyFragment
import com.cmpe352group4.buyo.vo.*
import kotlinx.android.synthetic.main.fragment_order_page.*


val dummyOrders = mutableListOf(
    Order(
        orderID="S000001",
        orderDate="01.09.2020",
        deliverDate="05.09.2020",
        isDelivered=true,
        product=Product(
            category = listOf("A","B"),
            sizes = listOf("A","B"),
            colors = listOf("Blue", "Gray"),
            name = "LCW Kid Pyjama",
            id = "P00001",
            imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4692240/l_20202-0weg94z1-g4y_a.jpg",
            rating = 5.0,
            price = 45.0,
            originalPrice = 50.0,
            brand = "LCW",
            stockValue = mapOf("S" to 4, "M" to 6,  "L" to 10),
            vendor = Vendor(id = "12", name = "KOTON", rating = 3.21),
            description = "Very nice kid pyjama",
            productInfos = listOf(
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "red"), Attribute(att_name = "size", att_value = "L")
                ), quantity = 2),
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "blue"), Attribute(att_name = "size", att_value = "M")
                ), quantity = 4)
            )
        ),
        address="Rumelihisarı Mah. 19 Sok. No:14/4 Sarıyer/Istanbul"
    ),
    Order(
        orderID="S000002",
        orderDate="04.09.2020",
        deliverDate=null,
        isDelivered=false,
        product=Product(
            category = listOf("A","B"),
            sizes = listOf("A","B"),
            colors = listOf("Blue", "Gray"),
            name = "LCW Men Sweatshirt 0",
            id = "P00002",
            imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/productimages/20192/1/3891903/l_20192-9wr187z8-mgl_a.jpg",
            rating = 5.0,
            price = 60.0,
            originalPrice = 70.0,
            brand = "LCW",
            stockValue = mapOf("S" to 4, "M" to 6,  "L" to 10),
            vendor = Vendor(id = "12", name = "AyseTeyze", rating = 3.21),
            description = "Very nice sweatshirt",
            productInfos = listOf(
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "red"), Attribute(att_name = "size", att_value = "L")
                ), quantity = 2),
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "blue"), Attribute(att_name = "size", att_value = "M")
                ), quantity = 4)
            )
        ),
        address="Rumelihisarı Mah. 19 Sok. No:14/4 Sarıyer/Istanbul"
    ),
    Order(
        orderID="S000003",
        orderDate="04.09.2020",
        deliverDate="05.10.2020",
        isDelivered=true,
        product=Product(
            category = listOf("A","B"),
            sizes = listOf("A","B"),
            colors = listOf("Blue", "Gray"),
            name = "LCW Men Sweatshirt 1",
            id = "P00002",
            imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/productimages/20192/1/3891903/l_20192-9wr187z8-mgl_a.jpg",
            rating = 5.0,
            price = 60.0,
            originalPrice = 70.0,
            brand = "LCW",
            stockValue = mapOf("S" to 4, "M" to 6,  "L" to 10),
            vendor = Vendor(id = "12", name = "DeFacto", rating = 3.21),
            description = "Very nice sweatshirt",
            productInfos = listOf(
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "red"), Attribute(att_name = "size", att_value = "L")
                ), quantity = 2),
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "blue"), Attribute(att_name = "size", att_value = "M")
                ), quantity = 4)
            )
        ),
        address="Rumelihisarı Mah. 19 Sok. No:14/4 Sarıyer/Istanbul"
    ),
    Order(
        orderID="S000004",
        orderDate="04.09.2020",
        deliverDate=null,
        isDelivered=false,
        product=Product(
            category = listOf("A","B"),
            sizes = listOf("A","B"),
            colors = listOf("Blue", "Gray"),
            name = "LCW Men Sweatshirt 2",
            id = "P00002",
            imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/productimages/20192/1/3891903/l_20192-9wr187z8-mgl_a.jpg",
            rating = 5.0,
            price = 60.0,
            originalPrice = 70.0,
            brand = "LCW",
            stockValue = mapOf("S" to 4, "M" to 6,  "L" to 10),
            vendor = Vendor(id = "12", name = "LCW", rating = 3.21),
            description = "Very nice sweatshirt",
            productInfos = listOf(
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "red"), Attribute(att_name = "size", att_value = "L")
                ), quantity = 2),
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "blue"), Attribute(att_name = "size", att_value = "M")
                ), quantity = 4)
            )
        ),
        address="Rumelihisarı Mah. 19 Sok. No:14/4 Sarıyer/Istanbul"
    ),
    Order(
        orderID="S000005",
        orderDate="04.09.2020",
        deliverDate="09.10.2020",
        isDelivered=true,
        product=Product(
            category = listOf("A","B"),
            sizes = listOf("A","B"),
            colors = listOf("Blue", "Gray"),
            name = "LCW Men Sweatshirt 3",
            id = "P00002",
            imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/productimages/20192/1/3891903/l_20192-9wr187z8-mgl_a.jpg",
            rating = 5.0,
            price = 60.0,
            originalPrice = 70.0,
            brand = "LCW",
            stockValue = mapOf("S" to 4, "M" to 6,  "L" to 10),
            vendor = Vendor(id = "12", name = "H&M", rating = 3.21),
            description = "Very nice sweatshirt",
            productInfos = listOf(
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "red"), Attribute(att_name = "size", att_value = "L")
                ), quantity = 2),
                ProductInfo(attributes = listOf(
                    Attribute(att_name = "color", att_value = "blue"), Attribute(att_name = "size", att_value = "M")
                ), quantity = 4)
            )
        ),
        address="Rumelihisarı Mah. 19 Sok. No:14/4 Sarıyer/Istanbul"
    )
)

class OrderPageFragment : BaseFragment() {

    companion object {
        fun newInstance() = OrderPageFragment()
    }

    private val orderAdapter by lazy {
        OrderAdapter(mutableListOf(),
            { order ->
                val myToast = Toast.makeText(
                    context,
                    "Order ID: "+order.orderID+" clicked",
                    Toast.LENGTH_SHORT
                )
                myToast.setGravity(Gravity.BOTTOM, 0, 200)
                myToast.show()
                navigationManager?.onReplace(
                    EmptyFragment.newInstance(),
                    TransactionType.Replace, true
                )
            },
            { order ->
                val myToast = Toast.makeText(
                    context,
                    "Send message to " + order.product.vendor.name,
                    Toast.LENGTH_SHORT
                )
                myToast.setGravity(Gravity.BOTTOM, 0, 200)
                myToast.show()
                navigationManager?.onReplace(
                    EmptyFragment.newInstance(),
                    TransactionType.Replace, true
                )
            },
            { order ->
                if (order.isDelivered) {
                    val myToast = Toast.makeText(
                        context,
                        "Comment on " + order.product.name,
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                    navigationManager?.onReplace(
                        EmptyFragment.newInstance(),
                        TransactionType.Replace, true
                    )
                } else {
                    val myToast = Toast.makeText(
                        context,
                        "Cancel " + order.product.name,
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                    navigationManager?.onReplace(
                        EmptyFragment.newInstance(),
                        TransactionType.Replace, true
                    )
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_order_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_paid_items.adapter = orderAdapter
        rv_paid_items.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )
        orderAdapter.submitList(dummyOrders)

    }
}