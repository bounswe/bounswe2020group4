import React, {useState} from 'react'

import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogContentText from '@material-ui/core/DialogContentText'
import DialogTitle from '@material-ui/core/DialogTitle'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import RatingStar from './RatingStar'

import DefaultProductImage from '../images/default-product-detail-image.png'

import './OrderDetails.css'

const ProductOrder = ({imgUrl, name, brand, price, isDelivered}) => {
	const [open, setOpen] = useState(false)
	const [rating, setRating] = useState(null)

	const handleClickOpen = () => {
		setOpen(true)
	}
	const handleClose = () => {
		setOpen(false)
		setRating(null)
	}
	const handleGiveFeedback = () => {
		setOpen(false)
	}
	const handleRatingChange = (e) => {
		setRating(e.target.value)
	}

	return(
		<div className="product-order">
			<img className="product-image" src={imgUrl || DefaultProductImage} alt='product'/>
			<div className="product-order-info">
				<div>{name}</div>
				<div>{brand}</div>
				<div>{price}</div>
			</div>
			{isDelivered && <button className="add-comment-button" onClick={handleClickOpen}>Give Feedback</button>}
			<button className="add-comment-button">Message Vendor</button>
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


const OrderDetails = ({isDelivered}) => {
	return(
		<div className="order-details-container">
			<div className="order-details-top">
				<div>
					<ProductOrder name="kazak" brand="mavi" price="200 tl" isDelivered={isDelivered}/>
					<ProductOrder name="çorap" brand="lcw" price="10 tl" isDelivered={isDelivered}/>
				</div>
				<div className="order-button-container">
					{!isDelivered ? <button className="order-page-button">Cancel Order</button> : null}
					{isDelivered && <button className="order-page-button" >Return Order</button>}
				</div>
			</div>
			<div className="address-container"> Address: Rumeli Hisarı, Hisar Üstü Nispetiye Cd No:7, 34342 Sarıyer/İstanbul
			</div>
		</div>
	)
}

export default OrderDetails