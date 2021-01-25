import React, { useState, useEffect } from 'react'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import 'bootstrap/dist/css/bootstrap.min.css'
import './SignIn.css'
import logo from '../logo-buyo.png'
import { connect } from 'react-redux'
import { hideHeader } from '../redux/actions'
import { showHeader } from '../redux/actions'
import { setLoginState } from '../redux/actions'
import history from '../util/history'
import accountService from '../services/account'
import { Link } from 'react-router-dom'

const SignIn = ({hideHeader, showHeader, setLoginState}) => {

	//This function corresponds to componentDidMount
	//The return function corresponds to componentDidUnmount
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
			const response = await accountService.login({ 'email': email, 'password': password})
			if (!response?.userId){
				alert('Wrong credentials')
			} else if (!response.status) {
				alert('You do not have a verified account.')
			} else if (response.status == 'banned') {
				alert('Your account has been suspended. Please contact support.')
			} else if (response.status == 'not-verified') {
				alert('Your account has not been verified. Please check your e-mail for the verification link.')
			} else if (response.status == 'verified') {
				setLoginState({ userId: response.userId, userType: 'customer'})
				history.push('/')
			} else {
				alert('You do not have a verified account.')
			}
		}
	}
	const redirectToSignup = function(e) {
		e.preventDefault()
		history.push('/signup')
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
							value = {password}
							onChange={handlePasswordChange}
						/>
						<div className="col text-center">
							<Link to="/vendorsignin" className="sign-in-link">Are you a vendor?</Link>
						</div>
						<div className="col text-center">
							<Link to="/resetpassword" className="sign-in-link">Click here to reset your password.</Link>
						</div>
					</Form.Group>
					<Button
						className="submitButton"
						variant="primary"
						type="submit"
						onClick = {handleClick}
					>
						SIGN IN
					</Button>
					<Button className="submitButtonTransparent" variant="primary" type="submit">
						SIGN IN WITH GOOGLE
					</Button>
					<Button
						className="submitButtonTransparent"
						variant="primary"
						type="submit"
						onClick = {redirectToSignup}
					>
							SIGN UP
					</Button>
				</Form>
			</div>
		</div>

	)

}


export default connect(null, {showHeader, hideHeader, setLoginState})(SignIn)