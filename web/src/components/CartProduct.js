import React from 'react'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Image from 'react-bootstrap/Image'
import Card from 'react-bootstrap/Card'
import { Link } from 'react-router-dom'
import TrashIcon from '../images/trash-icon.png'

import './CartProduct.css'

const CartProduct = ({name, price, imgUrl, productId, vendorName, brand, size, amount}) => {
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
                
                <Col  className="align-self-center text-center">
                    <Card.Body >
                        <Row ><Card.Title>Product: {name}</Card.Title></Row>
                        <Row><Card.Text>Brand: {brand}</Card.Text></Row>
                        <Row><Card.Text>Size: {size}</Card.Text></Row>
                    </Card.Body>
                </Col>
                <Col className="align-self-center text-center">
                    <Card.Body >
                    <Card.Title>Amount: {amount}</Card.Title>
                    </Card.Body>
                </Col>
                <Col className="align-self-center text-center">
                    <Card.Body >
                        <Card.Title>Price: {price}</Card.Title>
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