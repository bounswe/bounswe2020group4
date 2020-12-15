import axios from 'axios'


const baseUrl = 'http://3.138.113.101:8080'


export const searchProducts = async (searchTerm) => {
	const response = await axios.get(`${baseUrl}/products?search=${searchTerm}`)
	return response.data.data.products
}

const getProduct = async (id) => {
	const response = await axios.get(`${baseUrl}/product?id=${id}`)
	return response.data.data.result
}

const getCategoryProducts = async (path) => {
	const pathArray = path.split(',').map(s => '"' + s + '"')
	const response = await axios.get(`${baseUrl}/products?categories=[${pathArray}]`)
	return response.data.data.products
}

export default { getProduct, getCategoryProducts, searchProducts }