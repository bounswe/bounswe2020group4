import React from 'react'
import { Router, Route } from 'react-router-dom'
import { connect } from 'react-redux'
import history from './util/history'

//Pages
import Header from './components/Header'
import ProductDetails from './pages/ProductDetails'
import Homepage from './pages/Homepage'
import Wishlist from './pages/Wishlist'
import Cart from './pages/Cart'
import Checkout from './pages/Checkout'
import CategoryProducts from './pages/CategoryProducts'
import SignIn from './pages/SignIn'
import SignUp from './pages/SignUp'
import VendorSignUp from './pages/VendorSignUp'
import VendorSignIn from './pages/VendorSignIn'
import CustomerProfile from './pages/CustomerProfile'

//Styling
import './App.css'


const App = ({ showHeader }) => {
	return (
		<div className='page-container'>
			<Router history={history}>
				<div>
					{showHeader ? <Header /> : null}
					<Route path="/" exact component={Homepage}/>
					<Route path="/signin" exact component={SignIn} />
					<Route path="/signup" exact component={SignUp} />
					<Route path="/product/:id" exact component={ProductDetails}/>
					<Route path="/wishlist" exact component={Wishlist}/>
					<Route path="/cart" exact component={Cart}/>
					<Route path="/vendorsignup" exact component={VendorSignUp}/>
					<Route path="/vendorsignin" exact component={VendorSignIn}/>
					<Route path="/checkout" exact component={Checkout} />
					<Route path="/products" exact component={CategoryProducts}/>
					<Route path="/customerprofile" exact component={CustomerProfile}/>
				</div>
			</Router>
		</div>
	)
}

const mapStateToProps = state => {
	return { showHeader: state.header.showHeader }
}

export default connect(mapStateToProps, {})(App)