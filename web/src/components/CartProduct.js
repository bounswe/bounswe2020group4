import { Link } from 'react-router-dom'
import React, { useEffect, useState } from 'react'
import history from '../util/history'
import { connect } from 'react-redux'

import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Card from 'react-bootstrap/Card'
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';

import './CartProduct.css'
import cartService from '../services/cart'

const CartProduct = (props) => {
//, {name, price, imgUrl, productId, vendorName, brand, amount, attributes, originalPrice}
	const handleClick = async function(e) {
		e.preventDefault()
		const productInfo = {
			"attributes": props.attributes
		}
		console.log(productInfo)
		const response = await cartService.removeFromCart(props.customerId, props.productId, productInfo)
		if(response != 200){
			alert('Something went wrong, try again.')
		}else{
			history.go(0)
			return
		}
	}

	return (
		<div className='container'>
			<div className='card cp-container'>
				<div className="card-header">
					Vendor: {props.vendorName}
				</div>
				<div className='row'>
					<div className='col'>
					<div className='container-fluid p-0 card-img-container'>
						<Link to={`/product/${props.productId}`} >
							<img className="card-img" src={props.imgUrl}></img>
						</Link>
					</div>
					</div>
				
					<Col className="align-self-center">
						<Card.Body >
							<Row><Card.Title className='product-name'>{props.name}</Card.Title></Row>
							<Row><Card.Title className='product-brand'>{props.brand}</Card.Title></Row>
							{ props.attributes.map(attr =>
								<Row key={attr.name}><Card.Text className='product-attr'>{attr.value}</Card.Text></Row>
								) } 
						</Card.Body>
					</Col>
					<Col className="align-self-center text-center">
						<Card.Body >
							<Card.Title className='product-quantity'>Quantity</Card.Title>
							<Card.Title >{props.amount}</Card.Title>
						</Card.Body>
					</Col>
					<Col className="align-self-center text-center">
						<Card.Body >
							{props.originalPrice==props.price ? 
							<Card.Title>{props.originalPrice}</Card.Title> : 
							<div>
							<Card.Title style={{textDecorationLine: 'line-through'}}>{props.originalPrice}</Card.Title>
							<Card.Title className='product-discount'>{props.price}</Card.Title>
							</div>
							}
							
						</Card.Body>
					</Col>
					<Col className="align-self-center text-center">
						<IconButton onClick={handleClick}>
							<DeleteIcon/>
						</IconButton>
					</Col>
			
				</div>
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

export default connect(mapStateToProps)(CartProduct)