package com.cmpe352group4.buyo.base.fragment_ops

class FragmentTransactionContainer {
    var transactionType: TransactionType? = null
    var isBackStack: Boolean = false
    var backStackTag: String? = null
    var layoutId: Int = 0
    var isHasAnimation: Boolean = false

    class Builder {
        private var transactionType: TransactionType
        private var isBackStack: Boolean = false
        private var backStackTag: String?
        private var layoutId: Int = 0
        private var hasAnimation: Boolean = false

        init {
            transactionType = TransactionType.Add
            isBackStack = false
            backStackTag = null
            layoutId = -1
            hasAnimation = false
        }

        fun setTransactionType(type: TransactionType): Builder {
            this.transactionType = type
            return this
        }

        fun setBackState(isBackStack: Boolean, backStackTag: String?): Builder {
            this.backStackTag = backStackTag
            this.isBackStack = isBackStack
            return this
        }

        fun setLayoutId(layoutId: Int): Builder {
            this.layoutId = layoutId
            return this
        }

        fun setHasAnimation(hasAnimation: Boolean): Builder {
            this.hasAnimation = hasAnimation
            return this
        }

        fun build(): FragmentTransactionContainer {
            val container = FragmentTransactionContainer()
            container.let {
                it.isBackStack = isBackStack
                it.backStackTag = backStackTag
                it.isHasAnimation = hasAnimation
                it.layoutId = layoutId
                it.transactionType = transactionType
            }
            return container
        }
    }
}