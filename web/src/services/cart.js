import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/cart'

const addProductToCart = async (customerId, productId, productInfo) => {
	const params = {
		customerId: customerId,
		productId: productId,
		productInfo: productInfo
	}
	const response = await axios.post(`${baseUrl}`, { params })
	return response
}

export default { addProductToCart }