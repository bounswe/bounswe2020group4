import React from 'react'
import { removeComment, banUser } from '../../services'

const ReportedCommentCard = ({ report, fetchReports, removeReportedComment }) => {

    const removeCommentHandler = async () => {
        // removeReportedComment(report)
        if(await removeComment(report.commentDetails?._id)) {
            console.log("Comment Removed and reports fetched again")
            fetchReports()
        } else {
            alert("Something went wrong while removing comment")
        }
        fetchReports()
    }

    const banUserHandler = async () => {
        // removeReportedComment(report)
        if(await banUser(report.commentDetails?.userId)) {
            console.log("User banned and reports fetched again")
            fetchReports()
        } else {
            alert("Something went wrong while banning user")
        }
        fetchReports()
    }

    return (
        <div className='mb-3' style={{ width: '48%' }}>
            <div className='container-fluid border rounded p-2' style={{ height: '100%' }}>
                <div className='row'>
                    <div className='col-7 col-md-9'>
                        <div className='fw-bold'>Report Message: {report.message}</div>
                        <div>Comment: {report.commentDetails?.text}</div>
                    </div>
                    <div className='col-5 col-md-3'>
                        <button className='btn btn-outline-danger my-2' onClick={removeCommentHandler}>Remove Comment</button>
                        <button className='btn btn-danger my-2' onClick={banUserHandler} style={{ width: '100%' }}>Ban User</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ReportedCommentCard