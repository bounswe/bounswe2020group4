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

const checkoutOrder = async (customerId, paymentInfo, address) => {
	try {
		const response = await axios.post(`${baseUrl}?customerId=${customerId}&creditCard={"name":"${paymentInfo.name}","number":"${paymentInfo.no}","expirationMonth":${paymentInfo.month},"expirationYear":${paymentInfo.year},"cvc":${paymentInfo.cvv}}&address=ADDLATER`)
		return response.data.data
	} catch(err) {
		console.error(err)
		return false
	}
}

export default { getOrders, checkoutOrder }