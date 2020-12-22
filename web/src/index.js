import React from 'react'
import ReactDOM from 'react-dom'
import { Provider } from 'react-redux'
import { createStore, applyMiddleware, compose } from 'redux'
import reduxThunk from 'redux-thunk'
import { persistStore, persistReducer } from 'redux-persist'
import storage from 'redux-persist/lib/storage'
import { PersistGate } from 'redux-persist/integration/react'

import App from './App'
import reducers from './redux/reducers'

const persistConfig = {
	key: 'signIn',
	storage: storage,
	whitelist: ['signIn']
}
const pReducer = persistReducer(persistConfig, reducers)

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose
const store = createStore(
	pReducer,
	composeEnhancers(applyMiddleware(reduxThunk))
)

const persistor = persistStore(store)

ReactDOM.render(
	<Provider store={store}>
		<PersistGate persistor={persistor}>
			<App />
		</PersistGate>
	</Provider>,
	document.querySelector('#root')
)
