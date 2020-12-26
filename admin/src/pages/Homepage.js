import React, { useEffect } from 'react';
import history from '../util/history'

const Homepage = ({ location }) => {

    useEffect(() => {
        if(!location.state.isLoggedIn){
            history.push("/")
        }
    }, [])

    return (
        <div>
            Homepage!
        </div>
    )
}

export default Homepage