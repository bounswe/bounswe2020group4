import React, {useState} from 'react'
import { connect } from 'react-redux'

import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogContentText from '@material-ui/core/DialogContentText'
import DialogTitle from '@material-ui/core/DialogTitle'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import RatingStar from './RatingStar'

import commentService from '../services/comments'
import orderService from '../services/orders'

import DefaultProductImage from '../images/default-product-detail-image.png'

import './OrderDetails.css'

const ProductOrder = ({orderId, userId, userType, productId, imgUrl, name, brand, price, isDelivered, isPending, vendor, quantity, attributes, status}) => {
	const [open, setOpen] = useState(false)
	const [rating, setRating] = useState(null)
	const [comment, setComment] = useState('')


	const handleClickOpen = () => {
		setOpen(true)
	}
	const handleClose = () => {
		setOpen(false)
		setRating(null)
		setComment('')
	}
	const handleGiveFeedback = async () => {
		await commentService.giveFeedback(productId, userId, comment, rating)
		setOpen(false)
		setRating(null)
		setComment('')
	}
	const handleRatingChange = (e) => {
		setRating(e.target.value)
	}
	const handleCommentChange = (e) => {
		setComment(e.target.value)
	}

	const handleCancelOrder = async (e) => {
		const response = await orderService.updateOrderStatus(userType, userId, 'Canceled', orderId)
		if (response == 200) {
			setOpen(false)
			alert('Order is successfully canceled.')
			history.go(0)
		} else {
			alert('Something went wrong. Please try again later.')
		}
	}

	const customerProductButtons = () => {
		return (
			<div className="product-order-info">
				<button className="add-comment-button">Message Vendor</button>
				{isPending && <button className="add-comment-button">Cancel Order</button>}
				{isDelivered && <button className="add-comment-button" onClick={handleClickOpen}>Give Feedback</button>}
			</div>
		)
	}

	const vendorProductButtons = () => {
		return (
			<div className="product-order-info">
				{isPending && <button className="add-comment-button" onClick={handleCancelOrder}>Cancel Order</button>}
			</div>
		)
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
				<div>Vendor: {vendor}</div>
				<div>Status: {status}</div>
			</div>
			{userType == 'customer' ? customerProductButtons() : vendorProductButtons()}
			<Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
				<DialogTitle id="form-dialog-title">Give Feedback</DialogTitle>
				<DialogContent>
					<div className="dialog-rating">
						<DialogContentText>
              Rate this product: &nbsp;
						</DialogContentText>
						<RatingStar rating={rating} readOnly={false} precision={1} onChange={handleRatingChange} showLabel={false}/>
					</div>
					<DialogContentText>
						{(rating === null || rating == 3) && 'Add your comments on this product. Please describe your shopping experience, product quality etc.'}
						{rating >= 4 && 'We are glad that you liked this product! Please share with us your comments to inform everyone.'}
						{rating && rating < 3 && 'We are sorry that your expectations are not met :( Describe your experience by adding a comment so that we can improve ourselves for the next time.'}
					</DialogContentText>
					<TextField
						onChange={handleCommentChange}
						margin="dense"
						id="name"
						label="Comment"
						type="text"
						fullWidth
						multiline
					/>
				</DialogContent>
				<DialogActions>
					<Button onClick={handleClose} color="primary">
            Cancel
					</Button>
					<Button onClick={handleGiveFeedback} color="primary">
            Give Feedback
					</Button>
				</DialogActions>
			</Dialog>
		</div>
	)
}


const OrderDetails = ({orderId, address, products, isLoggedIn, userId, userType}) => {
	if(!isLoggedIn) {
		return <div>Please log in!</div>
	}

	return(
		<div className="order-details-container">
			<div className="order-details-top">
				<div>
					{products && products.map(p => <ProductOrder orderId={orderId} userId={userId} userType={userType} productId={p.productId} status={p.status} attributes={p.attributes} quantity={p.quantity} vendor={p.vendor.name} key={p.orderedProductId} name={p.name} brand={p.brand} price={p.price + '₺'} imgUrl={p.imageUrl} isPending={p.status === 'Pending'} isDelivered={p.status.startsWith('Delivered')}/>)}
				</div>
			</div>
			<div className="address-container"> Address: {address}</div>
		</div>
	)
}

const mapStateToProps = (state) => {
	return {
		isLoggedIn: state.signIn.isLoggedIn,
		userId: state.signIn.userId,
		userType: state.signIn.userType,
	}
}


export default connect(mapStateToProps)(OrderDetails)