const mongoose = require("mongoose");

/**
 * Schema of a Vendor object in the database, vendor documents resides
 * in 'vendors' collection.
 */
module.exports.ProductInfos = mongoose.model(
  "ProductInfos",
  new mongoose.Schema([
    
      {
        attributes: [{name:String , value:String}],
        stockValue : Number
      }
    
  ]),
  "ProductInfos"
);
