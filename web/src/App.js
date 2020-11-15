import React from 'react';
import { Router, Route } from 'react-router-dom';
import history from './util/history';
import SignIn from './pages/SignIn'
import SignUp from './pages/SignUp'

import Header from './components/Header';


const App = () => {
    return (
        <div className='page-container'>
            <Router history={history}>
                <div>
                    <Header />
                    <Route path="/signin" exact component={SignIn} />
                    <Route path="/signup" exact component={SignUp} />

                </div>
            </Router>
        </div>
    );
}

export default App;