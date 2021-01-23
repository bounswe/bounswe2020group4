import React, { useState, useEffect } from 'react'
import { connect } from 'react-redux'
import { useParams } from 'react-router-dom'
import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'
import { WithContext as ReactTags } from 'react-tag-input';
import history from '../util/history'

import './AddProduct.css'
import {getCategories} from '../services/category'

const UpdateProduct = (props) => {

	const id = useParams().id

	const [categories, setCategories] = useState([])
	const [path, setPath] = useState('')
	const [categorySelected, setCategorySelected] = useState(false)
	const [productName, setProductName] = useState('')
	const [description, setDescription] = useState('')
	const [brand, setBrand] = useState('')
	const [attributes, setAttributes] = useState([])
	const [attributeSelected, setAttributeSelected] = useState(false)
	const [possibleValues, setPossibleValues] = useState({})
	const [rerender, setRerender] = useState(false)
	const [possibleValuesEntered, setPossibleValuesEntered] = useState(false)
	const [productInfos, setProductInfos] = useState([])
	const [stockEntered, setStockEntered] = useState(false)
	const [price, setPrice] = useState()
	const [discountedPrice, setDiscountedPrice] = useState()


	useEffect(() => {
		
		//TODO: uncomment this later
		//if(!props.isLoggedIn | props.userType != 'vendor') {
		//	history.push('/vendorsignin')
		//	return
		//}

		const retrieveCategories = async () => {
			const categories = await getCategories()
			setCategories(categories)
		}

		retrieveCategories()
		props.hideHeader()
		props.showVendorHeader()
		return () => props.showHeader()
	}, [props.isLoggedIn, props.customerId])

	const renderSubcategories = (currPath, category, subcategories) => {

		return ( 
			subcategories.length == 0 ? 
			<div></div>
			:
			<div id={category.name+'-sub'} className='collapse pl-5' aria-labelledby={category.name} data-parent={'#'+ category.name + '-accordion'}>
				{subcategories.map((sub) =>
					<div key={sub.name} id={sub.name+'-accordion'}>
						<div id={sub.name}>
							<h5 className="mb-0">
								<button className="btn btn-link collapsed" onClick={() => createPath(currPath, sub.name)} data-toggle="collapse" data-target={'#'+sub.name+'-sub'} aria-expanded="false" aria-controls={sub.name+'-sub'}>
									{sub.name}
								</button>
							</h5>
						</div>
						<div>
							{renderSubcategories(currPath+','+sub.name, sub, sub.subcategories)}
						</div>
					</div>
				)}
			</div>)
	}

	const createPath = (parentPath, category) => {
		const cats = parentPath.split(',')
		if(cats[cats.length-1] !== category)
			setPath(parentPath+','+category)
	}

	const handleCategoryButton = () => {
		if(path){
			setCategorySelected(true)
		} else {
			alert('You must select a category first.')
		}
	}

	const handleAttributeButton = () => {
		if(!productName | !brand | !description | !price | !discountedPrice){
			alert('You must fill out name, brand, description, price and discounted price fields.')
			return
		}
		if(attributes.length){
			setAttributeSelected(true)
			setPossibleValuesEntered(false)
			setRerender(!rerender)
		} else {
			setAttributeSelected(false)
			alert('You must write at least one criteria first.')
		}
	}

	const handleAttributeAdd = (attribute) => {
		attributes.push(attribute)
		setAttributes(attributes)
	}

	const handleAttributeDelete = (i) => {
		var removed = attributes.splice(i, 1)
		setAttributes(attributes)
		delete possibleValues[removed[0]["text"]]
	}

	const handlePossibleValueChange = function(e, attr) {
		if(e.target.value){
			possibleValues[attr] = e.target.value.split(',')
		} else{
			possibleValues[attr] = ''
		}
		setPossibleValues(possibleValues)
	}

	const handlePossibleValuesButton = function() {
		var comb_length = 1
		for(var attr in attributes){
			var temp = attributes[attr]["text"]
			if(!possibleValues[temp]) {
				alert('You have to enter possible values for every criteria.')
				return
			} else if(possibleValues[temp].length == 0){
				alert('You have to enter possible values for every criteria.')
				return
			}
			comb_length *= possibleValues[temp].length
		}
		setPossibleValuesEntered(true)
		setProductInfos(Array(comb_length))
		setRerender(!rerender)
	}

	const handleValueChange = function(e, comb, index){
		comb['stockValue'] = e.target.value
		productInfos.splice(index,1,comb)
		setProductInfos(productInfos)
	}

	const handleStockButton = function(len){
		if(productInfos.includes(undefined)){
			alert('Enter stock information for all combinations.')
		} else{
			setStockEntered(true)
		}
	}

	function getCombinations(options, optionIndex, results, current) {
		var allKeys = Object.keys(options);
		var optionKey = allKeys[optionIndex];
	
		var vals = options[optionKey];
		for (var i = 0; i < vals.length; i++) {
			current[optionKey] = vals[i];
			if (optionIndex + 1 < allKeys.length) {
				getCombinations(options, optionIndex + 1, results, current);
			} else {
				var res = JSON.parse(JSON.stringify(current));
				results.push(res);
			}
		}
		return results;
	}

	function getListOfValues(dictionary){
		return Object.keys(dictionary).map(function(key){
			return dictionary[key];
		});
	}

	function renderCombinations(){
		var results = getCombinations(possibleValues, 0, [], {});
		return(
			<div>
				{results.map((result) => 
					<div className='row mb-2' key={result}>
						{getListOfValues(result).map((comb) =>
							<div key={comb} className='col'>
								{comb}
							</div>	
						)}
						<div className='col-1'>
							<input type='number' min='0' className='form-control form-control-md text-left' id={results.indexOf(result)} onChange={(e) => handleValueChange(e, result, results.indexOf(result))}/>
						</div>
					</div>
				)}
				<div className='row'>
					<div className='col'></div>
					<div className='col-3 align-self-end text-right mb-6'>
						<button className="btn btn-danger next-button" onClick={(e) => handleStockButton(results.length)}>Next</button>
					</div>
				</div>
			</div>	
		)
	}

	return (
		<div className='container'>
			<div className='row mt-3'>
				<p className='h2'>Add a new product</p>
			</div>
			<div className='container-fluid mt-3 p-3 container-main rounded'>
				<p className='h3 header-info'>1. Choose a category</p>
				<p className='h4'>Chosen category: {path}</p>
				<div className='row'>
				<div className='col-9' id='accordion'>
					{categories.map((category) =>
					<div key={category.name} id={category.name+'-accordion'}>
						<div id={category.name}>
							<h5 className="mb-0">
								<button onClick={() => setPath(category.name)} className="btn btn-link collapsed" data-toggle="collapse" data-target={'#'+category.name+'-sub'} aria-expanded="false" aria-controls={category.name+'-sub'}>
								{category.name}
								</button>
							</h5>
						</div>
						<div>
							{renderSubcategories(category.name, category, category.subcategories)}
						</div>
					</div>
					)}
				</div>
					<div className='col-3 align-self-end text-right mb-6'>
						<button className="btn btn-danger next-button" onClick={handleCategoryButton}>Next</button>
					</div>
				</div>	
			</div>
			{categorySelected ?
				<div className='container-fluid mt-3 p-3 container-main rounded'>
					<p className='h3 header-info'>2. Enter product information and criterion</p>
					<div className='form-group'>
						<div className='row'>
							<div className='col-1'>
								<label htmlFor='name'>Name</label>
							</div>
							<div className='col-4'>
								<input type='text' className='form-control form-control-md text-left' id='name' value={productName} onChange={(e)=>setProductName(e.target.value)}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-1'>
								<label htmlFor='brand'>Brand</label>
							</div>
							<div className='col-4'>
								<input type='text' className='form-control form-control-md text-left' id='brand' value={brand} onChange={(e)=>setBrand(e.target.value)}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-1'>
								<label htmlFor='description'>Description</label>
							</div>
							<div className='col'>
								<textarea type='text' className='form-control form-control-md text-left' id='description' value={description} onChange={(e)=>setDescription(e.target.value)}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-1'>
								<label htmlFor='price'>Price</label>
							</div>
							<div className='col-3'>
								<input type='number' min='0' id='price' onChange={(e)=>setPrice(e.target.value)}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-1'>
								<label htmlFor='discountedPrice'>Discounted price</label>
							</div>
							<div className='col-3'>
								<input type='number' min='0' id='discountedPrice' onChange={(e)=>setDiscountedPrice(e.target.value)}/>
							</div>
							<div className='col'>
								<p>Enter the original price if there is no discount.</p>
							</div>
						</div>
					</div>
					<ReactTags 
						inputFieldPosition='inline'
						tags = {attributes}
						handleDelete={handleAttributeDelete}
						handleAddition={handleAttributeAdd}
						placeholder='Enter product criteria'
					/>
					<div className='row'>
						<div className='col'></div>
						<div className='col-3 align-self-end text-right mb-6'>
							<button className="btn btn-danger next-button" onClick={handleAttributeButton}>Next</button>
						</div>
					</div>
				</div>
			:
				null
			}
			{attributeSelected ?
				<div className='container-fluid mt-3 p-3 container-main rounded'>
					<p className='h3 header-info'>3. Enter possible values for criterion separated by ,</p>
					{attributes.map((attr) =>
						<div key={attr.text}>
							<div className='form-group'>
								<div className='row'>
									<div className='col-1'>
										<label htmlFor={attr.text}>{attr.text}</label>
									</div>
									<div className='col-4'>
										<input type='text' className='form-control form-control-md text-left' 
										id={attr.text} 
										onChange={(e) => handlePossibleValueChange(e, attr.text)}
										/>
									</div>
								</div>
							</div>
					</div>
					)}
					<div className='row'>
						<div className='col'></div>
						<div className='col-3 align-self-end text-right mb-6'>
							<button className="btn btn-danger next-button" onClick={handlePossibleValuesButton}>Next</button>
						</div>
					</div>
				</div>
					:
				null
			}
			{possibleValuesEntered ?
				<div className='container-fluid mt-3 p-3 container-main rounded'>
					<p className='h3 header-info'>4. Enter stock information</p>
					<div className='row'>
					{attributes.map((attr) =>
						<div className='col' key={attr.text}>
							<p className='h5 header-info'>{attr.text}</p>
						</div>
					)}
					<div className='col-1'>
							<p className='h5 header-info'>Stock</p>
						</div>
					</div>
					{renderCombinations()}
				</div>
					:
				null
			}
			{stockEntered ?
			<div className='container-fluid mt-3 p-3 container-main rounded'>
				<p className='h3 header-info'>5. Upload an image of your product</p>
						<div className="form-group">
							<input type="file" className="form-control-file" id="image"/>
						</div>
			</div>
					:
				null
			}
		</div>
	)
}


const mapStateToProps = (state) => {
	return {
		isLoggedIn: state.signIn.isLoggedIn,
		customerId: state.signIn.userId,
		userType: state.signIn.userType

	}
}

export default connect(mapStateToProps, {showHeader, hideHeader, showVendorHeader, hideVendorHeader})(UpdateProduct)
