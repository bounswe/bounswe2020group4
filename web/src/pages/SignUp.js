import React, { useState, useEffect } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import './SignIn.css'
import logo from '../logo-buyo.png'
import { connect } from 'react-redux'
import { hideHeader, showHeader } from '../redux/actions';
import history from '../util/history'
import accountService from '../services/account'
import { setLoginState } from '../redux/actions';



const SignUp = ({hideHeader, showHeader, setLoginState}) => {


    //This function corresponds to componentDidMount
    //The return function corresponds to componentDidUnmount
    useEffect(() => {
        hideHeader()
        return () => showHeader()
    }, [])

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleEmailChange = function(e) {
        setEmail(e.target.value)
    }

    const handlePasswordChange = function(e) {
        setPassword(e.target.value)
    }

    const handleClick = async function(e) {
        e.preventDefault()
        const userId = accountService.signUp({'email':email, 'password': password})
        setLoginState({ userId: userId});     
    } 

    const redirectToSignin = function(e) {
        e.preventDefault()
        history.push("/signin")
       
    }
  
    return (
            <div className="signInModal">
                <div className="formContainer">
                    <img class="logo" src={logo} alt="Buyo logo"/>
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
                        </Form.Group>
                        <Form.Group controlId="formBasicPassword">
                            <Form.Control className="formInputBox" type="password" placeholder="Password" />
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
      
    );
  
}


export default connect(null, {showHeader, hideHeader, setLoginState})(SignUp);