import React, { useState } from 'react'
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

const ProductPurchase = ({price, originalPrice, productInfos}) => {
	const possibleValues = generatePossibleAttVals(productInfos)
	const initialValues = generateInitialAttVals(possibleValues)

	const [values, setValues] = useState(initialValues)
	const [amount, setAmount] = useState(1)

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

	const handleAddToCart = () => {
		//TODO
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
					<label className='product-purchase-label'>Amount:</label>
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
			</div>
		</div>
	)
}

export default ProductPurchase