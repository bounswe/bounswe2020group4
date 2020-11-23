import React, { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import './SignIn.css'
import logo from '../logo-buyo.png'
import { connect } from 'react-redux'
import { hideHeader } from '../redux/actions';
import { showHeader } from '../redux/actions';
import { login } from '../redux/actions';
import { useHistory, withRouter, Redirect } from "react-router-dom";
import history from '../util/history';




const SignIn = ({login, hideHeader, showHeader}) => {

   // const dispatch = useDispatch()

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

    const handleClick = function(e) {
        e.preventDefault()
        login({'email':email, 'password': password})
    } 

    const redirectToSignup = function(e) {
        e.preventDefault()
        history.push("/signup")
    }

    return (
        <div className="signInModal">
            <div className="formContainer">
                <img className="logo" src={logo} alt="Buyo logo"/>
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
                        onClick = {redirectToSignup}>
                            SIGN UP
                        </Button>
                </Form>
            </div>
        </div>
    
    );
  
}


const mapStateToProps = state => {
    return { showHeader: state.header.showHeader,
            hideHeader: state.header.hideHeader,
            signIn: state.signIn.signInReducer}
}

export default connect(mapStateToProps, {showHeader, hideHeader, login})(SignIn);