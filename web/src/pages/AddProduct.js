import React, { useState, useEffect } from 'react'
import { connect } from 'react-redux'
import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'
import history from '../util/history'

import './AddProduct.css'

const AddProduct= (props) => {

	useEffect(() => {
		
		//TODO: uncomment this
		//if(!props.isLoggedIn | props.userType != 'vendor') {
		//	history.push('/vendorsignin')
		//	return
		//}

		props.hideHeader()
		props.showVendorHeader()
		return () => props.showHeader()
	}, [])

	return (
		<div className='container'>
			<div className='row mt-3'>
				<p className='h2'>Add a new product</p>
			</div>
			<div className='container-fluid mt-3 container-main rounded'>
				<p className='h3 header-info'>1. Choose a category</p>
			</div>
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

export default connect(mapStateToProps, {showHeader, hideHeader, showVendorHeader, hideVendorHeader})(AddProduct)
