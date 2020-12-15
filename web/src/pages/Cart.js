import React from 'react'
import history from '../util/history'
import { connect } from 'react-redux'

import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button'

import DefaultProductImg from '../images/default-product-image.png'

import './Cart.css'
import './Checkout.css'
import CartProduct from '../components/CartProduct'
import Container from 'react-bootstrap/esm/Container'

const Cart = (props) => {

    if(!props.isLoggedIn) {
        history.push('/signin')
    }

    const redirectToCheckout = function(e) {
        e.preventDefault()
        history.push("/checkout")
    }

    const cartTotal = "23.99"

    const products = [
        {
            name : "T-shirt",
            price: "24.99",
            imgUrl: "",
            productId: "10001",
            vendorName: "ayse",
            brand: "LC Waikiki",
            size: "M",
            amount: "2"
        },
        {
            name : "T-shirt",
            price: "24.99",
            imgUrl: "",
            productId: "10001",
            vendorName: "ayse",
            brand: "LC Waikiki",
            size: "M",
            amount: "2"
        }
    ]

    return (
    <div>
        <div className="checkout-header-container px-5 py-3">Cart</div>
        <Container fluid>
            <Row>
                <Col>
                {products.map(p =>
                    <Container className="cart-product">
                        <CartProduct name={p.name} price={p.price} imgUrl={p.imgUrl | DefaultProductImg} productId={p.productId} vendorName={p.vendorName} brand={p.brand} size={p.size} amount={p.amount}/>
                    </Container>
                )}
                </Col>
                <Col sm={2} className="align-self-center text-center">
                    <Container className="cart-total">
                        <Card >
                            <Card.Header className="header">Cart Total</Card.Header>
                            <Card.Body>{cartTotal}</Card.Body>
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
      customerId: state.signIn.userId
    }
  }
  
  export default connect(mapStateToProps)(Cart)