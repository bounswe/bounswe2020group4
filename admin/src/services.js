import axios from 'axios'

const baseUrl = "http://3.138.113.101:8080/admin"

export const getReportedProducts = async () => {
    const mockData = [
        {
            reportId: "amsd12omqsdk1m23",
            reportMessage: "The description of this product include profanity",
            product: {
                "category": [
                    "Electronics",
                    "Phones"
                ],
                "productInfos": [
                    {
                        "attributes": [
                            {
                                "name": "screenSize",
                                "value": "5.5 Inch"
                            },
                            {
                                "name": "RAM",
                                "value": "4 GB"
                            },
                            {
                                "name": "diskSize",
                                "value": "64 GB"
                            }
                        ],
                        "stockValue": 21
                    },
                    {
                        "attributes": [
                            {
                                "name": "screenSize",
                                "value": "5.5 Inch"
                            },
                            {
                                "name": "RAM",
                                "value": "4 GB"
                            },
                            {
                                "name": "diskSize",
                                "value": "128 GB"
                            }
                        ],
                        "stockValue": 18
                    }
                ],
                "description": "Uncover the Ultimate Design. Beauty Beyond the Visual",
                "name": "Samsung S8",
                "price": 2800,
                "originalPrice": 3000,
                "imageUrl": "https://elasticbeanstalk-us-east-2-334058266782.s3.amazonaws.com/images/16089907472046",
                "rating": 4,
                "brand": "Samsung",
                "vendor": {
                    "name": "JohnsShop",
                    "rating": 4.23
                },
                "id": "5fe86281a17abd0012d601e6"
            }
        },
        {
            reportId: "asdanskdj119230",
            reportMessage: "The picture is not appropriate for children blah blah blah this is just excessive message so that we can test how a long message displays",
            product: {
                "category": [
                    "Electronics",
                    "Phones"
                ],
                "productInfos": [
                    {
                        "attributes": [
                            {
                                "name": "screenSize",
                                "value": "5.5 Inch"
                            },
                            {
                                "name": "RAM",
                                "value": "8 GB"
                            },
                            {
                                "name": "diskSize",
                                "value": "64 GB"
                            }
                        ],
                        "stockValue": 54
                    },
                    {
                        "attributes": [
                            {
                                "name": "screenSize",
                                "value": "5.5 Inch"
                            },
                            {
                                "name": "RAM",
                                "value": "8 GB"
                            },
                            {
                                "name": "diskSize",
                                "value": "128 GB"
                            }
                        ],
                        "stockValue": 74
                    }
                ],
                "description": "Uncover the Ultimate Design. Beauty Beyond the Visual",
                "name": "Samsung S9",
                "price": 4000,
                "originalPrice": 4200,
                "imageUrl": "https://elasticbeanstalk-us-east-2-334058266782.s3.amazonaws.com/images/16089907472057",
                "rating": 4.4,
                "brand": "Samsung",
                "vendor": {
                    "name": "Ahmet",
                    "rating": 3.22
                },
                "id": "5fe86281a17abd0012d601e7"
            }
        },
        {
            reportId: "amsd12omqsdk1m23",
            reportMessage: "The description of this product include profanity",
            product: {
                "category": [
                    "Electronics",
                    "Phones"
                ],
                "productInfos": [
                    {
                        "attributes": [
                            {
                                "name": "screenSize",
                                "value": "5.5 Inch"
                            },
                            {
                                "name": "RAM",
                                "value": "4 GB"
                            },
                            {
                                "name": "diskSize",
                                "value": "64 GB"
                            }
                        ],
                        "stockValue": 21
                    },
                    {
                        "attributes": [
                            {
                                "name": "screenSize",
                                "value": "5.5 Inch"
                            },
                            {
                                "name": "RAM",
                                "value": "4 GB"
                            },
                            {
                                "name": "diskSize",
                                "value": "128 GB"
                            }
                        ],
                        "stockValue": 18
                    }
                ],
                "description": "Uncover the Ultimate Design. Beauty Beyond the Visual",
                "name": "Samsung S8",
                "price": 2800,
                "originalPrice": 3000,
                "imageUrl": "https://elasticbeanstalk-us-east-2-334058266782.s3.amazonaws.com/images/16089907472046",
                "rating": 4,
                "brand": "Samsung",
                "vendor": {
                    "name": "JohnsShop",
                    "rating": 4.23
                },
                "id": "5fe86281a17abd0012d601e6"
            }
        },
        {
            reportId: "asdanskdj119230",
            reportMessage: "The picture is not appropriate for children blah blah blah this is just excessive message so that we can test how a long message displays",
            product: {
                "category": [
                    "Electronics",
                    "Phones"
                ],
                "productInfos": [
                    {
                        "attributes": [
                            {
                                "name": "screenSize",
                                "value": "5.5 Inch"
                            },
                            {
                                "name": "RAM",
                                "value": "8 GB"
                            },
                            {
                                "name": "diskSize",
                                "value": "64 GB"
                            }
                        ],
                        "stockValue": 54
                    },
                    {
                        "attributes": [
                            {
                                "name": "screenSize",
                                "value": "5.5 Inch"
                            },
                            {
                                "name": "RAM",
                                "value": "8 GB"
                            },
                            {
                                "name": "diskSize",
                                "value": "128 GB"
                            }
                        ],
                        "stockValue": 74
                    }
                ],
                "description": "Uncover the Ultimate Design. Beauty Beyond the Visual",
                "name": "Samsung S9",
                "price": 4000,
                "originalPrice": 4200,
                "imageUrl": "https://elasticbeanstalk-us-east-2-334058266782.s3.amazonaws.com/images/16089907472057",
                "rating": 4.4,
                "brand": "Samsung",
                "vendor": {
                    "name": "Ahmet",
                    "rating": 3.22
                },
                "id": "5fe86281a17abd0012d601e7"
            }
        }
    ]

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
            url: `http://3.138.113.101:8080/vendor/product?vendorId=${vendorId}`,
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
    const mockData = [
        {
            reportId: 'aksjndkajxowiedla', 
            reportMessage: "This comment includes profanity and hate speech. Delete it immediately. What kind of organization is this Jesus christ cmon man!",
            comment: {
                "id": "5fe8af602e7b430019d8004c",
                "rating": 4.23,
                "text": "niceee",
                "owner": {
                    "username": "Koray Cetin",
                    "email": "koray@gmail.com"
                }
            }
        },
        {
            reportId: 'aksjndkaj191xowiedla', 
            reportMessage: "This comment includes profanity and hate speech. Delete it immediately. What kind of organization is this Jesus christ cmon man!",
            comment: {
                "id": "5fe8af602e7b430019d8004c",
                "rating": 4.23,
                "text": "niceee alright alright alright. This sweater makes bank robberies really easy. Would recommend it to every thief.",
                "owner": {
                    "username": "Koray Cetin",
                    "email": "koray@gmail.com"
                }
            }
        },
        {
            reportId: 'aksjndkajx11owiedla', 
            reportMessage: "This guy wrote game of thrones spoilers as a comment",
            comment: {
                "id": "5fe8af602e7b430019d8004c",
                "rating": 4.23,
                "text": "Ned stark dies at the end of season 1. Nearly all of the starks die at the red wedding episode.",
                "owner": {
                    "username": "Koray Cetin",
                    "email": "koray@gmail.com"
                }
            }
        },
        {
            reportId: 'aksjndka1jxowiedla', 
            reportMessage: "No explanations necessary",
            comment: {
                "id": "5fe8af602e7b430019d8004c",
                "rating": 4.23,
                "text": "Interstellar was only ok.",
                "owner": {
                    "username": "Koray Cetin",
                    "email": "koray@gmail.com"
                }
            }
        }
    ]

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
        // const response = await axios({
        //     method:'delete',
        //     url: `http://3.138.113.101:8080/comment`,
        //     data: {
        //         id: commentId
        //     }
        // })
        const response = await axios.delete(`http://3.138.113.101:8080/comment?id=${commentId}`)
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