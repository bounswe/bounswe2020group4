import { SET_LOGIN_STATE, SET_LOGOUT_STATE } from '../actions/types'

const INITIAL_STATE = {
	isLoggedIn: false,
	userId: '',
	userType: ''
}

export default (state = INITIAL_STATE, action) => {
	switch(action.type){
	case SET_LOGIN_STATE:
		return {
			...state,
			...action.payload,
			isLoggedIn: true,
		}
	case SET_LOGOUT_STATE: {
		return {
			isLoggedIn: false,
		}
	}
	default:
		return state
	}
}