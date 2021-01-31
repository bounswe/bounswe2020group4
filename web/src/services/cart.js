import axios from 'axios'

const baseUrl = 'http://3.141.25.245:8080/cart'

const addProductToCart = async (customerId, productId, productInfo) => {
	const response = await axios.post(
		`${baseUrl}?customerId=${customerId}&productId=${productId}&productInfo=${JSON.stringify(productInfo)}`)
	return response
}

const getCart = async (id) => {
	let response
	try{
		response = await axios.get(`${baseUrl}?customerId=${id}`)
	} catch (err) {
		return null
	}

	if(response.data.status.code == 200){
		return response.data.data.products
	} else {
		return null
	}

}

const removeFromCart = async (customerId, productId, productInfo) => {
	let response
	try{
		response = await axios.post(`${baseUrl}?customerId=${customerId}&productId=${productId}&productInfo=${JSON.stringify(productInfo)}`)
	} catch (err) {
		return null
	}
	return response.data.status.code

}

export default {getCart, removeFromCart, addProductToCart}