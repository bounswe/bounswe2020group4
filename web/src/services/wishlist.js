import axios from 'axios'

const addToWishlist = async (customerId, productId) => {
  const response = await axios.get(`http://3.138.113.101:8080/like?customerId=${customerId}&productId=${productId}`)
  return response
}

export default { addToWishlist }