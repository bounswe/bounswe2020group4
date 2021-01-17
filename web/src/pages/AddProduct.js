import React, { useState, useEffect } from 'react'
import { connect } from 'react-redux'
import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'
import { WithContext as ReactTags } from 'react-tag-input';
import history from '../util/history'

import './AddProduct.css'
import {getCategories} from '../services/category'

const AddProduct= (props) => {

	const [categories, setCategories] = useState([])
	const [path, setPath] = useState('')
	const [categorySelected, setCategorySelected] = useState(false)
	const [productName, setProductName] = useState('')
	const [description, setDescription] = useState('')
	const [brand, setBrand] = useState('')
	const [attributes, setAttributes] = useState([])
	const [attributeSelected, setAttributeSelected] = useState(false)


	useEffect(() => {
		
		//TODO: uncomment this
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
		if(!productName | !brand | !description){
			alert('You must fill out name, brand and description fields.')
			return
		}
		if(attributes.length){
			setAttributeSelected(true)
		} else {
			setAttributeSelected(false)
			alert('You must write at least one criteria first.')
		}
	}

	const handleAttributeAdd = (attribute) => {
		attributes.push(attribute)
		setAttributes(attributes)
		//setAttributeSelected(attributes.length > 0)
	}

	const handleAttributeDelete = (i) => {
		attributes.splice(i, 1)
		setAttributes(attributes)
		//setAttributeSelected(attributes.length > 0)
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
					<div className='form-group'>
						<div className='row'>
							<div className='col-1'>
								<label hmtlFor='name'>Name</label>
							</div>
							<div className='col-4'>
								<input type='text' className='form-control form-control-md text-left' id='name' value={productName} onChange={(e)=>setProductName(e.target.value)}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-1'>
								<label hmtlFor='brand'>Brand</label>
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
								<textarea type='text' className='form-control form-control-md text-left' id='surname' value={description} onChange={(e)=>setDescription(e.target.value)}/>
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
				<div>hello</div>
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

export default connect(mapStateToProps, {showHeader, hideHeader, showVendorHeader, hideVendorHeader})(AddProduct)
