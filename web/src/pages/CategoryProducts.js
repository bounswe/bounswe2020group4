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

const FilterCheckBox = ({label, checked, handleFilterCheckbox}) => {
	return(
		<FormControlLabel
			checked={checked}
			value="end"
			control={<Checkbox onChange={handleFilterCheckbox} color="primary" />}
			label={'  ' + label}
			labelPlacement="end"
		/>
	)
}

const CategoryProducts = () => {
	const location = useLocation()
	const params = queryString.parse(location.search)

	// Default sorting factor and type
	if(!params.sortingFactor) {
		params.sortingFactor = 'price'
	}
	if(!params.sortingType) {
		params.sortingType = 'ascending'
	}

	const [sortText, setSortText] = useState(params.sortingFactor)
	const [sortTypeText, setSortTypeText] = useState(params.sortingType)
	const [products, setProducts] = useState([])
	const [filterCriterias, setFilterCriterias] = useState([])
	const [newURL, setNewURL] = useState('/')

	useEffect(() => {
		const getProducts = async () => {
			if(params.categories || params.search) {
				const response = await productService.getProducts(params)
				setProducts(response.productList)
				setFilterCriterias(response.filterCriterias)
			} else {
				history.push('/')
			}
		}

		getProducts()
	}, [params.categories, params.search])

	const handleSortingFactorDropdownClick = (e) => {
		setSortText(e.target.text)
		params.sortingFactor = e.target.text.toLowerCase()
		setNewURL('/products?' + queryString.stringify(params))
	}
	const handleSortingTypeDropdownClick = (e) => {
		setSortTypeText(e.target.text)
		params.sortingType = e.target.text.toLowerCase()
		setNewURL('/products?' + queryString.stringify(params))
	}
	const handleFilterCheckbox = (e, filterName, filterValue) => {
		if(e.target.checked) {
			//TODO
		}
		else {
			//TODO
		}
	}

	return(
		<div className='category-products-container'>
			<div className='category-title px-5 py-3' >
				{params.categories ? 'Category: ' + params.categories : 'Search: ' + params.search}
				<div className="sort-container">
					<div className="sortby-text">Sort by</div>
					<div className="dropdown show type">
						<button className="btn btn-secondary dropdown-toggle" type="button" id="typeDropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							{sortTypeText}
						</button>
						<div className="dropdown-menu dropdown-menu-right" aria-labelledby="typeDropdownMenuButton">
							<a className="dropdown-item" href={newURL} onClick={handleSortingTypeDropdownClick}>ascending</a>
							<a className="dropdown-item" href={newURL} onClick={handleSortingTypeDropdownClick}>descending</a>
						</div>
					</div>
					<div className="dropdown show">
						<button className="btn btn-secondary dropdown-toggle" type="button" id="factorDropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							{sortText}
						</button>
						<div className="dropdown-menu dropdown-menu-right" aria-labelledby="factorDropdownMenuButton">
							<a className="dropdown-item" href={newURL} onClick={handleSortingFactorDropdownClick}>price</a>
							<a className="dropdown-item" href={newURL} onClick={handleSortingFactorDropdownClick}>rating</a>
							<a className="dropdown-item" href={newURL} onClick={handleSortingFactorDropdownClick}>name</a>
						</div>
					</div>
				</div>
			</div>
			<div className='products-container'>
				<div className='filters'>
					{filterCriterias.map(f => (
						<div key={f.name} className='filter-container'>
							<h2>{f.displayName}</h2>
							{f.possibleValues.map(v => <FilterCheckBox key={v} checked={params[f.name] && params[f.name] === v} label={v} />)}
						</div>
					))}
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