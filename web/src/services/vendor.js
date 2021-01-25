import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/vendor/products'
const deleteUrl = 'http://3.138.113.101:8080/vendor/product'
const fileUrl = 'http://3.138.113.101:8080/file'
const updateUrl = 'http://3.138.113.101:8080/vendor/wholeproducts'

const getProducts = async (vendorId) => {
	const response = await axios.get(`${baseUrl}/${vendorId}`)
	return response.data.data.result.productList
}

const uploadImage = async (imageData) => {
	let formData = new FormData();
    formData.append('image', imageData);
	const response = await axios.post(`${fileUrl}`, formData)
	return response.data.data.urls[0]
}

const addProduct = async (product, vendorId) => {
	const response = await axios.post(`${baseUrl}?vendorId=${vendorId}`, product)
	return response.data.status.code
}

const updateProduct = async (product, productId) => {
	const response = await axios.patch(`${updateUrl}/${productId}`, product)
	return response.data.status.code
}

const deleteProduct = async (vendorId, productId) => {
	var body = {"productId":productId}
	const response = await axios.delete(`${deleteUrl}/${vendorId}`, {'data':body})
	return response.data.status.code
}

export default {getProducts, uploadImage, addProduct, updateProduct, deleteProduct}