package com.cmpe352group4.buyo.ui.navigationtabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.cmpe352group4.buyo.widgets.navigation_bar.NavigationBar

class NavigationPagerAdapter(
    fm: FragmentManager,
    behavior: Int
) : FragmentStatePagerAdapter(fm, behavior) {
    private val fragments = mutableListOf<Fragment>()
    override fun getItem(position: Int): Fragment =
        when (position) {
            NavigationBar.HOME_INDEX -> fragments[0]
            NavigationBar.CATEGORY_INDEX -> fragments[1]
            NavigationBar.WISHLIST_INDEX -> fragments[2]
            NavigationBar.CART_INDEX -> fragments[3]
            NavigationBar.PROFILE_INDEX -> fragments[4]
            else -> throw RuntimeException("Illegal position exception")
        }


    override fun getCount(): Int = fragments.size

    fun getClassName(position: Int) = when (position) {
        NavigationBar.HOME_INDEX -> HomeTabContainer::class.java.name
        NavigationBar.CATEGORY_INDEX -> CategoryTabContainer::class.java.name
        NavigationBar.WISHLIST_INDEX -> WishlistTabContainer::class.java.name
        NavigationBar.CART_INDEX -> CartTabContainer::class.java.name
        NavigationBar.PROFILE_INDEX -> ProfileTabContainer::class.java.name
        NavigationBar.FULL_SCREEN_INDEX -> FullScreenContainer::class.java.name
        else -> HomeTabContainer::class.java.name
    }

    fun setFragments(list: List<Fragment>) {
        fragments.clear()
        fragments.addAll(list)
    }
}