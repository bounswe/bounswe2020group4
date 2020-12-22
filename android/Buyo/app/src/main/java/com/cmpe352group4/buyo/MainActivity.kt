package com.cmpe352group4.buyo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.cmpe352group4.buyo.base.BaseActivity
import com.cmpe352group4.buyo.base.ConnectionManager
import com.cmpe352group4.buyo.base.fragment_ops.NavigationManager
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.navigationtabs.*
import com.cmpe352group4.buyo.util.GeneralUtil
import com.cmpe352group4.buyo.widgets.navigation_bar.NavigationBar
import com.cmpe352group4.buyo.widgets.navigation_bar.NavigationBarOnClickListener

import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), NavigationBarOnClickListener {

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    @Inject
    lateinit var sharedPref: SharedPref

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
//            (context as Activity).finish()
        }

    }
    @Inject
    lateinit var connectionManager: ConnectionManager
    @Inject
    lateinit var navManager: NavigationManager
    private val pagerAdapter: NavigationPagerAdapter by lazy {
        NavigationPagerAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
    }


    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initialize() {
        initNavigationBar()
        connectionManager.setActivity(this)
        sharedPref.saveUserId("")
        sharedPref.saveVendorLat("")
        sharedPref.saveVendorLon("")
    }

    override fun onItemSelected(position: Int) {
        navManager.setConsumerName(pagerAdapter.getClassName(position))
        navigationPager.setCurrentItem(position, false)
    }

    override fun onItemReselected(position: Int) {
        navManager.activeFragment()?.let { stackOwner ->
            navManager.consumeReselect()
        }
    }

    private fun initNavigationBar() {
        pagerAdapter.setFragments(initFragments())
        navigationBar.setOnClickListener(this)
        navigationPager.adapter = pagerAdapter
        navigationPager.offscreenPageLimit = pagerAdapter.count
    }

    private fun initFragments() = listOf<Fragment>(
        HomeTabContainer.newInstance(), CategoryTabContainer.newInstance(),
        WishlistTabContainer.newInstance(), CartTabContainer.newInstance(),
        ProfileTabContainer.newInstance()
    )

    fun changeActiveTab(position: Int) {
        navigationBar.changeActiveTab(position)
        if (position != NavigationBar.FULL_SCREEN_INDEX) {
            navManager.dispatchFullScreenNavigation()
        }
    }

    private val exitRunnable = Runnable {
        finishAffinity()
    }

    override fun onBackPressed() {
        if (!navManager.consumeBackPress()){

            if (navigationBar.currentSelectedItem != navigationBar.home) {
                navigationBar.changeActiveTab(0)
            }else{
                GeneralUtil.dialogWithOneOptions(
                    this,
                    "",
                    "Are you sure you want to quit Buyo?",
                    "Yes",
                    "Cancel",
                    exitRunnable
                )
            }
        }
    }
}
