import React from 'react';
import { Router, Route } from 'react-router-dom';
import history from './util/history';

import Header from './components/Header';
import ProductDetails from './pages/ProductDetails';

const App = () => {
    return (
        <div className='page-container'>
            <Router history={history}>
                <div>
                    <Header />
                    <ProductDetails />
                </div>
            </Router>
        </div>
    );
}

export default App;