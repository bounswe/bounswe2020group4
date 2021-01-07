const Product = require("../models/product").Product;
const ObjectId = require("mongoose").Types.ObjectId;

module.exports.addProducts = async (products) => {
  try {
    const dbProducts = [];

    products.forEach((product) => {
      const product = new Product({
        name: product.name,
        imageUrl: product.imageUrl,
        category: product.category,
        numberOfRatings: 0,
        price: product.price,
        originalPrice: product.originalPrice || product.price,
        stockValue: product.stockValue,
        brand: product.brand,
        sizes: product.sizes,
        colors: product.colors,
        vendorId: ObjectId(product.vendorId),
      });

      dbProducts.push(product);
    });

    await Promise.all(dbProducts.map(async (dbProduct) => await dbProduct.save()));

    return dbProducts.map((dbProduct) => dbProduct._id.toString());
  } catch (error) {
    console.log(error);
    return error;
  }
};


module.exports.getProducts = async (params) => {
  try {
    let products;

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
      if (product.productInfos.length > 0) {
        product.productInfos.forEach(function (property) {
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
    });

    if (!!params.sortingFactor) {
      try {
        if (typeof(products[0][params.sortingFactor]) == "number") {
          products = products.sort(
            (product1, product2) =>
              (params.sortingType == "descending" ? -1 : 1) *
              (product1[params.sortingFactor] - product2[params.sortingFactor])
          );
        } else {
          products = products.sort(
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
      products = products.filter(function (product) {
        return product.category.indexOf(params.subcategory) > -1;
      });
    }

    if (!!params.brand) {
      products = products.filter(function (product) {
        return product.brand.indexOf(params.brand) > -1;
      });
    }

    if (!!params.color) {
      products = products.filter(function (product) {
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
      products = products.filter(function (product) {
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
      products = products.filter(function (product) {
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
      products = products.filter(function (product) {
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
      products = products.filter(function (product) {
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
      products = products.filter(function (product) {
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
      products = products.filter(function (product) {
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
        delete product.vendorId;

        return product;
      })
    );

    return { productList: products, filterCriterias };
  } catch (error) {
    console.log(error);
    return error;
  }
};
