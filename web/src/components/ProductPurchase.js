import React, { useState } from 'react'
import './ProductPurchase.css'

const ProductPurchase = ({price, originalPrice, sizes, colors, stockValue}) => {
	const [size, setSize] = useState(sizes ? sizes[0] : null)
	const [color, setColor] = useState(colors ? colors[0] : null)
	const [amount, setAmount] = useState(1)

	const handleSizeChange = (e) => {
		setSize(e.target.value)
		setAmount(1)
	}
	const handleColorChange = (e) => {
		setColor(e.target.value)
		setAmount(1)
	}

	const handleAmountChange = (e) => {
		if(sizes) {
			if(e.target.value < amount) {
				setAmount(e.target.value)
			}
			if(amount < stockValue[size + '-' + color]) {
				setAmount(e.target.value)
			}
		}
	}

	const handleAddToCart = () => {
		//TODO
	}

	return(
		<div className='product-purchase-container'>
			<div className='product-purchase-options-container'>
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
