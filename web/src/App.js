import React from 'react';
import { Router, Route } from 'react-router-dom';
import history from './util/history';

import Header from './components/Header';
import Homepage from './pages/Homepage'

//Styling
import './App.css'


const App = () => {
    return (
        <div className='page-container'>
            <Router history={history}>
                <div>
                    <Header />
                    <Route path="/" exact component={Homepage}/>
                </div>
            </Router>
        </div>
    );
}

export default App;