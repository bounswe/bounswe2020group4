const Product = require("../models/product").Product;
const ObjectId = require("mongoose").Types.ObjectId;
const Vendor = require("../models/vendor").Vendor;
const Customer = require("../models/customer").Customer;
const Comment = require("../models/comment").Comment;
const CommentReport = require("../models/commentReport").CommentReport;
const nodemailer = require("nodemailer");



/**
 * This function changes the status of vendor. 
 *
 * @param {
 *  vendorId: String,
 *  changeParameter: {
 *           status: String
 *        }
 * } params 
 *
 * @returns {true | false}
 */
module.exports.changeStatusForVendor = async (params) => {
  try {
    checker = false;
    const vendor = await Vendor.findById(ObjectId(params.vendorId));

    if(vendor === null){
      return false;
    }

    if( (vendor.status == "banned" && (params.status === "verified" || params.status === "not-verified"))  ||
    (vendor.status === "verified" && (params.status === "not-verified" || params.status === "banned"  )) ||
    (vendor.status === "not-verified" && (params.status === "verified" || params.status === "banned"  ) ) 
 ){
          let innerParameter = {"status":params.status}
          
          await Vendor.findByIdAndUpdate(ObjectId(params.vendorId),innerParameter, function(err, result){

            if(err){
                checker = false
            }
            else{
                checker = true
            }
    
        })
       
    }
    if (params.status === "banned" && checker){
      sendBannedInformation("vendor", params.vendorId);
     }
    return checker
  } catch (error) {
    console.log(error);
    return error;
  }
};




/**
 * This function changes the status of customer. 
 *
 * @param {
  *  vendorId: String,
  *  changeParameter: {
  *           status: String
  *        }
  * } params 
  *
  * @returns {true | false}
  */
 module.exports.changeStatusForCustomer = async (params) => {
   
   try {
     checker = false;
     const customer = await Customer.findById(ObjectId(params.customerId));
 
     if(customer === null){
       return false;
     }
 
     if( (customer.status == "banned" && (params.status === "verified" || params.status === "not-verified"))  ||
         (customer.status === "verified" && (params.status === "not-verified" || params.status === "banned"  )) ||
         (customer.status === "not-verified" && (params.status === "verified" || params.status === "banned"  ) ) 
      ){
           let innerParameter = {"status":params.status}

           if(params.status === "banned"){
            await Comment.deleteOne({ _id: ObjectId(params.commentId) },{new: true, useFindAndModify: false});
            await CommentReport.deleteOne({ userId: ObjectId(params.customerId) },{new: true, useFindAndModify: false});
          }
           
           await Customer.findByIdAndUpdate(ObjectId(params.customerId),innerParameter, function(err, result){
            
             if(err){
                 checker = false
             }
             else{
                 checker = true
             }
     
         })
        
     }
     if(params.status === "banned" && checker){
      sendBannedInformation("customer", params.customerId);
     }
     return checker
   } catch (error) {
     console.log(error);
     return error;
   }
 };

/**
 * This function send email to banned user. 
 *
 * @param {
  *  userType: String,
  *  userId: String
  * } params 
  *
  */

 async function sendBannedInformation(userType, userId) {
  var transporter = nodemailer.createTransport({
    service: "Gmail",
    auth: {
      user: "buyoboun@gmail.com",
      pass: "buyo1234",
    },
  });
  const collection = userType === "customer" ? Customer : Vendor;
  const account = await collection.findOne({ _id: ObjectId(userId) });
  var mailOptions = {
    from: '"BUYO" <buyoboun@gmail.com>',
    to: account.email,
    subject: "Your BUYO verification e-mail",
    text:"Your BUYO account is banned. Please contact with admin for further questions."
  };

  transporter.sendMail(mailOptions, function (error, info) {
    if (error) {
      console.log(error);
    } else {
      console.log("Email sent: " + info.response);
    }
  });
}