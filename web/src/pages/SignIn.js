import React from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import './SignIn.css'
import logo from '../logo-buyo.png'



class SignIn extends React.Component {
  onSubmit = formValues => {
  };

  render() {
    return (
            <div className="signInModal">
                <div className="formContainer">
                    <img class="logo" src={logo} alt="Buyo logo"/>
                    <Form>
                        <Form.Group controlId="formBasicEmail">
                            <Form.Control className="formInputBox" type="email" placeholder="Email" />
                        </Form.Group>

                        <Form.Group controlId="formBasicPassword">
                            <Form.Control className="formInputBox" type="password" placeholder="Password" />
                        </Form.Group>
                        <Button className="submitButton" variant="primary" type="submit">
                            SIGN IN
                        </Button>
                        <Button className="submitButtonTransparent" variant="primary" type="submit">
                            SIGN IN WITH GOOGLE
                        </Button>
                    </Form>
                </div>
            </div>
      
    );
  }
}

export default SignIn;