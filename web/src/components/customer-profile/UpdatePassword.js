import React from 'react'

import './ProfileInfo.css'

const UpdatePassword = () => {

	return(
		<div className='container p-3'>
			<div className='row m-3'>
				<div className='row pb-3'>
					<h3 className='text-container font-weight-bold'>Change password</h3>
				</div>
				<form className="form-container">
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='oldPassword'>Current password</label>
							</div>
							<div className='col'>
								<input type='text' className='form-control form-control-md text-left' id='oldPassword' value=''/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='newPassword'>New password</label>
							</div>
							<div className='col'>
								<input type='text' className='form-control form-control-md text-left' id='newPassword' value=''/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='newPassword'>New password (again)</label>
							</div>
							<div className='col'>
								<input type='text' className='form-control form-control-md text-left' id='newPassword' value=''/>
							</div>
						</div>
					</div>

					<div className='text-right'>
						<button type='submit' className='btn btn-danger'>Update your password</button>
					</div>

				</form>
			</div>
		</div>
	)
}

export default UpdatePassword