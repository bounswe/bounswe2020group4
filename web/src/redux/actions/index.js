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
    return (dispatch) => {
        dispatch(setLoginState({ userId: 1 }))
        console.log("dispatched")
        history.push("/")
    }
}