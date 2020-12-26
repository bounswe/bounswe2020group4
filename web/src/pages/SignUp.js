import React, { useState, useEffect } from 'react'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import 'bootstrap/dist/css/bootstrap.min.css'
import './SignIn.css'
import logo from '../logo-buyo.png'
import { connect } from 'react-redux'
import { hideHeader, showHeader } from '../redux/actions'
import history from '../util/history'
import accountService from '../services/account'
import { setLoginState } from '../redux/actions'
import { Link } from 'react-router-dom'

const SignUp = ({hideHeader, showHeader, setLoginState}) => {

	useEffect(() => {
		hideHeader()
		return () => showHeader()
	}, [])

	const [email, setEmail] = useState('')
	const [password, setPassword] = useState('')
	const [checked, setChecked] = useState(false)

	const handleCheckedChange = function(e) {
		setChecked(!checked)
	}

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
		} else if (!checked){
			alert('Agree to terms and conditions')
		} else {
			const userId = await accountService.signUp({'email': email, 'password': password})
			console.log(userId)
			if(userId == -1){
				alert('This user already exists, use a different email')
			} else if (userId == null){
				alert('Something went wrong, try again')
			} else {
				setLoginState({ userId: userId, userType: 'customer'})
				history.goBack()
			}
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
						<div className='row pb-4'>
							<div className='col-2'>
								<input type='checkbox' className='check-box' checked={checked} onChange={handleCheckedChange}/>
							</div>
							<div className='col'>
								I agree to terms and conditions.
							</div>
						</div>

						<div className="col text-center">
							<a href="/vendorsignup">Are you a vendor?</a>
						</div>
					</Form.Group>
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


export default connect(null, {showHeader, hideHeader, setLoginState})(SignUp)