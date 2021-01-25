import React, { useState } from 'react'
import history from '../util/history'
import { login } from '../util/api'

const LoginPage = ({ location }) => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState(false)

    const onLoginClick = async () => {
        const response = await login(username, password)
        if(response){
            history.push({
                pathname: '/homepage',
                state: { isLoggedIn: true }
            })
        } else {
            setError(true)
        }
    }

    return (
        <div>
            <div className={`${error ? 'border-danger' : null} border rounded p-5 position-absolute top-50 start-50 translate-middle`}>
                <div>Username:</div>
                <div className='mb-2'>
                    <input type='text' value={username} onChange={(e) => setUsername(e.target.value)}/>
                </div>
                <div>Password:</div>
                <div className='mb-3'>
                    <input type='password' value={password} onChange={(e) => setPassword(e.target.value)}/>
                </div>
                <div className='text-center'>
                    <button className='btn btn-danger' onClick={onLoginClick}>Login</button>
                </div>
            </div>
        </div>
    )
}

export default LoginPage