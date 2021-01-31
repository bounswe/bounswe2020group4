import axios from 'axios'

const baseUrl = 'http://3.141.25.245:8080/report'

const reportProduct = async (productId, message) => {
	const response = await axios.post(`${baseUrl}/product?productId=${productId}&message=${message}`)
	return response
}

const reportComment = async (commentId, message) => {
	const response = await axios.post(`${baseUrl}/comment?commentId=${commentId}&message=${message}`)
	return response
}

export default { reportProduct, reportComment }
