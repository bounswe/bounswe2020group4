import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom'
import queryString from 'query-string'
import history from '../util/history'
import FormControlLabel from '@material-ui/core/FormControlLabel'
import Checkbox from '@material-ui/core/Checkbox'


import ProductCard from '../components/ProductCard'
import productService from '../services/products'

import 'bootstrap/dist/js/bootstrap.bundle'
import './CategoryProducts.css'

const FilterCheckBox = ({label}) => {
	return(
		<FormControlLabel
			value="end"
			control={<Checkbox color="primary" />}
			label={'  ' + label}
			labelPlacement="end"
		/>
	)
}

const CategoryProducts = () => {
	const location = useLocation()
	const params = queryString.parse(location.search)
	const [sortText, setSortText] = useState('Lowest Price')
	const [products, setProducts] = useState([])

	useEffect(() => {
		const getProducts = async () => {
			if(params.categories) {
				const products = await productService
					.getCategoryProducts(params.categories)
				setProducts(products)
			} else if(params.search) {
				const products = await productService
					.searchProducts(params.search)
				setProducts(products)
			} else {
				history.push('/')
			}
		}

		getProducts()
	}, [params.categories, params.search])

	const handleDropdownClick = (e) => {
		console.log(e.target.text)
		setSortText(e.target.text)
	}

	return(
		<div className='category-products-container'>
			<div className='category-title px-5 py-3' >
				{params.categories ? 'Category: ' + params.categories : 'Search: ' + params.search}
				<div className="sort-container">
					<div className="sortby-text">Sort by</div>
					<div className="dropdown show">
						<button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							{sortText}
						</button>
						<div className="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
							<a className="dropdown-item" href="#" onClick={handleDropdownClick}>Lowest Price</a>
							<a className="dropdown-item" href="#" onClick={handleDropdownClick}>Highest Price</a>
							<a className="dropdown-item" href="#" onClick={handleDropdownClick}>Best Selling</a>
							<a className="dropdown-item" href="#" onClick={handleDropdownClick}>Latest Arrival Time</a>
							<a className="dropdown-item" href="#" onClick={handleDropdownClick}>Number of Comments</a>
							<a className="dropdown-item" href="#" onClick={handleDropdownClick}>Rating</a>
							<a className="dropdown-item" href="#" onClick={handleDropdownClick}>Average Customer Review</a>
						</div>
					</div>
				</div>
			</div>
			<div className='products-container'>
				<div className='filters'>
					<h2>Brand</h2>
					<FilterCheckBox label="LCW" />
					<FilterCheckBox label="Koton" />
					<FilterCheckBox label="Adidas" />
					<h2>Size</h2>
					<FilterCheckBox label="XS"/>
					<FilterCheckBox label="S"/>
					<FilterCheckBox label="M"/>
					<FilterCheckBox label="L"/>
					<FilterCheckBox label="XL"/>
					<h2>Color</h2>
					<FilterCheckBox label="Blue"/>
					<FilterCheckBox label="Black"/>
					<FilterCheckBox label="White"/>
				</div>
				<div className='product-cards'>
					{products.map(p =>
						<div key={p.id} className='product-card-container'>
							<ProductCard name={p.name} price={p.price} imgUrl={p.imageUrl} productId={p.id}/>
						</div>
					)}
				</div>
			</div>
		</div>
	)
}

export default CategoryProducts