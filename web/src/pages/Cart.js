import React, { useState, useEffect } from 'react'
import history from '../util/history'
import { connect } from 'react-redux'

import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button'
import Container from 'react-bootstrap/esm/Container'

import './Cart.css'
import './Checkout.css'
import cartService from '../services/cart'
import CartProduct from '../components/CartProduct'
import accountService from '../services/account.js'

const Cart = (props) => {

	const [products, setProducts] = useState([])
	const [totalPrice, setTotalPrice] = useState()
	const [discountedPrice, setDiscountedPrice] = useState()

	const redirectToCheckout = async function(e) {
		e.preventDefault()

		const profileInfo = await accountService.getProfileInfo('customer', props.customerId)
			
		if(!profileInfo){
			alert('Something went wrong, please refresh this page.')
			return
		}

		if(!profileInfo.firstName | !profileInfo.lastName | !profileInfo.phone){
			alert('You have to fill in your name, surname and phone number before proceeding to checkout, you will be redirected to your profile page.')
			history.push('/customerprofile')
			return
		}

		history.push('/checkout')
		return
	}

	useEffect(() => {

		if(!props.isLoggedIn | props.userType != 'customer') {
			history.push('/signin')
			return
		}

		const getCart = async () => {
			const cart = await cartService.getCart(props.customerId)
			setProducts(cart.products)
			setTotalPrice(cart.totalPrice)
			setDiscountedPrice(cart.discountedPrice)
		}

		getCart()
	}, [props.isLoggedIn, props.customerId, props.userType])


	return (
		<div>
			<div className="checkout-header-container px-5 py-3">Cart</div>
			<Container fluid>
				<Row>
					<Col>
						{products.length != 0 ? products.map((p) =>
							<Container key={p} className="cart-product">
								<CartProduct 
								name={p.name} 
								price={p.price} 
								originalPrice={p.originalPrice} 
								imgUrl={p.imageUrl} 
								productId={p.id} 
								vendorName={p.vendor.name} 
								brand={p.brand} 
								attributes={p.attributes} 
								amount={p.quantity}/>
							</Container>
						) :
						<div className='container p-5'>
							<p>Your cart is empty.</p>
						</div>}
					</Col>
					<Col sm={2} className="align-self-center text-center">
						<Container className="cart-total">
							<Card >
								<Card.Header className="header">Cart Total</Card.Header>
								{totalPrice==discountedPrice ? 
									<Card.Body>{discountedPrice}</Card.Body>: 
									<div>
									<Card.Body style={{textDecorationLine: 'line-through'}}>{totalPrice}</Card.Body>
									<Card.Body>{discountedPrice}</Card.Body>
									</div>
								}
								<Button className="checkout-button" onClick={redirectToCheckout}>Checkout</Button>
							</Card>
						</Container>
					</Col>
				</Row>
			</Container>
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

export default connect(mapStateToProps)(Cart)