import React, { useState, useEffect } from 'react'
import { connect } from 'react-redux'
import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'
import history from '../util/history'


const VendorProducts = (props) => {

	useEffect(() => {

		if(!props.isLoggedIn | props.userType != 'vendor') {
			history.push('/vendorsignin')
			return
		}

		props.hideHeader()
		props.showVendorHeader()
		return () => props.showHeader()
	}, [])

	return(
		<div>

		</div>
	)
}

const mapStateToProps = (state) => {
	return {
		isLoggedIn: state.signIn.isLoggedIn,
		customerId: state.signIn.userId,
		userType: state.signIn.userType

	}
}

export default connect(mapStateToProps, {showHeader, hideHeader, showVendorHeader, hideVendorHeader})(VendorProducts)
