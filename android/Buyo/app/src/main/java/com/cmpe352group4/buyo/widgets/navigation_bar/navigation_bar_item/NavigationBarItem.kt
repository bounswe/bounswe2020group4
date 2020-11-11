package com.cmpe352group4.buyo.widgets.navigation_bar.navigation_bar_item

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.util.extensions.inflate

class NavigationBarItem : LinearLayout,
    INavigationBarItem {
    private lateinit var icon: ImageView
    private lateinit var title: TextView
    private var unselectedIcon: Drawable? = null
    private var selectedIcon: Drawable? = null


    constructor(context: Context?) : super(context) {
        init(context, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, null)
    }


    override fun setSelected() {
        selectedIcon?.let {
            icon.setImageDrawable(it)
        }
 //       title.setTextColor()
    }

    override fun setUnselected() {
        unselectedIcon?.let {
            icon.setImageDrawable(it)
        }
 //       title.setTextColor()
    }

    override fun init(context: Context?, attrs: AttributeSet?) {
        inflate(R.layout.layout_navigation_item, true)
        orientation = VERTICAL
        gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        initFields()
        setPadding(
            0,
            resources.getDimensionPixelSize(R.dimen.default_margin),
            0,
            resources.getDimensionPixelSize(R.dimen.minimum_margin)
        )
        context?.run {
            // setBackgroundColor(ContextCompat.getColor(context, R.color.bg_navigation_icon_color))
        }
    }


    override fun setIcon(vararg icons: Drawable?) {
        unselectedIcon = icons[0]
        selectedIcon = icons[1]
        icon.setImageDrawable(icons[0])
    }

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setClickListener(listener: OnClickListener) {
        setOnClickListener(listener)
    }

    override fun confirmClick() {
        performClick()
    }

    private fun initFields() {
        icon = findViewById(R.id.icon)
        title = findViewById(R.id.title)
    }
}