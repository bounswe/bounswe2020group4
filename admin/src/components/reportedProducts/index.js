import React, { useEffect, useState } from 'react'
import Header from '../Header'
import ReportedProductCard from './ReportedProductCard'
import { getReportedProducts } from '../../services'

const ReportedProducts = () => {
    const [reportedProducts, setReportedProducts] = useState([])

    useEffect(() => {
        fetchReports()
    }, [])

    const renderProductCards = () => {
        return reportedProducts.map((report) => <ReportedProductCard key={report.reportId} report={report} fetchReports={fetchReports}/>)
    }

    const fetchReports = async () => {
        const fetchedReports = await getReportedProducts()
        setReportedProducts(fetchedReports)
    }

    return (
        <div>
            <Header />
            <h3 className='m-3'>Reported Products</h3>
            <div className='container-fluid d-flex flex-wrap'>
                {renderProductCards()}
            </div>
        </div>
    )
}

export default ReportedProducts