import axios from 'axios'
import queryString from 'query-string'

const baseUrl = 'http://3.141.25.245:8080'


export const searchProducts = async (searchTerm) => {
	const response = await axios.get(`${baseUrl}/products?search=${searchTerm}`)
	return response.data.data.products.productList
}

const getProduct = async (id) => {
	const response = await axios.get(`${baseUrl}/product?id=${id}`)
	return response.data.data.result
}

const getProducts = async (params) => {
	if(params.categories) {
		params.categories = '[' + params.categories.split(',').map(s => '"' + s + '"').toString() + ']'
	}

	const queryUrl = `${baseUrl}/products?` + queryString.stringify(params)

	const response = await axios.get(queryUrl)
	return response.data.data.products
}

export default { getProduct, searchProducts, getProducts }