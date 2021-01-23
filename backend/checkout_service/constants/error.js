const ErrorMessage = {
  EMAIL_HAS_BEEN_USED: "This email has been already used",
  COULD_NOT_CREATE_USER: "User couldn't be created",
  MISSING_PARAMETER: "Missing parameter, see documentation for details",
  ADDRESS_ALREADY_EXISTS: "Address with the title already exists",
  USER_NOT_FOUND: "User not found",
  PRODUCT_NOT_FOUND: "Product(s) not found",
  WRONG_USER_TYPE: "Please use one of the following user types: customer, vendor",
  ALREADY_SHIPPED: "Product(s) cannot be cancelled, it is already shipped.",
  SHOULD_BE_DELIVERED: "Product(s) should be delivered first to return it.",
  VENDOR_CANNOT_RETURN: "Vendors cannot return a product.",
  ALREADY_CANCEL_OR_RETURN: "Product(s) already cancelled or returned.",
};

module.exports.ErrorCode = (message) => {
  const ErrorCodes = {};

  ErrorCodes[ErrorMessage.EMAIL_HAS_BEEN_USED] = 400;
  ErrorCodes[ErrorMessage.COULD_NOT_CREATE_USER] = 400;
  ErrorCodes[ErrorMessage.MISSING_PARAMETER] = 400;
  ErrorCodes[ErrorMessage.WRONG_USER_TYPE] = 400;
  ErrorCodes[ErrorMessage.ALREADY_CANCEL_OR_RETURN] = 400;
  ErrorCodes[ErrorMessage.SHOULD_BE_DELIVERED] = 400;
  ErrorCodes[ErrorMessage.VENDOR_CANNOT_RETURN] = 400;
  ErrorCodes[ErrorMessage.ALREADY_SHIPPED] = 400;
  ErrorCodes[ErrorMessage.USER_NOT_FOUND] = 404;
  ErrorCodes[ErrorMessage.PRODUCT_NOT_FOUND] = 404;

  return ErrorCodes[message] || 500;
};
module.exports.ErrorMessage = ErrorMessage;
