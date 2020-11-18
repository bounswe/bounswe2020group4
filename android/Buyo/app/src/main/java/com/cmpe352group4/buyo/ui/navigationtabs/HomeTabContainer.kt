package com.cmpe352group4.buyo.ui.navigationtabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.fragment_ops.*
import com.cmpe352group4.buyo.dependencyinjection.Injectable
import com.cmpe352group4.buyo.ui.HomepageFragment
import com.cmpe352group4.buyo.util.extensions.hideKeyboardFrom
import javax.inject.Inject

class HomeTabContainer : StackOwnerFragment(), INavigationManager, Injectable {

    companion object {
        fun newInstance() = HomeTabContainer()
    }

    @Inject
    lateinit var navManager: NavigationManager
    private val initialFragment by lazy {
        HomepageFragment.newInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_host_layout, container, false)
        showInitialFragment()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navManager.setConsumerName(HomeTabContainer::class.java.name)

    }

    override fun getInitialFragment(): Fragment = initialFragment

    override fun getNavigationManager(): NavigationManager = navManager

    override fun getHostLayoutId(): Int = R.id.hostFragment

    override fun onReplace(target: Fragment, transactionType: TransactionType, isBackStack: Boolean) {
        hideKeyboardFrom(context, view?.windowToken)
        showFragment(
            childFragmentManager,
            FragmentTransactionContainer.Builder()
                .setLayoutId(getHostLayoutId())
                .setTransactionType(transactionType)
                .setHasAnimation(true)
                .setBackState(isBackStack, null)
                .build(), target
        )
    }

    override fun onReplaceFullScreen(target: Fragment, transactionType: TransactionType) {
        hideKeyboardFrom(context, view?.windowToken)
        navManager.setFullScreenConsumerName(FullScreenContainer::class.java.name)
        (navManager.activeFragment() as? FullScreenContainer)?.onReplace(
            target,
            transactionType
        )
    }

}