import React, { useState, useEffect } from 'react'
import { connect } from 'react-redux'
import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'
import history from '../util/history'

import './AddProduct.css'
import {getCategories} from '../services/category'

const AddProduct= (props) => {

	const [categories, setCategories] = useState([])
	const [path, setPath] = useState('')

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


	return (
		<div className='container'>
			<div className='row mt-3'>
				<p className='h2'>Add a new product</p>
			</div>
			<div className='container-fluid mt-3 container-main rounded'>
				<p className='h3 header-info'>1. Choose a category</p>
				<p className='h4'>Choosen category: {path}</p>
				<div id='accordion'>
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
			</div>
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
