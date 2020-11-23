package com.cmpe352group4.buyo.widgets.navigation_bar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.util.extensions.inflate
import com.cmpe352group4.buyo.widgets.navigation_bar.navigation_bar_item.INavigationBarItem
import com.cmpe352group4.buyo.widgets.navigation_bar.navigation_bar_item.NavigationBarItem

class NavigationBar : ConstraintLayout, View.OnClickListener {
    companion object {
        const val HOME_INDEX = 0
        const val CATEGORY_INDEX = 1
        const val WISHLIST_INDEX = 2
        const val CART_INDEX = 3
        const val PROFILE_INDEX = 4
        const val FULL_SCREEN_INDEX = 99
    }

    lateinit var home: INavigationBarItem
    lateinit var category: INavigationBarItem
    lateinit var wishlist: INavigationBarItem
    lateinit var cart: INavigationBarItem
    lateinit var profile: INavigationBarItem
    lateinit var currentSelectedItem: INavigationBarItem
    private var onClickListener: NavigationBarOnClickListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        init()
    }

    override fun onClick(p0: View?) {
        if (p0 == currentSelectedItem) {
            dispatchReSelectEvent(p0)
            return
        }
        currentSelectedItem.setUnselected()
        currentSelectedItem = p0 as INavigationBarItem
        (p0 as INavigationBarItem).setSelected()
        dispatchEvent(p0)
    }

    private fun init() {
        inflate(R.layout.layout_navigation_bar, true)
        initFields()
        setClickListeners()
        home.setSelected()
        currentSelectedItem = home
    }

    private fun initFields() {
        home = findViewById<NavigationBarItem>(R.id.homeItem)
        category = findViewById<NavigationBarItem>(R.id.categoryItem)
        wishlist = findViewById<NavigationBarItem>(R.id.wishlistItem)
        cart = findViewById<NavigationBarItem>(R.id.cartItem)
        profile = findViewById<NavigationBarItem>(R.id.profileItem)
        setIcons()
        setTitles()
    }

    private fun setClickListeners() {
        home.setClickListener(this)
        category.setClickListener(this)
        wishlist.setClickListener(this)
        cart.setClickListener(this)
        profile.setClickListener(this)
    }

    private fun setIcons() {
//        home.setIcon(
//            ContextCompat.getDrawable(context, R.drawable.ic_nav_item_home_unselected),
//            ContextCompat.getDrawable(context, R.drawable.ic_nav_item_home_selected)
//        )
//        category.setIcon(
//            ContextCompat.getDrawable(context, R.drawable.ic_nav_item_category_unselected),
//            ContextCompat.getDrawable(context, R.drawable.ic_nav_item_category_selected)
//        )
//        wishlist.setIcon(
//            ContextCompat.getDrawable(context, R.drawable.ic_nav_item_wishlist_selected),
//            ContextCompat.getDrawable(context, R.drawable.ic_nav_item_wishlist_selected)
//        )
//        cart.setIcon(
//            ContextCompat.getDrawable(context, R.drawable.ic_nav_item_cart_unselected),
//            ContextCompat.getDrawable(context, R.drawable.ic_nav_item_cart_selected)
//        )
//        profile.setIcon(
//            ContextCompat.getDrawable(context, R.drawable.ic_nav_item_profile_unselected),
//            ContextCompat.getDrawable(context, R.drawable.ic_nav_item_profile_selected)
//        )
    }

    private fun setTitles() {
        home.setTitle(resources.getString(R.string.navigation_home))
        category.setTitle(resources.getString(R.string.navigation_category))
        wishlist.setTitle(resources.getString(R.string.navigation_wishlist))
        cart.setTitle(resources.getString(R.string.navigation_cart))
        profile.setTitle(resources.getString(R.string.navigation_profile))
    }

    fun setOnClickListener(listener: NavigationBarOnClickListener) {
        onClickListener = listener
    }

    private fun dispatchEvent(v: View) {
        when (v) {
            home -> {
                onClickListener?.onItemSelected(HOME_INDEX)
            }
            category -> {
                onClickListener?.onItemSelected(CATEGORY_INDEX)
            }
            wishlist -> {
                onClickListener?.onItemSelected(WISHLIST_INDEX)
            }
            cart -> {
                onClickListener?.onItemSelected(CART_INDEX)
            }
            profile -> {
                onClickListener?.onItemSelected(PROFILE_INDEX)
            }
        }
    }

    private fun dispatchReSelectEvent(v: View) {
        when (v) {
            home -> onClickListener?.onItemReselected(HOME_INDEX)
            category -> onClickListener?.onItemReselected(CATEGORY_INDEX)
            wishlist -> onClickListener?.onItemReselected(WISHLIST_INDEX)
            cart -> onClickListener?.onItemReselected(CART_INDEX)
            profile -> onClickListener?.onItemReselected(PROFILE_INDEX)
        }
    }

    fun changeActiveTab(position: Int) {
        when (position) {
            HOME_INDEX -> home.confirmClick()
            CATEGORY_INDEX -> category.confirmClick()
            WISHLIST_INDEX -> wishlist.confirmClick()
            CART_INDEX -> cart.confirmClick()
            PROFILE_INDEX -> profile.confirmClick()
        }
    }

    fun highlightActiveTab(position: Int) {
        home.setUnselected()
        category.setUnselected()
        wishlist.setUnselected()
        cart.setUnselected()
        profile.setUnselected()
        when (position) {
            HOME_INDEX -> {
                home.setSelected()
                currentSelectedItem = home
            }
            CATEGORY_INDEX -> {
                category.setSelected()
                currentSelectedItem = category
            }
            WISHLIST_INDEX -> {
                wishlist.setSelected()
                currentSelectedItem = wishlist
            }
            CART_INDEX -> {
                cart.setSelected()
                currentSelectedItem = cart
            }
            PROFILE_INDEX -> {
                profile.setSelected()
                currentSelectedItem = profile
            }
        }
    }
}