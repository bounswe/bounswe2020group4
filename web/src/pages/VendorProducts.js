import React, { useState, useEffect } from 'react'
import { connect } from 'react-redux'
import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'
import history from '../util/history'

import VendorProductCard from '../components/VendorProductCard'
import './VendorProducts.css'


const VendorProducts = (props) => {

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

	const products = [
		{
			'productId': 1,
			'name': 'T-shirt',
			'imgUrl': '',
			'price': '50',
			'brand': 'Koton'
		},
		{
			'productId': 2,
			'name': 'Pants',
			'imgUrl': '',
			'price': '70',
			'brand': 'Koton'
		},
		{
			'productId': 2,
			'name': 'Pants',
			'imgUrl': '',
			'price': '70',
			'brand': 'Koton'
		}, 
		{
			'productId': 2,
			'name': 'Pants',
			'imgUrl': '',
			'price': '70',
			'brand': 'Koton'
		},
		{
			'productId': 2,
			'name': 'Pants',
			'imgUrl': '',
			'price': '70',
			'brand': 'Koton'
		},
		{
			'productId': 2,
			'name': 'Pants',
			'imgUrl': '',
			'price': '70',
			'brand': 'Koton'
		},
		{
			'productId': 2,
			'name': 'Pants',
			'imgUrl': '',
			'price': '70',
			'brand': 'Koton'
		}
	]

	return(
		<div>
			<div className='row' >
			{products.length != 0 ? products.map((p) =>
			
				<div className='col-sm-6 col-md-4 col-lg-3 mt-4' key={p.productId}>
					<div className="justify-content-center">
				<VendorProductCard
					name={p.name} 
					price={p.price} 
					imgUrl={p.imageUrl} 
					productId={p.productId} 
					brand={p.brand}
				/>
				</div>
				</div>
			
			) :
			<div className='container p-5'>
				<p>You haven't added any products.</p>
			</div>}
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

export default connect(mapStateToProps, {showHeader, hideHeader, showVendorHeader, hideVendorHeader})(VendorProducts)
