import axios from 'axios'

const baseUrl = 'http://3.141.25.245:8080/comment'

const giveFeedback = async (productId, userId, comment, rating) => {
	const response = await axios.post(`${baseUrl}?userId=${userId}&productId=${productId}&comment=${comment}&rating=${rating}`)
	return response
}

export default { giveFeedback }