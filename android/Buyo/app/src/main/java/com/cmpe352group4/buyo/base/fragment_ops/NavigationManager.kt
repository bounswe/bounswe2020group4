package com.cmpe352group4.buyo.base.fragment_ops

import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {
    private val stackOwnerMap by lazy {
        HashMap<String, ViewStackOwner>()
    }
    private var stackOwner: ViewStackOwner? = null
    private var previousStackOwnerName: String? = null
    private var betSlipSummerVisibleState = true
    private var appBarHeight = 0
    private var appBar: AppBarLayout? = null

    fun registerStackOwner(className: String, owner: ViewStackOwner) {
        stackOwnerMap[className] = owner
    }

    fun unRegisterStackOwner(className: String) {
        if(stackOwnerMap[className] == null){
            stackOwner = null
        }
        stackOwnerMap.remove(className)
    }

    fun clearAllStackOwner() {
        stackOwnerMap.clear()
        stackOwner = null
    }

    fun consumeBackPress(): Boolean {
        stackOwner?.run {
            return stackOwner?.consumePopBackStackEvent() ?: false
        }
        return false
    }

    fun setConsumerName(className: String) {
        previousStackOwnerName = className
        stackOwner = stackOwnerMap[className]
    }

    fun setFullScreenConsumerName(className: String) {
        stackOwner = stackOwnerMap[className]
    }

    fun activeFragment() = stackOwner



    fun consumeReselect() {
        stackOwner?.resetToInitialState()
    }

    fun getBackStackCount() = stackOwner?.getStackCount() ?: 1

    fun dispatchFullScreenNavigation() {
        stackOwner = stackOwnerMap[previousStackOwnerName]
    }

    fun lastFragment(): Fragment? {
        return if (stackOwner?.lastFragment() is Fragment)
            stackOwner?.lastFragment()
        else
            null
    }

}