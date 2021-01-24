module.exports.ErrorMessage = {
  MISSING_PARAMETER: "Missing parameter, see documentation for details",
  PRODUCT_NOT_FOUND: "Product not found",
  COMMENT_NOT_FOUND: "Comment not found"
};

module.exports.ErrorCode = (message) => {
  const ErrorCodes = {
    MISSING_PARAMETER: 400,
    PRODUCT_NOT_FOUND: 404,
    COMMENT_NOT_FOUND: 404
  };

  return ErrorCodes[message] || 500;
};
