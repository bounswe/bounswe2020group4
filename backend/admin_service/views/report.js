const Comment = require("../models/comment").Comment;
const Product = require("../models/product").Product;
const ProductReport = require("../models/productReport").ProductReport;
const CommentReport = require("../models/commentReport").CommentReport;
const ObjectId = require("mongoose").Types.ObjectId;
const { ErrorMessage } = require("../constants/error");

/**
 * Adds comment to given product, from given customer, with given text.
 *
 * @param {
 *  userId: String,
 *  productId: String,
 *  comment: String
 * } params 
 *
 * @returns {commentId | error}
 */
module.exports.reportProduct = async (params) => {
  try {
    if (!params.productId || !params.message) {
      return { success: false, message: ErrorMessage.MISSING_PARAMETER };
    }
    const product = await Product.findById(ObjectId(params.productId));

    if (!product) {
      return {
        success: false,
        message: ErrorMessage.PRODUCT_NOT_FOUND,
      };
    }
    var productReport = await ProductReport.create({ productId: ObjectId(params.productId), message: params.message });

    return {
      success: true,
      id: productReport._id.toString(),
    };
  } catch (error) {
    return { success: false, message: error.message || error };
  }
};


/**
 * Adds comment to given product, from given customer, with given text.
 *
 * @param {
  *  userId: String,
  *  productId: String,
  *  comment: String
  * } params
  *
  * @returns {commentId | error}
  */
 module.exports.reportComment = async (params) => {
   try {
     if (!params.commentId || !params.message) {
       return { success: false, message: ErrorMessage.MISSING_PARAMETER };
     }
     const comment = await Comment.findById(ObjectId(params.commentId));
 
     if (!comment) {
       return {
         success: false,
         message: ErrorMessage.COMMENT_NOT_FOUND,
       };
     }
     var commentReport = await CommentReport.create({ commentId: ObjectId(params.commentId), message: params.message });
 
     return {
       success: true,
       id: commentReport._id.toString(),
     };
   } catch (error) {
     return { success: false, message: error.message || error };
   }
 };




 /**
 * Get reported comments 
 */
module.exports.getCommentReports = async () => {
   try { 
    const commentReportList = await CommentReport.find();
    let finalReportList = [] 
    const waitFor = (ms) => new Promise(r => setTimeout(r, ms));


     commentReportList.forEach(async function(currentReport){
      let comment = await Comment.findById(ObjectId(currentReport.commentId));

      let commentReport = {message: currentReport.message,commentDetails:comment}
      finalReportList.push(commentReport)

     })

     await waitFor(300);

     return {
      success: true,
      data: finalReportList
    };
     
    } catch (error) {
      return { success: false, message: error.message || error };
    }
  };




 /**
 * Get reported products 
 */


module.exports.getProductReports = async () => {
  try { 
   const productReportList = await ProductReport.find();
   var finalReportList = [] 
   const waitFor = (ms) => new Promise(r => setTimeout(r, ms));

    productReportList.forEach(async function(currentReport){
     let product = await Product.findById(ObjectId(currentReport.productId));

     let productReport = { message: currentReport.message, productDetails:product}
     finalReportList.push(productReport)
    })

  
    await waitFor(300);
   
   
      return {
        success: true,
        data: finalReportList
      };
    

    
    
   } catch (error) {
     return { success: false, message: error.message || error };
   }
 };

