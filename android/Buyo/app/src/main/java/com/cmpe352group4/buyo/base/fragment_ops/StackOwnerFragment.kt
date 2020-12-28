package com.cmpe352group4.buyo.base.fragment_ops

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cmpe352group4.buyo.base.BaseFragment

private const val INITIAL_FRAG_TAG = "initial_fragment"

abstract class StackOwnerFragment : BaseFragment(), ViewStackOwner {

    abstract fun getInitialFragment(): Fragment

    abstract fun getNavigationManager(): NavigationManager

    abstract fun getHostLayoutId(): Int

    override fun consumePopBackStackEvent(): Boolean {
        if (childFragmentManager.backStackEntryCount == 1)
            return false
        return childFragmentManager.popBackStackImmediate()
    }

    override fun resetToInitialState() {
        childFragmentManager.popBackStackImmediate(INITIAL_FRAG_TAG, 0)
    }

    override fun getStackCount(): Int = if(!isAdded) 0 else childFragmentManager.backStackEntryCount

    override fun lastFragment(): Fragment? {
        if(!isAdded)
            return null
        return if (childFragmentManager.fragments.isNotEmpty()) childFragmentManager.fragments.last() else null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getNavigationManager().registerStackOwner(javaClass.name, this)
        super.onCreate(savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
        //getNavigationManager().unRegisterStackOwner(javaClass.name)
    }

    protected fun showInitialFragment() {
        val container = FragmentTransactionContainer.Builder()
            .setBackState(true, INITIAL_FRAG_TAG)
            .setHasAnimation(false)
            .setLayoutId(getHostLayoutId())
            .setTransactionType(TransactionType.Add)
            .build()
        showFragment(childFragmentManager, container, getInitialFragment())

    }
}