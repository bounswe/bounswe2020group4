import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'

import history from '../../util/history'
import accountService from '../../services/account.js'

import './VendorProfileInfo.css'

import GoogleMaps from '../GoogleMaps'

const VendorProfileInfo = (props) => {

	const [firstName, setFirstName] = useState('')
	const [lastName, setLastName] = useState('')
	const [companyName, setCompanyName] = useState('')
	const [website, setWebsite] = useState('')
	const [email, setEmail] = useState('')
	const [selectedCoord, setSelectedCoord] = useState()
	
	const handleFirstNameChange = function(e) {
		setFirstName(e.target.value)
	}

	const handleLastNameChange = function(e) {
		setLastName(e.target.value)
	}

	const handleCompanyNameChange = function(e) {
		setCompanyName(e.target.value)
	}

	const handleWebsiteChange = function(e) {
		setWebsite(e.target.value)
	}

	const handleClick = async function(e) {
		e.preventDefault()
		const profileInfo = {
			'firstName': firstName,
			'lastName': lastName,
			'company': companyName,
			'website': website,
			'coords': selectedCoord,
		}

		const updateProfile = await accountService.updateProfileInfo(props.userType, props.userId, profileInfo)
		if (!updateProfile){
			alert('Something went wrong, please try again.')
		} else {
			alert('Your profile information has been successfully changed.')
			history.go(0)
		}
	}

	useEffect(() => {
		if(!props.isLoggedIn | props.userType != 'vendor') {
			history.push('/signin')
			return
		}

		const getProfileInfo = async () => {
			const profileInfo = await accountService.getProfileInfo(props.userType, props.userId)
			
			if(!profileInfo){
				alert('Something went wrong, please refresh this page.')
				return
			}
			setFirstName(profileInfo.firstName)
			setLastName(profileInfo.lastName)
			setCompanyName(profileInfo.companyName)
			setWebsite(profileInfo.website)
			setEmail(profileInfo.email)
			setSelectedCoord(profileInfo.coords)
		}
		getProfileInfo()
	}, [props.isLoggedIn, props.userId])

	return (
		<div className='container-main p-3 mt-3 mb-3 mr-1 ml-3 rounded'>
			<div className='row m-3'>
				<form className="form-container">
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label hmtlFor='firstName'>First Name</label>
							</div>
							<div className='col'>
								<input type='text' className='form-control form-control-md text-left' id='firstName' value={firstName} onChange={handleFirstNameChange}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label hmtlFor='lastName'>Last Name</label>
							</div>
							<div className='col'>
								<input type='text' className='form-control form-control-md text-left' id='lastName' value={lastName} onChange={handleLastNameChange}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='companyName'>Company Name</label>
							</div>
							<div className='col'>
								<input type='text' className='form-control form-control-md text-left' id='companyName' value={companyName} onChange={handleCompanyNameChange}/>
							</div>
						</div>
					</div>

					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='website'>Website</label>
							</div>
							<div className='col'>
								<input type='text' className='form-control form-control-md text-left' id='website' value={website} onChange={handleWebsiteChange}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='email'>Email</label>
							</div>
							<div className='col'>
								<input type='text' readOnly className='form-control-plaintext text-left' id='email' value={email}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='map-label text-center'>Position of your distribution center</div>
							<GoogleMaps selectedCoord={selectedCoord} setSelectedCoord={setSelectedCoord}/>
						</div>
					</div>
					<div className='text-right'>
						<button type='button' className='btn btn-danger' onClick={handleClick}>Update your profile</button>
					</div>

				</form>
			</div>
		</div>
	)
}

const mapStateToProps = (state) => {
	return {
		isLoggedIn: state.signIn.isLoggedIn,
		userId: state.signIn.userId,
		userType: state.signIn.userType
	}
}

export default connect(mapStateToProps)(VendorProfileInfo)