import axios from 'axios'

const baseUrl = "http://3.141.25.245:8080/admin"

export const getReportedProducts = async () => {
    try {
        const response = await axios.get(`${baseUrl}/report/product`)
        console.log(response.data.data.productReport)
        return response.data.data.productReport
    } catch (err) {
        console.error(err)
        return null
    }

}

export const removeProduct = async (productId, vendorId) => {
    try {
        const response = await axios({
            method: 'delete',
            url: `http://3.141.25.245:8080/vendor/product?vendorId=${vendorId}`,
            data: {
                "productId": productId
            }
        })
        if(response.status === 200) {
            return true
        }
        return false
    } catch (err) {
        console.error(err)
        return false
    }
}

export const banVendor = async (vendorId, productId) => {
    try {
        // const response = await axios.post(`${baseUrl}/vendor/changeStatus/${vendorId}`, { status: "banned" })
        const response = await axios({
            method: 'post',
            url: `${baseUrl}/vendor/changeStatus/${vendorId}`,
            data: {
                "status": "banned",
                "productId": productId
            }
        })
        if(response.status === 200){
            return true
        }
        return false
    } catch (err) {
        console.error(err)
        return false
    }
}


export const getReportedComments = async () => {
    try {
        const response = await axios.get(`${baseUrl}/report/comment`)
        console.log(response.data.data.commentReport)
        return response.data.data.commentReport
    } catch (err) {
        console.error(err)
        return null
    }

}

export const removeComment = async (commentId) => {
    try {
        const response = await axios.delete(`http://3.141.25.245:8080/comment?id=${commentId}`)
        if(response.status === 200) {
            return true
        }
        return false
    } catch (err) {
        console.error(err)
        return false
    }
}

export const banUser = async (customerId, commentId) => {
    try {
        const response = await axios({
            method: 'post',
            url: `${baseUrl}/customer/changeStatus/${customerId}`,
            data: {
                "status": "banned",
                "commentId": commentId
            }
        })
        if(response.status === 200){
            return true
        }
        return false
    } catch (err) {
        console.error(err)
        return false
    }
}