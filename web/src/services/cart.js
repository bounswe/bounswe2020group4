import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/' 

const getCart = async (id) => {
	let response
	try{
		response = await axios.get(`${baseUrl}cart?customerId=${id}`)
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
		response = await axios.post(`${baseUrl}cart?customerId=${customerId}&productId=${productId}&productInfo=${JSON.stringify(productInfo)}`)
	} catch (err) {
		return null
	}
	return response.data.status.code

}

export default {getCart, removeFromCart}