import history from '../../util/history';

import {
    SHOW_HEADER,
    HIDE_HEADER,
    SET_LOGIN_STATE
} from './types'

export const showHeader = () => {
    return { type: SHOW_HEADER }
}

export const hideHeader = () => {
    return { type: HIDE_HEADER }
}

export const setLoginState = (loginData) => {
    return {
        type: SET_LOGIN_STATE,
        payload: loginData,
    }
}

export const login = (loginInput) => {
    const { email, password } = loginInput;

    var loginUrl = "http://3.138.113.101:8080/login?userType=customer&email=" + email + "&password=" + password
    return (dispatch) => {  
        return fetch(loginUrl, {
        method: 'POST',
        headers: { 
            Accept: 'application/json',
            'Content-Type': 'application/json',
          },
        })
        .then((response) => response.json())
        .then((json) => {
            if (json.status.code === 200) {
            dispatch(setLoginState({ ...json, userId: json.data.userId})); 
            console.log("login success")
            history.push("/")
            } else {
            console.log('Login Failed', 'Username or Password is incorrect');
            }
        })
        .catch((err) => {
            console.log(err);
        });
    };

}

