import React, { useState, useEffect } from 'react'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import 'bootstrap/dist/css/bootstrap.min.css'
import './SignIn.css'
import logo from '../logo-buyo.png'
import { connect } from 'react-redux'
import { hideHeader, showHeader } from '../redux/actions'
import history from '../util/history'
import GoogleMaps from '../components/GoogleMaps'
import { setLoginState } from '../redux/actions'
import { Link } from 'react-router-dom'

import accountService from '../services/account'

const VendorSignUp = ({hideHeader, showHeader, setLoginState}) => {


	useEffect(() => {
		hideHeader()
		return () => showHeader()
	}, [])

	const [email, setEmail] = useState('')
	const [password, setPassword] = useState('')
	const [checked, setChecked] = useState(false)
	const [name, setName] = useState('')
	const [companyName, setCompanyName] = useState('')
	const [website, setWebsite] = useState('')
	const [selectedCoord, setSelectedCoord] = useState()

	const handleCheckedChange = function(e) {
		setChecked(!checked)
	}

	const handleCompanyNameChange = function(e) {
		setCompanyName(e.target.value)
	}

	const handleWebsiteChange = function(e) {
		setWebsite(e.target.value)
	}

	const handleNameChange = function(e) {
		setName(e.target.value)
	}

	const handleEmailChange = function(e) {
		setEmail(e.target.value)
	}

	const handlePasswordChange = function(e) {
		setPassword(e.target.value)
	}

	const handleClick = async function(e) {

		e.preventDefault()
		if(!email | !password | !name | !website | !companyName){
			alert('Fill in all fields')
			return
		}

		if (!checked){
			alert('Agree to terms and conditions')
			return
		} 

		if (!selectedCoord){
			alert('Choose your business location on the map')
			return
		}
		const signUpInfo = {
			'name': name, 
			'email': email, 
			'password': password,
			'lng': selectedCoord.lng(), 
			'lat': selectedCoord.lat(), 
			'website': website, 
			'company': companyName
		}
		const userId = await accountService.vendorSignUp(signUpInfo)
		if(userId == -1){
			alert('This user already exists, use a different email')
		} else if (userId == null){
			alert('Something went wrong, try again')
		} else {
			setLoginState({ userId: userId, userType: 'customer'})
			history.goBack()
		}
	}

	const redirectToSignin = function(e) {
		e.preventDefault()
		history.push('/signin')

	}

	return (
		<div className="signInModal">
			<div className="formContainer">
				<Link to='/'>
					<img className="logo" src={logo} alt="Buyo logo"/>
				</Link>
				<Form>
					<Form.Group controlId="formBasicEmail">
						<div className="col text-center">
							<a href="/signup">Are you a customer?</a>
						</div>
						<Form.Control
							className="formInputBox"
							type="text"
							placeholder="Name and surname"
							value = {name}
							onChange={handleNameChange}
						/>
						<Form.Control
							className="formInputBox"
							type="text"
							placeholder="Company name"
							value = {companyName}
							onChange={handleCompanyNameChange}
						/>
						<Form.Control
							className="formInputBox"
							type="text"
							placeholder="Business website"
							value = {website}
							onChange={handleWebsiteChange}
						/>
						<Form.Control
							className="formInputBox"
							type="email"
							placeholder="Business email"
							value = {email}
							onChange={handleEmailChange}
						/>
					</Form.Group>
					<Form.Group controlId="formBasicPassword">
						<Form.Control
							className="formInputBox"
							type="password"
							placeholder="Password"
							onChange={handlePasswordChange}
						/>
					</Form.Group>
					<div className='pb-4'>
						<div className='map-label text-center'>Enter the position of your distribution center</div>
						<GoogleMaps selectedCoord={selectedCoord} setSelectedCoord={setSelectedCoord}/>
					</div>
					<div className='row pb-4'>
						<div className='col-2'>
							<input type='checkbox' className='check-box' checked={checked} onChange={handleCheckedChange}/>
						</div>
						<div className='col'>
							I agree to terms and conditions.
						</div>
					</div>
					<Button
						className="submitButton"
						variant="primary"
						type="submit"
						onClick = {handleClick}
					>
                            SIGN UP
					</Button>
					<Button className="submitButtonTransparent" variant="primary" type="submit">
                            SIGN UP WITH GOOGLE
					</Button>
					<Button
						className="submitButtonTransparent"
						variant="primary"
						type="submit"
						onClick = {redirectToSignin}>
                            SIGN IN
					</Button>
				</Form>
			</div>
		</div>

	)

}


export default connect(null, {showHeader, hideHeader, setLoginState})(VendorSignUp)