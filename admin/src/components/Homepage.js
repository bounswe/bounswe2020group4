import React, { useEffect } from 'react';
import history from '../util/history'
import Header from './Header'

const Homepage = ({ location }) => {

    useEffect(() => {
        if(!location.state.isLoggedIn){
            history.push("/")
        }
    }, [])

    return (
        <div>
            <Header />
        </div>
    )
}

export default Homepage