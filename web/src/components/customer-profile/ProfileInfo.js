import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'

import history from '../../util/history'
import accountService from '../../services/account.js'

import './ProfileInfo.css'

const ProfileInfo = (props) => {

	const [firstName, setFirstName] = useState('')
	const [lastName, setLastName] = useState('')
	const [email, setEmail] = useState('')
	const [phone, setPhone] = useState('')
	const [gender, setGender] = useState('choose')

	const handleFirstNameChange = function(e) {
		setFirstName(e.target.value)
	}

	const handleLastNameChange = function(e) {
		setLastName(e.target.value)
	}

	const handleEmailChange = function(e) {
		setEmail(e.target.value)
	}

	const handlePhoneChange = function(e) {
		setPhone(e.target.value)
	}

	const handleGenderChange = function(e){
		setGender(e.target.value)
	}

	const handleClick = async function(e) {
		e.preventDefault()
		const profileInfo = {
			'firstName': firstName,
			'lastName': lastName,
			'email': email,
			'phone': phone,
			'gender': gender
		}

		const updateProfile = await accountService.updateProfileInfo('customer', props.customerId, profileInfo)
		if(!updateProfile){
			history.push('/customerprofile')
		}
		//TODO: Use backend data when endpoint is ready

	}

	useEffect(() => {

		if(!props.isLoggedIn | props.userType != 'customer') {
			history.push('/signin')
			return
		}

		const getProfileInfo = async () => {
			const profileInfo = await accountService.getProfileInfo('customer', props.customerId)
			console.log(profileInfo)
			if (!profileInfo){
				setFirstName('Eylul')
				setLastName('Yalcinkaya')
				setEmail('eylul@hotmail.com')
				setPhone('05305005050')
				setGender('Female')
			}
			//TODO: Use backend data when endpoint is ready
		}

		getProfileInfo()
	}, [props.isLoggedIn, props.customerId])

	return (
		<div className='container-main p-3 mt-3 mb-3 mr-1 ml-3 rounded'>
			<div className='row m-3'>
				<form className="form-container">
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label hmtlFor='name'>Name</label>
							</div>
							<div className='col'>
								<input type='text' className='form-control form-control-md text-left' id='name' value={firstName} onChange={handleFirstNameChange}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='surname'>Surname</label>
							</div>
							<div className='col'>
								<input type='text' className='form-control form-control-md text-left' id='surname' value={lastName} onChange={handleLastNameChange}/>
							</div>
						</div>
					</div>

					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='email'>Email</label>
							</div>
							<div className='col'>
								<input type='text' readOnly className='form-control-plaintext text-left' id='email' value={email} onChange={handleEmailChange}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='phone'>Phone number</label>
							</div>
							<div className='col'>
								<input type='text' className='form-control form-control-md text-left' id='phone' value={phone} onChange={handlePhoneChange}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='gender'>Gender</label>
							</div>
							<div className='col'>
								<select className="form-select form-select-lg p-1 mb-1" value={gender} onChange={handleGenderChange}>
									<option value="choose" disabled hidden>Choose</option>
									<option value="female">Female</option>
									<option value="male">Male</option>
									<option value="other">Other</option>
									<option value="noInfo">Prefer not to say</option>
								</select>
							</div>
						</div>
					</div>
					<div className='text-right'>
						<button type='submit' className='btn btn-danger' onSubmit={handleClick}>Update your profile</button>
					</div>

				</form>
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

export default connect(mapStateToProps)(ProfileInfo)