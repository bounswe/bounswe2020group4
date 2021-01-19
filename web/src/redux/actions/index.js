import {
	SHOW_HEADER,
	HIDE_HEADER,
	SHOW_VENDOR_HEADER, 
	HIDE_VENDOR_HEADER,
	SET_LOGIN_STATE
} from './types'

export const showHeader = () => {
	return { type: SHOW_HEADER }
}

export const hideHeader = () => {
	return { type: HIDE_HEADER }
}

export const showVendorHeader = () => {
	return { type: SHOW_VENDOR_HEADER }
}

export const hideVendorHeader = () => {
	return { type: HIDE_VENDOR_HEADER }
}

export const setLoginState = (loginData) => {
	return {
		type: SET_LOGIN_STATE,
		payload: loginData,
	}
}



