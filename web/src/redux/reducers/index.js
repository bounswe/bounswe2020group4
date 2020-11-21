import { combineReducers } from "redux";
import headerReducer from './headerReducer'
import signInReducer from './signInReducer'

export default combineReducers({
    header: headerReducer,
    signIn: signInReducer
});