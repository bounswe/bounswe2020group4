import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/order'

const getOrders = async (userId, userType) => {
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

const updateOrderStatus = async (userType, userId, status, orderId) => {
	try {
		const response = await axios.patch(`${baseUrl}?userType=${userType}&userId=${userId}&status=${status}&orderId=${orderId}`)
		return response.data.status.code
	} catch(err) {
		console.error(err)
		return null
	}
}

const updateProductOrderStatus = async (userType, userId, status, orderId, productId) => {
	try {
		const response = await axios.patch(`${baseUrl}?userType=${userType}&userId=${userId}&status=${status}&orderId=${orderId}&productId=${productId}`)
		return response.data.status.code
	} catch(err) {
		console.error(err)
		return null
	}
}

export default { getOrders, checkoutOrder, updateOrderStatus, updateProductOrderStatus }
