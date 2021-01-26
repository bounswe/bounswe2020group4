import React, {useState} from 'react'
import { connect } from 'react-redux'

import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogContentText from '@material-ui/core/DialogContentText'
import DialogTitle from '@material-ui/core/DialogTitle'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'

import orderService from '../services/orders'
import messageService from '../services/messages'

import DefaultProductImage from '../images/default-product-detail-image.png'

import './OrderDetails.css'

const VendorProductOrder = ({orderId, userId, userType, productId, imgUrl, name, brand, price, isDelivered, isPending, customerId, quantity, attributes, status}) => {
	const [messageOpen, setMessageOpen] = useState(false)
	const [message, setMessage] = useState('')

	// Message customer dialog button functions
	const handleMessageCustomer = () => {
		setMessageOpen(true)
	}
	const handleMessageChange = (e) => {
		setMessage(e.target.value)
	}
	const handleMessageClose = () => {
		setMessageOpen(false)
		setMessage('')
	}
	const handleSendMessage = () => {
		messageService.sendSingleMessage(userId, userType, customerId, 'customer', message)
		setMessageOpen(false)
		setMessage('')
	}

	const handleCancelProductOrder = async () => {
		const response = await orderService.updateProductOrderStatus(userType, userId, 'Cancelled', orderId, productId)
		if (response == 200) {
			alert('Order is successfully canceled.')
			history.go(0)
		} else {
			alert('Something went wrong. Please try again later.')
		}
	}

	return(
		<div className="product-order">
			<a className="product-image-container" href={`/product/${productId}`}><img className="product-image" src={imgUrl || DefaultProductImage} alt='product'/></a>
			<div className="product-order-info">
				<div>{name}</div>
				<div>{brand}</div>
				<div>{price}</div>
			</div>
			<div className="product-order-info">
				{attributes.map(a => <div key={a.name}>{a.value}</div>)}
				<div>Quantity: {quantity}</div>
			</div>
			<div className="product-order-info">
				<div>Status: {status}</div>
			</div>
			<div className="product-order-info">
				<button className="add-comment-button" onClick={handleMessageCustomer}>Message Customer</button>
				{isPending && <button className="add-comment-button" onClick={handleCancelProductOrder}>Cancel Order</button>}
			</div>
			<Dialog open={messageOpen} onClose={handleMessageClose} aria-labelledby="form-dialog-message">
				<DialogTitle id="form-dialog-message">Message Customer</DialogTitle>
				<DialogContent>
					<DialogContentText> Send your message to the customer:
					</DialogContentText>
					<TextField
						onChange={handleMessageChange}
						margin="dense"
						id="name"
						label="Your Message"
						type="text"
						fullWidth
						multiline
					/>
				</DialogContent>
				<DialogActions>
					<Button onClick={handleMessageClose} color="primary"> Cancel
					</Button>
					<Button onClick={handleSendMessage} color="primary"> Send message
					</Button>
				</DialogActions>
			</Dialog>
		</div>
	)
}

const VendorOrderDetails = ({orderId, address, products, userId, userType}) => {
	return(
		<div className="order-details-container">
			<div className="order-details-top">
				<div>
					{products && products.map(p => <VendorProductOrder orderId={orderId} userId={userId} userType={userType} productId={p.productId} status={p.status} attributes={p.attributes} quantity={p.quantity} customerId={p.customerId} key={p.orderedProductId} name={p.name} brand={p.brand} price={p.price + '₺'} imgUrl={p.imageUrl} isPending={p.status === 'Pending'} isDelivered={p.status.startsWith('Delivered')}/>)}
				</div>
			</div>
			<div className="address-container"> Address: {address}</div>
		</div>
	)
}

const mapStateToProps = (state) => {
	return {
		userId: state.signIn.userId,
		userType: state.signIn.userType,
	}
}

export default connect(mapStateToProps)(VendorOrderDetails)
