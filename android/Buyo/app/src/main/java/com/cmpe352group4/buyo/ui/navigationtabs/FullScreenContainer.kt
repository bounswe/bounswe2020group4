package com.cmpe352group4.buyo.ui.navigationtabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cmpe352group4.buyo.MainActivity
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.fragment_ops.*
import com.cmpe352group4.buyo.dependencyinjection.Injectable
import com.cmpe352group4.buyo.ui.EmptyFragment
import com.cmpe352group4.buyo.util.extensions.hideKeyboardFrom
import javax.inject.Inject

class FullScreenContainer : StackOwnerFragment(), INavigationManager, Injectable {
    companion object {
        fun newInstance() = FullScreenContainer()
    }

    @Inject
    lateinit var navManager: NavigationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_full_screen, container, false)
        showInitialFragment()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun getInitialFragment(): Fragment {
        return EmptyFragment.newInstance()
    }

    override fun getNavigationManager(): NavigationManager {
        return navManager
    }

    override fun getHostLayoutId(): Int = R.id.fullScreenContainer

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
        showFragment(
            childFragmentManager,
            FragmentTransactionContainer.Builder()
                .setLayoutId(getHostLayoutId())
                .setTransactionType(transactionType)
                .setHasAnimation(true)
                .setBackState(true, null)
                .build(), target
        )
    }
}