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

export default { getProduct }