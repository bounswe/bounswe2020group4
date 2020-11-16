import React from 'react';
import { Router, Route } from 'react-router-dom';
import { connect } from 'react-redux'
import history from './util/history';
import SignIn from './pages/SignIn'
import SignUp from './pages/SignUp'

import Header from './components/Header';
import Homepage from './pages/Homepage'

//Styling
import './App.css'


const App = ({ showHeader }) => {
    return (
        <div className='page-container'>
            <Router history={history}>
                <div>
                    {showHeader ? <Header /> : null}
                    <Route path="/" exact component={Homepage}/>
                    <Route path="/signin" exact component={SignIn} />
                    <Route path="/signup" exact component={SignUp} />
                </div>
            </Router>
        </div>
    );
}

const mapStateToProps = state => {
    return { showHeader: state.header.showHeader }
}

export default connect(mapStateToProps, {})(App);