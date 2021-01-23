import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/vendor/products/'
const fileUrl = 'http://3.138.113.101:8080/file'

const getProducts = async (vendorId) => {
	const response = await axios.get(`${baseUrl}${vendorId}`)
	console.log(`${baseUrl}${vendorId}`)
	console.log(response)
	//TODO: error handling
	return response.data.data.result.productList
}

const uploadImage = async (imageData) => {
	let formData = new FormData();
    formData.append('image', imageData);
	const response = await axios.post(`${fileUrl}`, formData)
	return response.data.data.urls[0]
}

export default {getProducts, uploadImage}