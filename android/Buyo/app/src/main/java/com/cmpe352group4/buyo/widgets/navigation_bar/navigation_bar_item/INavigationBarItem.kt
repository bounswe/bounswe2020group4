package com.cmpe352group4.buyo.widgets.navigation_bar.navigation_bar_item

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View

interface INavigationBarItem {
    fun setSelected()

    fun setUnselected()

   // fun setIcon(vararg icons: Drawable?)

    fun setTitle(title: String)

    fun init(context: Context?, attrs: AttributeSet?)

    fun setClickListener(listener: View.OnClickListener)
    fun confirmClick()

}