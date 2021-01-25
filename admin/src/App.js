import React, { useEffect } from 'react'
import { Router, Route } from 'react-router-dom'
import history from './util/history'
import Homepage from './components/Homepage'
import LoginPage from './components/LoginPage'
import ReportedProducts from './components/reportedProducts'
import ReportedComments from './components/reportedComments'

import './App.css'


const App = () => {
    useEffect(() => {
        document.title = 'Buyo Admin'
    }, [])

    return (
        <div className='page-container'>
            <Router history={history}>
                <div>
                    <Route path="/" exact component={LoginPage}/>
                    <Route path="/homepage" exact component={Homepage} />
                    <Route path='/reported_products' exact component={ReportedProducts} />
                    <Route path='/reported_comments' exact component={ReportedComments} />
                </div>
            </Router>
        </div>
    )
}

export default App