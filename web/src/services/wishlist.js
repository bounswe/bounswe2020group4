import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080'

const addToWishlist = async (customerId, productId) => {
  const response = await axios.get(`${baseUrl}/like?customerId=${customerId}&productId=${productId}`)
  return response
}

const getWishlist = async (customerId) => {
  const response = await axios.get(`${baseUrl}/wishlist?customerId=${customerId}`)
  return response.data.data.products
}

const isInWishlist = async (customerId, productId) => {
  const response = await axios.get(`${baseUrl}/wishlist?customerId=${customerId}`)
  console.log(response.data.data.products.find(p => p.id === productId) !== undefined)
  return response.data.data.products.find(p => p.id === productId) !== undefined
}

export default { addToWishlist, getWishlist, isInWishlist }