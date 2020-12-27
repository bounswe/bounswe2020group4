import { Link } from 'react-router-dom'
import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'

import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Card from 'react-bootstrap/Card'

import history from '../util/history'
import TrashIcon from '../images/trash-icon.png'
import './CartProduct.css'

const CartProduct = ({name, price, imgUrl, productId, vendorName, brand, amount, attributes, originalPrice}) => {

	return (
		<Container fluid>
			<Card className='cp-container'>
				<Card.Header className="header">
					<Card.Text>Vendor: {vendorName}</Card.Text>
				</Card.Header>
				<Row>
					<Col>
						<Link to={`/product/${productId}`} >
							<Card.Img className="cart-img" src={imgUrl}></Card.Img>
						</Link>
					</Col>
					<Col className="align-self-center text-center">
						<Card.Body >
							<Row><Card.Title>{name}</Card.Title></Row>
							<Row><Card.Title>{brand}</Card.Title></Row>
							{attributes.map(attr =>
								<Row key={attr.name}><Card.Text>{attr.value}</Card.Text></Row>
								)}
						</Card.Body>
					</Col>
					<Col className="align-self-center text-center">
						<Card.Body >
							<Card.Title>Quantity:</Card.Title>
							<Card.Title>{amount}</Card.Title>
						</Card.Body>
					</Col>
					<Col className="align-self-center text-center">
						<Card.Body >
							{originalPrice==price ? 
							<Card.Title>Price: {originalPrice}</Card.Title> : 
							<div>
							<Card.Title style={{textDecorationLine: 'line-through'}}>{originalPrice}</Card.Title>
							<Card.Title>{price}</Card.Title>
							</div>
							}
							
						</Card.Body>
					</Col>
					<Col className="align-self-center text-center">
						<img className="trash-icon" src={TrashIcon}></img>
					</Col>
				</Row>
			</Card>
		</Container>
	)
}

export default CartProduct