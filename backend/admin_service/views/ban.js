const Product = require("../models/product").Product;
const ObjectId = require("mongoose").Types.ObjectId;
const Vendor = require("../models/vendor").Vendor;
const Customer = require("../models/customer").Customer;


/**
 * Adds comment to given product, from given customer, with given text.
 *
 * @param {
 *  vendorId: String,
 *  changeParameter: {
 *           status: String
 *        }
 * } params 
 *
 * @returns {commentId | error}
 */
module.exports.changeStatusForVendor = async (params) => {
  try {
    checker = false;
    const vendor = await Vendor.findById(ObjectId(params.vendorId));

    if(vendor === null){
      return false;
    }
    console.log(typeof(vendor))

    if( (vendor.status == "banned" && params.status === "verified")  ||
        (vendor.status !== "banned" && params.status === "banned") ){
          let innerParameter = {"status":params.status}
          
          console.log("INNER PARAMETER ",innerParameter)
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