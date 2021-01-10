import React from 'react'
import { removeProduct, banUser, banVendor } from '../../services'

const ReportedProductCard = ({ report, fetchReports }) => {
    const attributes = []
    report.product.productInfos[0].attributes.forEach((attribute) => {
        attributes.push(attribute.name)
    })

    const attributeValues = []
    report.product.productInfos.forEach((productInfo) => {
        productInfo.attributes.forEach((attribute) => {
            if(!attributeValues.includes(attribute.value)){
                attributeValues.push(attribute.value)
            }
        })
    })

    const removeProductHandler = async () => {
        if(await removeProduct(report.product.id)) {
            console.log("Product Removed and reports fetched again")
            fetchReports()
        } else {
            alert("Something went wrong while removing user")
        }
    }

    const banVendorHandler = async () => {
        //!!!!!!!!!!Vendor name i yolluyoruz suan placeholder olsun diye id yollamamiz lazim
        if(await banVendor(report.product.vendor.name)) {
            console.log("Vendor banned and reports fetched again")
            fetchReports()
        } else {
            alert("Something went wrong while banning vendor")
        }
    }

    return (
        <div className='container-fluid border rounded p-2 mb-4' style={{ width: '48%' }}>
            <div className='fw-bold'>Report Message: {report.reportMessage}</div>
            <hr />
            <div className='row'>
                <div className='col-4'>
                    <img className='img-fluid rounded' src={report.product.imageUrl}  alt='product img'/>
                </div>
                <div className='col-8'>
                    <div>Name: {report.product.name}</div>
                    <div>Description: {report.product.description}</div>
                    <div>Attributes: {attributes.map((attr) => `${attr} `)}</div>
                    <div>Attribute Values: {attributeValues.map((value) => `${value} `)}</div>
                    <div>Original Price: {report.product.originalPrice} | Price: {report.product.price}</div>
                    <div>Brand: {report.product.brand}</div>
                    <div>Vendor: {report.product.vendor.name}</div>
                </div>
            </div>
            <div className='d-flex justify-content-center py-2'>
                <button className='btn btn-outline-danger btn-lg mx-4' onClick={removeProductHandler}>Remove Product</button>
                <button className='btn btn-danger btn-lg mx-4'onClick={banVendorHandler}>Ban Vendor</button>
            </div>
        </div>
    )
}

export default ReportedProductCard