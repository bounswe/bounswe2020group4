import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/vendor/products/'

const getProducts = async (vendorId) => {
	const response = await axios.get(`${baseUrl}${vendorId}`)
	console.log(`${baseUrl}${vendorId}`)
	console.log(response)
	//TODO: error handling
	return response.data.data.result.productList
}

export default {getProducts}