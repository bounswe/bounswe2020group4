const Product = require("../models/product").Product;
const Vendor = require("../models/vendor").Vendor;
const Comment = require("../models/comment").Comment;
const Customer = require("../models/customer").Customer;
const ObjectId = require("mongoose").Types.ObjectId;

/**
 * Returns the whole categories in the app as a tree.
 *
 * @object {
 *  path: String,
 *  subcategories: [CategoryObject],
 *  name: String
 * } CategoryObject
 * @returns {[
 *  CategoryObject
 * ]}
 */
module.exports.getProductCategories = async () => {
  try {
    const rawResult = await Product.aggregate([
      {
        $group: {
          _id: 0,
          distinctCategories: { $addToSet: "$category" },
        },
      },
    ]);
    let categories = [];

    rawResult[0].distinctCategories.forEach((distinctCategory) => {
      let temp = categories;
      let index = 1;
      distinctCategory.forEach((category) => {
        if (!temp.some((subcategory) => subcategory.name === category)) {
          temp.push({
            name: category,
            subcategories: [],
            path: distinctCategory.slice(0, index).join(","),
          });
        }

        temp = temp.find((subcategory) => subcategory.name === category).subcategories;

        index++;
      });
    });

    return categories;
  } catch (error) {
    console.log(error);
    return error;
  }
};

/**
 * Gets products within a specific category or with a search parameter
 * Filters and sort them with certain parameters and returns the result.
 *
 * @param {
 *  categories: [String],
 *  search: String,
 *  sortingFactor: String,
 *  sortingType: descending | ascending
 *  color: String,
 *  brand: String
 * } params
 *
 * @returns {[
 *  Product
 * ]}
 */
module.exports.getProducts = async (params) => {
  try {
    let products;

    finalProductList = []

    if (params.categories) {
      products = await Product.find({ category: { $all: JSON.parse(params.categories) } });
    } else if (params.search) {
      products = await Product.find({ name: { $regex: params.search, $options: "i" } });
    }


    var filterCriterias = [];
    var filteringConfig = {
      screenSize: "Screen Size",
      RAM: "RAM",
      diskSize: "Disk Size",
      noiseCancelling: "Noise Cancelling",
      aroma: "Aroma",
      size: "Size",
      color: "Color",
    };
    /*
     * Important Note: I can do it this operation in thhe function that we set product Id.
     * However when I create filtering creterias at the other part, it shows only filtered category.
     * For instance if user filter RAM as 4 GB, we wont be able to see other RAM values.
     * Thats why I create filtering part here.
     */

     
    products.forEach(function (product) {

      /* Since we cannot change the structure of product , we have to create a temp product and return it.*/
      tempProduct = {
        "category": product.category,
        "description": product.description,
        "name": product.name,
        "price": product.price,
        "originalPrice": product.originalPrice,
        "imageUrl": product.imageUrl,
        "rating": product.rating,
        "brand": product.brand,
        "vendorId": product.vendorId,
        "id": product.id
    }
      
      if ("productInfos" in product) {
        productInfos = {}
      
        tempProduct["productInfos"]  = JSON.parse(product.productInfos)
        
       
        tempProduct["productInfos"].forEach(function (property) {
          property["attributes"].forEach(function (attribute) {
            if (filterCriterias.length === 0) {
              filterCriterias.push({
                name: attribute.name,
                displayName: filteringConfig[attribute.name],
                possibleValues: [attribute.value],
              });
            } else {
              var nameCheckerObject = filterCriterias.filter(function (currentCriteria) {
                return currentCriteria.name === attribute.name;
              });

              if (nameCheckerObject.length > 0) {
                var currentCriteria;
                filterCriterias.forEach(function (criteria) {
                  if (criteria.name === attribute.name) {
                    currentCriteria = criteria;
                  }
                });

                var valueChecker = currentCriteria["possibleValues"].some(function (currentValue) {
                  return attribute.value === currentValue;
                });

                if (!valueChecker) {
                  currentCriteria["possibleValues"].push(attribute.value);
                }
              } else {
                filterCriterias.push({
                  name: attribute.name,
                  displayName: filteringConfig[attribute.name],
                  possibleValues: [attribute.value],
                });
              }
            }
          });
        });
      }

      finalProductList.push(tempProduct)
    });

    if (!!params.sortingFactor) {
      try {
        if (typeof(finalProductList[0][params.sortingFactor]) == "number") {
          finalProductList = finalProductList.sort(
            (product1, product2) =>
              (params.sortingType == "descending" ? -1 : 1) *
              (product1[params.sortingFactor] - product2[params.sortingFactor])
          );
        } else {
          finalProductList = finalProductList.sort(
            (product1, product2) =>
              (params.sortingType == "descending" ? -1 : 1) *
              ("" + product1[params.sortingFactor]).localeCompare(product2[params.sortingFactor])
          );
        }
      } catch {
        console.log("Check your sorting factor"); // No need to return this value. I put it here for debugging.
      }
    }

    if (!!params.subcategory) {
      finalProductList = finalProductList.filter(function (product) {
        return product.category.indexOf(params.subcategory) > -1;
      });
    }

    if (!!params.brand) {
      finalProductList = finalProductList.filter(function (product) {
        return product.brand.indexOf(params.brand) > -1;
      });
    }

    if (!!params.color) {
      finalProductList = finalProductList.filter(function (product) {
        var checker = false;

        try {
          checker = product["productInfos"].some(function (attributes) {
            return attributes["attributes"].some(function (attribute) {
              return attribute.name === "color" && attribute.value === params.color;
            });
          });
        } catch (error) {
          checker = false;
          console.log(error);
        }
        return checker;
      });
    }

    if (!!params.size) {
      finalProductList = finalProductList.filter(function (product) {
        var checker = false;

        try {
          checker = product["productInfos"].some(function (attributes) {
            return attributes["attributes"].some(function (attribute) {
              return attribute.name === "size" && attribute.value === params.size;
            });
          });
        } catch (error) {
          checker = false;
          console.log(error);
        }
        return checker;
      });
    }

    if (!!params.screenSize) {
      finalProductList = finalProductList.filter(function (product) {
        var checker = false;

        try {
          checker = product["productInfos"].some(function (attributes) {
            return attributes["attributes"].some(function (attribute) {
              return attribute.name === "screenSize" && attribute.value === params.screenSize;
            });
          });
        } catch (error) {
          checker = false;
          console.log(error);
        }
        return checker;
      });
    }

    if (!!params.aroma) {
      finalProductList = finalProductList.filter(function (product) {
        var checker = false;

        try {
          checker = product["productInfos"].some(function (attributes) {
            return attributes["attributes"].some(function (attribute) {
              return attribute.name === "aroma" && attribute.value === params.aroma;
            });
          });
        } catch (error) {
          checker = false;
          console.log(error);
        }
        return checker;
      });
    }

    if (!!params.RAM) {
      finalProductList = finalProductList.filter(function (product) {
        var checker = false;

        try {
          checker = product["productInfos"].some(function (attributes) {
            return attributes["attributes"].some(function (attribute) {
              return attribute.name === "RAM" && attribute.value === params.RAM;
            });
          });
        } catch (error) {
          checker = false;
          console.log(error);
        }
        return checker;
      });
    }

    if (!!params.diskSize) {
      finalProductList = finalProductList.filter(function (product) {
        var checker = false;

        try {
          checker = product["productInfos"].some(function (attributes) {
            return attributes["attributes"].some(function (attribute) {
              return attribute.name === "diskSize" && attribute.value === params.diskSize;
            });
          });
        } catch (error) {
          checker = false;
          console.log(error);
        }
        return checker;
      });
    }

    if (!!params.noiseCancelling) {
      finalProductList = finalProductList.filter(function (product) {
        var checker = false;

        try {
          checker = product["productInfos"].some(function (attributes) {
            return attributes["attributes"].some(function (attribute) {
              return attribute.name === "noiseCancelling" && attribute.value === params.noiseCancelling;
            });
          });
        } catch (error) {
          checker = false;
          console.log(error);
        }
        return checker;
      });
    }

    finalProductList = await Promise.all(
      finalProductList.map(async (product) => {

        const vendor = await Vendor.findById(product.vendorId);

        product.vendor = {
          name: vendor.name,
          rating: vendor.rating,
        };
        product.id = product.id.toString();

        delete product._id;
        delete product.vendorId;

        return product;
      })
    );



    if(params.vendorName){
      finalProductList = finalProductList.filter(product => product.vendor.name == params.vendorName)
    }

    return { productList: finalProductList, filterCriterias };
  } catch (error) {
    console.log(error);
    return error;
  }
};

/**
 * Gets an product id and returns that product if it exists.
 *
 * @param {id: String} params
 * @returns {
 *  Product
 * }
 */
module.exports.getProduct = async (params) => {
  try {
    let product;


    

    if (params.id) {
      product = await Product.findOne({ _id: ObjectId(params.id) });
    }

    if (!product) {
      return false
    }
      product = product.toJSON();

      let comments = await Comment.find({ productId: ObjectId(product._id) });
      comments = await Promise.all(
        comments.map(async (comment) => {
          comment = comment.toJSON();
          const user = (await Customer.findOne({ _id: comment.userId })).toJSON();

          return {
            id: comment._id.toString(),
            rating: comment.rating,
            text: comment.text,
            owner: {
              username: user.name,
              email: user.email,
            },
          };
        })
      );

      const vendor = await Vendor.findOne({ _id: product.vendorId });



      delete product._id;
      delete product.vendorId;


    /* Since we cannot change the structure of product , we have to create a temp product and return it.*/
    tempProduct = {
      "category": product.category,
      "description": product.description,
      "name": product.name,
      "price": product.price,
      "originalPrice": product.originalPrice,
      "imageUrl": product.imageUrl,
      "rating": product.rating,
      "brand": product.brand,
      "vendorId": product.vendorId,
      "id": product.id,
      "comments": comments,
      "vendor":  {
        name: vendor.name,
        rating: vendor.rating,
      }
    }
    

    var filterCriterias = [];
    var filteringConfig = {
      screenSize: "Screen Size",
      RAM: "RAM",
      diskSize: "Disk Size",
      noiseCancelling: "Noise Cancelling",
      aroma: "Aroma",
      size: "Size",
      color: "Color",
    };

      if ("productInfos" in product) {
        productInfos = {}
      
        tempProduct["productInfos"]  = JSON.parse(product.productInfos)
        
        tempProduct["productInfos"].forEach(function (property) {
          property["attributes"].forEach(function (attribute) {
            if (filterCriterias.length === 0) {
              filterCriterias.push({
                name: attribute.name,
                displayName: filteringConfig[attribute.name],
                possibleValues: [attribute.value],
              });
            } else {
              var nameCheckerObject = filterCriterias.filter(function (currentCriteria) {
                return currentCriteria.name === attribute.name;
              });

              if (nameCheckerObject.length > 0) {
                var currentCriteria;
                filterCriterias.forEach(function (criteria) {
                  if (criteria.name === attribute.name) {
                    currentCriteria = criteria;
                  }
                });

                var valueChecker = currentCriteria["possibleValues"].some(function (currentValue) {
                  return attribute.value === currentValue;
                });

                if (!valueChecker) {
                  currentCriteria["possibleValues"].push(attribute.value);
                }
              } else {
                filterCriterias.push({
                  name: attribute.name,
                  displayName: filteringConfig[attribute.name],
                  possibleValues: [attribute.value],
                });
              }
            }
          });
        });
      }


      tempProduct.filterCriterias = filterCriterias

    return tempProduct;
  } catch (error) {
    console.log(error);
    return error;
  }
};
