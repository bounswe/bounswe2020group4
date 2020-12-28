package com.cmpe352group4.buyo.base.fragment_ops

import androidx.fragment.app.Fragment

interface ViewStackOwner {
    fun consumePopBackStackEvent(): Boolean
    fun resetToInitialState()
    fun getStackCount(): Int
    fun lastFragment(): Fragment?
}