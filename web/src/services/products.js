import axios from 'axios'


const baseUrl = 'http://3.138.113.101:8080/product'



const getProduct = async (id) => {
  const response = await axios.get(`${baseUrl}?id=${id}`)
  return response.data.data.result
}

export default { getProduct }