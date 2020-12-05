import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080'

export const getCategories = async () => {
    const response = await axios.get(`${baseUrl}/categories`)
    return response.data.data.categories
}
