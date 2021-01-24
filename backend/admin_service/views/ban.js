const Product = require("../models/product").Product;
const ObjectId = require("mongoose").Types.ObjectId;
const Vendor = require("../models/vendor").Vendor;
const Customer = require("../models/customer").Customer;


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
           
           await Customer.findByIdAndUpdate(ObjectId(params.customerId),innerParameter, function(err, result){
 
             if(err){
                 checker = false
             }
             else{
                 checker = true
             }
     
         })
        
     }
 
     return checker
   } catch (error) {
     console.log(error);
     return error;
   }
 };