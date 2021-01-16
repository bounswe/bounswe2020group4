import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'

import './VendorOrders.css'

const VendorOrders = ({isLoggedIn, userId}) => {
    return (
        <div>Orders (WIP)</div>
    )
}

const mapStateToProps = (state) => {
	return {
		isLoggedIn: state.signIn.isLoggedIn,
		userId: state.signIn.userId
	}
}

export default connect(mapStateToProps)(VendorOrders)
