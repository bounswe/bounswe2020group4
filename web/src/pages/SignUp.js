import React, { useState} from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import './SignIn.css'
import logo from '../logo-buyo.png'
import { connect } from 'react-redux'
import { hideHeader } from '../redux/actions';
import { showHeader } from '../redux/actions';


const SignUp = ({hideHeader}) => {

    hideHeader()

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
                    </Form>
                </div>
            </div>
      
    );
  
}


const mapStateToProps = state => {
    return { showHeader: state.header.showHeader,
            hideHeader: state.header.hideHeader }
}

export default connect(mapStateToProps, {showHeader, hideHeader})(SignUp);