import axios from 'axios'

const baseUrl = 'http://localhost:3001/products'

const getProduct = async (id) => {
  const response = await axios.get(`${baseUrl}/${id}`)
  return response.data
}

export default { getProduct }