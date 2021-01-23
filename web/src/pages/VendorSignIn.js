import React, { useState, useEffect } from 'react'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import 'bootstrap/dist/css/bootstrap.min.css'
import './SignIn.css'
import logo from '../logo-buyo.png'
import { connect } from 'react-redux'
import { hideHeader, showHeader } from '../redux/actions'
import history from '../util/history'
import { setLoginState } from '../redux/actions'
import { Link } from 'react-router-dom'

import accountService from '../services/account'

const VendorSignIn = ({hideHeader, showHeader, setLoginState}) => {

	useEffect(() => {
		hideHeader()
		return () => showHeader()
	}, [])

	const [email, setEmail] = useState('')
	const [password, setPassword] = useState('')

	const handleEmailChange = function(e) {
		setEmail(e.target.value)
	}

	const handlePasswordChange = function(e) {
		setPassword(e.target.value)
	}

	const handleClick = async function(e) {
		e.preventDefault()
		if(email == '' | password == ''){
			alert('Enter your credentials')
		} else {
			const response = await accountService.vendorLogin({ 'email': email, 'password': password})
			if (!response?.userId){
				alert('Wrong credentials')
			} else if (response.status == 'banned') {
				alert('Your account has been suspended. Please check your e-mail for further information.')
			} else if (response.status == 'not-verified') {
				alert('Your account has not been verified. Please check your e-mail for the verification link.')
			} else {
				setLoginState({ userId: response.userId, userType: 'vendor'})
				history.push('/vendorprofile')
			}
		}
	}

	const redirectToSignUp = function(e) {
		e.preventDefault()
		history.push('/vendorsignup')
	}

	return (
		<div className="signInModal">
			<div className="formContainer">
				<Link to='/'>
					<img className="logo" src={logo} alt="Buyo logo"/>
				</Link>
				<Form>
					<Form.Group controlId="formBasicEmail">
						<Form.Control
							className="formInputBox"
							type="email"
							placeholder="Email"
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
						<div className="col text-center">
							<a href="/signin">Are you a customer?</a>
						</div>
					</Form.Group>

					<Button
						className="submitButton"
						variant="primary"
						type="submit"
						onClick = {handleClick}
					>
						SIGN IN AS VENDOR
					</Button>

					<Button className="submitButtonTransparent" variant="primary" type="submit">
						SIGN IN WITH GOOGLE
					</Button>

					<Button
						className="submitButtonTransparent"
						variant="primary"
						type="submit"
						onClick = {redirectToSignUp}
					>
						SIGN UP AS VENDOR
					</Button>
				</Form>
			</div>
		</div>
	)
}


export default connect(null, {showHeader, hideHeader, setLoginState})(VendorSignIn)