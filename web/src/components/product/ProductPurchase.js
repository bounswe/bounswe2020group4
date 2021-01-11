import React, { useState } from 'react'
import { connect } from 'react-redux'
import Snackbar from '@material-ui/core/Snackbar'
import MuiAlert from '@material-ui/lab/Alert'
const Alert = (props) => {
	return <MuiAlert elevation={6} variant="filled" {...props} />
}


import cartService from '../../services/cart'
import './ProductPurchase.css'

const generatePossibleAttVals = (productInfos) => {
	let possibleValues = {}
	let info
	for(info of productInfos) {
		let attribute
		for(attribute of info.attributes) {
			if(possibleValues[attribute.name]) {
				if(!possibleValues[attribute.name].includes(attribute.value)) {
					possibleValues[attribute.name].push(attribute.value)
				}
			}
			else {
				possibleValues[attribute.name] = [attribute.value]
			}
		}
	}

	return possibleValues
}

const generateInitialAttVals = (possibleValues) => {
	const keys = Object.keys(possibleValues)
	let result = {}
	let key
	for(key of keys) {
		result[key] = possibleValues[key][0]
	}
	return result
}

const getStockValue = (productInfos, values) => {
	let stockElem
	const attrCount = Object.keys(values).length
	let count = 0
	for(stockElem of productInfos) {
		let attribute
		for(attribute of stockElem.attributes) {
			if(values[attribute.name] !== attribute.value) {
				count = 0
				break
			} else {
				count++
			}
			if(count === attrCount) {
				return stockElem.stockValue
			}
		}
	}
	return 0
}

const ProductPurchase = ({productId, price, originalPrice, productInfos, isLoggedIn, customerId }) => {
	const possibleValues = generatePossibleAttVals(productInfos)
	const initialValues = generateInitialAttVals(possibleValues)

	const [values, setValues] = useState(initialValues)
	const [amount, setAmount] = useState(1)
	const [open, setOpen] = useState(false)


	const handleValueChange = (e, opt) => {
		const valuesCopy = {...values}
		valuesCopy[opt] = e.target.value
		setValues(valuesCopy)
		setAmount(1)
	}

	const handleAmountChange = (e) => {
		if(e.target.value < amount) {
			setAmount(e.target.value)
		} else if(amount < getStockValue(productInfos, values)) {
			setAmount(e.target.value)
		}
	}

	const handleAddToCart = async () => {
		if(isLoggedIn) {
			const addToCartInfo = {
				'attributes': [],
				'quantity': amount
			}
			let key
			for(key of Object.keys(values)) {
				addToCartInfo.attributes.push({'name': key, 'value': values[key]})
			}
			cartService.addProductToCart(customerId, productId, addToCartInfo)
			setAmount(1)
			setOpen(true)
		}
	}
	const handleClose = (event, reason) => {
		if (reason === 'clickaway') {
			return
		}
		setOpen(false)
	}

	return(
		<div className='product-purchase-container'>
			<div className='product-purchase-options-container'>
				{Object.keys(possibleValues).map(opt =>
					<form key={opt}>
						<label className='product-purchase-label'>Choose {opt}:</label>
						<select onChange={(e) => handleValueChange(e, opt)} value={values[opt]}>
							{possibleValues[opt].map(v => <option key={v} value={v}>{v}</option>)}
						</select>
					</form>)}
				<div className='product-purchase-amount'>
					<label className='product-purchase-label'>Quantity:</label>
					<input type="number" min="1" onChange={handleAmountChange} value={amount}/>
				</div>
			</div>
			<div className='product-add-to-cart-container'>
				{originalPrice && originalPrice !== price &&
				<div className='product-price'>
					<s>{originalPrice} &#8378;</s>
				</div>}
				<div className='product-price'>
					{price} &#8378;
				</div>
				<button onClick={handleAddToCart}>Add to Cart</button>
				<Snackbar open={open} autoHideDuration={6000} onClose={handleClose} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
					<Alert onClose={handleClose} severity="success">
						You added this product to your cart!
					</Alert>
				</Snackbar>
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

export default connect(mapStateToProps)(ProductPurchase)

//