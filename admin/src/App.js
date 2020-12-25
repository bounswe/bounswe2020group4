import React from 'react'
import { Router, Route } from 'react-router-dom'
import history from './util/history'
import Homepage from './pages/Homepage'
import LoginPage from './pages/LoginPage'


const App = () => {
    return (
        <div className='page-container'>
            <Router history={history}>
                <div>
                    <Route path="/" exact component={LoginPage}/>
                    <Route path="/homepage" exact component={Homepage} />
                </div>
            </Router>
        </div>
    )
}

export default App