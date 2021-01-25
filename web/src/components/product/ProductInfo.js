import React, { useState } from 'react'

import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogContentText from '@material-ui/core/DialogContentText'
import DialogTitle from '@material-ui/core/DialogTitle'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'

import reportService from '../../services/report'

import WishlistButton from './WishlistButton'
import RatingStar from '../RatingStar'

import FreeShippingImage from '../../images/free-shipping.png'

import './ProductInfo.css'


const ProductInfo = ({ productId, name, brand, price, rating, vendor, vendorRating}) => {
	const [open, setOpen] = useState(false)
	const [message, setMessage] = useState('')

	const handleClickOpen = () => {
		setOpen(true)
	}
	const handleClose = () => {
		setOpen(false)
		setMessage('')
	}
	const handleMessageChange = (e) => {
		setMessage(e.target.value)
	}
	const handleReport = async () => {
		await reportService.reportProduct(productId, message)
		setMessage('')
		setOpen(false)
	}

	return(
		<div className='product-info-container'>
			<div>
				<h2 className='product-name' >{name}</h2>
				<div className='wishlist-button-container' >
					<WishlistButton productId={productId} />
				</div>
			</div>
			<h3 className='product-brand'>{brand}</h3>
			<RatingStar rating={rating} />
			<div className='product-price'>{price}&#8378;</div>
			<div>
				<div className='shipping-container'>
					<img src={FreeShippingImage} alt='free shipping'/>
				</div>
				<div className='company-detail-container'>
					<div>Seller: {vendor}</div>
					<RatingStar rating={vendorRating}/>
					<button className="report-product-button" onClick={handleClickOpen}>Report Product</button>
				</div>
			</div>
			<Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
				<DialogTitle id="form-dialog-title">Report Product</DialogTitle>
				<DialogContent>
					<DialogContentText> State the problem with this product:
					</DialogContentText>
					<TextField
						onChange={handleMessageChange}
						margin="dense"
						id="name"
						label="Comment"
						type="text"
						fullWidth
						multiline
					/>
				</DialogContent>
				<DialogActions>
					<Button onClick={handleClose} color="primary"> Cancel
					</Button>
					<Button onClick={handleReport} color="primary"> Report product
					</Button>
				</DialogActions>
			</Dialog>
		</div>
	)
}

export default ProductInfo