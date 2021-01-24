import React, { useEffect, useState } from 'react'
import history from '../util/history'
import { connect } from 'react-redux'

import ProductCard from '../components/ProductCard'
import wishlistService from '../services/wishlist'
import './Wishlist.css'

const Wishlist = (props) => {
	if(!props.isLoggedIn) {
		history.push('/signin')
		return
	}

	const [products, setProducts] = useState([])

	useEffect(() => {
		const getWishlist = async () => {
			const prods = await wishlistService.getWishlist(props.customerId)
			setProducts(prods)
		}

		getWishlist()
	}, [props.customerId])

	return(
		<div>
			<div className='list-title px-5 py-3' >Wishlist</div>
			<div className='list-container'>
				<div className='product-cards'>
					{products.length === 0 ?
						'You can add products to your wishlist by clicking the like button!' :
						products.map(p =>
							<div key={p.id} className='product-card-container'>
								<ProductCard name={p.name} price={p.price} imgUrl={p.imageUrl} productId={p.id}/>
							</div>
						)}
				</div>
			</div>
		</div>
	)
}

const mapStateToProps = (state) => {
	return {
		isLoggedIn: state.signIn.isLoggedIn,
		customerId: state.signIn.userId
	}
}

export default connect(mapStateToProps)(Wishlist)