import React, { useState, useEffect } from 'react'
import { connect } from 'react-redux'
import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'
import history from '../util/history'

import VendorProductCard from '../components/VendorProductCard'
import './VendorProducts.css'

import vendorService from '../services/vendor.js'

const VendorProducts = (props) => {

	const [products, setProducts] = useState([])

	useEffect(() => {

		if(!props.isLoggedIn | props.userType != 'vendor') {
			history.push('/vendorsignin')
			return
		}

		const getProducts = async () => {
			const products = await vendorService.getProducts(props.userId)
			setProducts(products)
		}

		getProducts()
		props.hideHeader()
		props.showVendorHeader()
		return () => props.showHeader()
	}, [])



	return(
		<div>
			<div className='row' >
				{products.length != 0 ? products.map((p) =>

					<div className='col-sm-6 col-md-4 col-lg-3 mt-4' key={p.productId}>
						<div className="justify-content-center">
							<VendorProductCard
								vendorId={props.userId}
								name={p.name}
								price={p.price}
								originalPrice={p.originalPrice}
								imgUrl={p.imageUrl}
								productId={p.id}
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
		userId: state.signIn.userId,
		userType: state.signIn.userType

	}
}

export default connect(mapStateToProps, {showHeader, hideHeader, showVendorHeader, hideVendorHeader})(VendorProducts)
