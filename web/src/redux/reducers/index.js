import { combineReducers } from "redux";
import headerReducer from './headerReducer'

export default combineReducers({
    header: headerReducer
});