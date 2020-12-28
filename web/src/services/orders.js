import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/order'

const getOrders = async (userId, userType='customer') => {
	try {
		const response = await axios.get(`${baseUrl}?id=${userId}&userType=${userType}`)
		console.log(response)
		return response.data.data
	} catch(err) {
		return []
	}
}

export default { getOrders }