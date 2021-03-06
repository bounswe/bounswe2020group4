const Product = require("../models/product").Product;
const ObjectId = require("mongoose").Types.ObjectId;
const Vendor = require("../models/vendor").Vendor;
const { addNotification } = require("../models/notification");

module.exports.updateProduct = async (product_id, parameter) => {
  try {
    var innerParameter = parameter;
    var productInfosChecker = false;
    const isPriceChanged = innerParameter.price;

    if (!!parameter.attributes) {
      product = await Product.findOne({ _id: ObjectId(product_id) });
      product = product.toJSON();

      var productAttributes = [];

      JSON.parse(product.productInfos).forEach(function (item) {
        if (JSON.stringify(item.attributes) == JSON.stringify(parameter.attributes)) {
          productAttributes.push(parameter);
          productInfosChecker = true;
        } else {
          productAttributes.push(item);
        }
      });

      productAttributes = JSON.stringify(productAttributes);
      innerParameter = { productInfos: productAttributes };

      if (!productInfosChecker) {
        return "Please check your update parameters";
      }
    }

    if (isPriceChanged) {
      addNotification(ObjectId(product_id));
    }

    checker = await Product.findByIdAndUpdate(product_id, innerParameter, function (err, docs) {
      if (err) {
        console.log(err);
      } else {
        return true;
      }
    });

    if (checker) {
      product = await Product.findOne({ _id: ObjectId(product_id) });
      return product;
    }
  } catch (error) {
    console.log(error);
    return "Please check your update parameters";
  }
};

module.exports.updateWholeProduct = async (product_id, newProductInfo) => {
  try {
    product = await Product.findOne({ _id: ObjectId(product_id) });
    notificationChecker = product.price !== newProductInfo.price;
    if (product == null) {
      return false;
    }
    product.category = newProductInfo.category;
    product.description = newProductInfo.description;
    product.name = newProductInfo.name;
    product.price = newProductInfo.price;
    product.originalPrice = newProductInfo.originalPrice;
    product.imageUrl = newProductInfo.imageUrl;
    product.rating = newProductInfo.rating;
    product.brand = newProductInfo.brand;
    product.productInfos = JSON.stringify(newProductInfo.productInfos);

    await product.save();

    if (notificationChecker) {
      addNotification(ObjectId(product_id));
    }

    return newProductInfo;
  } catch (error) {
    console.log(error);
    return "Please check your update parameters";
  }
};

module.exports.addProducts = async (products) => {
  try {
    const dbProducts = [];
    checker = true;
    products.forEach((product) => {
      checker =
        checker &&
        "name" in product &&
        "imageUrl" in product &&
        "description" in product &&
        "rating" in product &&
        "price" in product &&
        "originalPrice" in product &&
        "brand" in product &&
        "productInfos" in product &&
        "vendorId" in product;

      if (!checker) {
        return "Please check your parameters";
      }
      const newProduct = new Product({
        name: product.name,
        imageUrl: product.imageUrl,
        category: product.category,
        description: product.description,
        rating: product.rating,
        price: product.price,
        originalPrice: product.originalPrice || product.price,
        brand: product.brand,
        productInfos: JSON.stringify(product.productInfos),
        vendorId: ObjectId(product.vendorId),
      });

      dbProducts.push(newProduct);
    });

    await Promise.all(dbProducts.map(async (dbProduct) => await dbProduct.save()));
    if (checker) {
      return { idList: dbProducts.map((dbProduct) => dbProduct._id.toString()) };
    }

    return false;
  } catch (error) {
    console.log(error);
    return error;
  }
};

module.exports.getVendorList = async () => {
  try {
    vendorList = await Vendor.find();

    return vendorList;
  } catch (error) {
    console.log(error);
    return error;
  }
};

module.exports.getProducts = async (params) => {
  try {
    let products;

    finalProductList = [];

    if (params.categories) {
      products = await Product.find({ category: { $all: params.categories } });
    } else if (params.search) {
      products = await Product.find({ name: { $regex: params.search, $options: "i" } });
    } else {
      products = await Product.find();
    }

    products = products.filter(function (product) {
      return JSON.stringify(params.vendorId) === JSON.stringify(product.vendorId);
    });

    products = products.filter(function (product) {
      return JSON.stringify(params.vendorId) === JSON.stringify(product.vendorId);
    });

    products = await Promise.all(
      products.map(async (product) => {
        product = product.toJSON();
        const vendor = await Vendor.findById(product.vendorId);

        product.vendor = {
          name: vendor.name,
          rating: vendor.rating,
        };
        product.id = product._id.toString();

        delete product._id;

        return product;
      })
    );

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
        category: product.category,
        description: product.description,
        name: product.name,
        price: product.price,
        originalPrice: product.originalPrice,
        imageUrl: product.imageUrl,
        rating: product.rating,
        brand: product.brand,
        vendorId: product.vendorId,
        id: product.id,
      };

      if ("productInfos" in product) {
        productInfos = {};

        tempProduct["productInfos"] = JSON.parse(product.productInfos);

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

      finalProductList.push(tempProduct);
    });

    if (!!params.sortingFactor) {
      try {
        if (typeof finalProductList[0][params.sortingFactor] == "number") {
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

    if (params.vendorName) {
      finalProductList = finalProductList.filter((product) => product.vendor.name == params.vendorName);
    }

    return { productList: finalProductList, filterCriterias };
  } catch (error) {
    console.log(error);
    return error;
  }
};

module.exports.getVendorList = async () => {
  const vendorList = await Vendor.find();

  return vendorList;
};

module.exports.deleteProduct = async (parameter) => {
  let checker = false;
  const product = await Product.findById(ObjectId(parameter.productId));

  if (product == null) {
    return false;
  }

  if (JSON.stringify(product.vendorId) == JSON.stringify(parameter.vendorId)) {
    try {
      await Product.findByIdAndDelete(product._id);

      checker = true;
    } catch (error) {
      checker = false;
    }
  }

  return checker;
};
