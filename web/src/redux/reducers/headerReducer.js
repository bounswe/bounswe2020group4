import { SHOW_HEADER, HIDE_HEADER } from '../actions/types'

const INITIAL_STATE = {
	showHeader: true
}

export default (state = INITIAL_STATE, action) => {
	switch (action.type) {
	case SHOW_HEADER:
		return { ...state, showHeader: true }
	case HIDE_HEADER:
		return { ...state, showHeader: false }
	default:
		return state
	}
}