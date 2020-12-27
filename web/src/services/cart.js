import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/' 

const getCart = async (id) => {
	let response
	try{
		response = await axios.get(`${baseUrl}cart?customerId=${id}`)
		console.log(response)
	} catch (err) {
		return null
	}

	if(response.data.status.code == 200){
		console.log(response.data.data.products)
		return response.data.data.products
	} else {
		return null
	}

}

export default {getCart}