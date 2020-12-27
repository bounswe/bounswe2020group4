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
	let result = []
	let key
	for(key of keys) {
		const dict = {}
		dict[key] = possibleValues[key][0]
		result.push(dict)
	}
	return result
}

const ProductPurchase = ({price, originalPrice, productInfos}) => {
	const possibleValues = generatePossibleAttVals(productInfos)
	const initialValues = generateInitialAttVals(possibleValues)

	const [values, setValues] = useState(initialValues)
	const [amount, setAmount] = useState(1)

	const handleValueChange = (e, opt) => {
		const d = {}
		d[opt] = e.target.value
		setValues(values.map(v => v[opt] ? d : v))
		setAmount(1)
	}

	const handleAmountChange = (e) => {
		/*if(sizes) {
			if(e.target.value < amount) {
				setAmount(e.target.value)
			}
			if(amount < stockValue[size + '-' + color]) {
				setAmount(e.target.value)
			}
		}*/
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
/*
{sizes &&
				<form>
					<label className='product-purchase-label'>Choose size:</label>
					<select onChange={handleSizeChange} value={size}>
						{sizes.map(s => <option key={s} value={s}>{s}</option>)}
					</select>
				</form>}
				{colors &&
				<form>
					<label className='product-purchase-label'>Choose color:</label>
					<select onChange={handleColorChange} value={color}>
						{colors.map(c => <option key={c} value={c}>{c}</option>)}
					</select>
				</form>}
				*/