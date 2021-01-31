import { SHOW_HEADER, HIDE_HEADER, SHOW_VENDOR_HEADER, HIDE_VENDOR_HEADER } from '../actions/types'

const INITIAL_STATE = {
	showHeader: true,
	showVendorHeader: false
}

export default (state = INITIAL_STATE, action) => {
	switch (action.type) {
	case SHOW_HEADER:
		return { ...state, showHeader: true }
	case HIDE_HEADER:
		return { ...state, showHeader: false }
	case SHOW_VENDOR_HEADER:
		return { ...state, showVendorHeader: true}
	case HIDE_VENDOR_HEADER:
		return { ...state, showVendorHeader: false}
	default:
		return state
	}
}