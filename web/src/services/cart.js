import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/cart'

const addProductToCart = async (customerId, productId, productInfo) => {
	const response = await axios.post(
		`${baseUrl}?customerId=${customerId}&productId=${productId}&productInfo=${JSON.stringify(productInfo)}`)
	console.log(`${baseUrl}?customerId=${customerId}&productId=${productId}&productInfo=${JSON.stringify(productInfo)}`)
	return response
}

export default { addProductToCart }