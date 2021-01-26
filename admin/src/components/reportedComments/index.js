import React, { useState, useEffect } from 'react'
import Header from '../Header'
import ReportedCommentCard from './ReportedCommentCard'
import { getReportedComments } from '../../services'

const ReportedComments = () => {
    const [reports, setReports] = useState([])

    useEffect(() => {
        fetchReports()
    }, [])

    const fetchReports = async () => {
        const fetchedReports = await getReportedComments()
        setReports(fetchedReports)
    }

    const removeReportedComment = (report) => {
        const newReports = reports.filter((rep) => rep != report)
        setReports(newReports)
    }

    const renderCommentCards = () => {
        return reports.map((report) => <ReportedCommentCard key={report.reportId} report={report} fetchReports={fetchReports} removeReportedComment={removeReportedComment} />)
    }

    return (
        <div>
            <Header />
            <h3 className='m-3'>Reported Comments</h3>
            <div className='container-fluid d-flex flex-wrap justify-content-evenly'>
                {renderCommentCards()}
            </div>
        </div>
    )
}

export default ReportedComments