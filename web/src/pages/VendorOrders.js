import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'
import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'

import './VendorOrders.css'

const VendorOrders = ({showHeader, hideHeader, showVendorHeader, isLoggedIn, userId}) => {
	useEffect(() => {
		hideHeader()
		showVendorHeader()
		return () => showHeader()
	}, [])
	
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

export default connect(mapStateToProps, {showHeader, hideHeader, showVendorHeader, hideVendorHeader})(VendorOrders)
