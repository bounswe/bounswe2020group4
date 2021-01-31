import axios from 'axios'

const baseUrl = 'http://3.141.25.245:8080'

const addToWishlist = async (customerId, productId) => {
	const response = await axios.post(`${baseUrl}/like?customerId=${customerId}&productId=${productId}`)
	return response
}

const removeFromWishlist = async (customerId, productId) => {
	const response = await axios.post(`${baseUrl}/like?customerId=${customerId}&productId=${productId}`)
	return response
}

const getWishlist = async (customerId) => {
	const response = await axios.get(`${baseUrl}/wishlist?customerId=${customerId}`)
	return response.data.data.products
}

const isInWishlist = async (customerId, productId) => {
	const response = await axios.get(`${baseUrl}/wishlist?customerId=${customerId}`)
	if (typeof response.data.data.products == 'object') return false
	return response.data.data.products.find(p => p.id === productId) !== undefined
}

export default { addToWishlist, removeFromWishlist, getWishlist, isInWishlist }