import React, {useState} from 'react'

import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';


import DefaultProductImage from '../images/default-product-detail-image.png'

import './OrderDetails.css'

const ProductOrder = ({imgUrl, name, brand, price, isDelivered}) => {
  const [open, setOpen] = useState(false);
  const handleClickOpen = () => {
    setOpen(true);
  }
  const handleClose = () => {
    setOpen(false);
  }
  return(
    <div className="product-order">
      <img className="product-image" src={imgUrl || DefaultProductImage} alt='product'/>
      <div className="product-order-info">
        <div>{name}</div>
        <div>{brand}</div>
        <div>{price}</div>
      </div>
      {isDelivered && <button className="add-comment-button" onClick={handleClickOpen}>Add Comment</button>}
      <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">Add Comment</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Add your comments on this product. Please describe your shopping experience, product quality etc.
          </DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Comment"
            type="text"
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancel
          </Button>
          <Button onClick={handleClose} color="primary">
            Add Comment
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  )
}


const OrderDetails = ({isDelivered}) => {
  return(
    <div className="order-details-container">
      <div className="order-details-top">
        <div>
          <ProductOrder name="kazak" brand="mavi" price="200 tl" isDelivered={isDelivered}/>   
          <ProductOrder name="çorap" brand="lcw" price="10 tl" isDelivered={isDelivered}/> 
        </div>  
        <div className="order-button-container">
          {!isDelivered ? <button className="order-page-button">Cancel Order</button> : null}
          <button className="order-page-button" >Message Vendor</button>
        </div>
      </div>
      <div className="address-container"> Address: Rumeli Hisarı, Hisar Üstü Nispetiye Cd No:7, 34342 Sarıyer/İstanbul
      </div>
    </div>
  )
}

export default OrderDetails;