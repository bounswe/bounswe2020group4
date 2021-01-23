const ErrorMessage = {
  EMAIL_HAS_BEEN_USED: "This email has been already used",
  COULD_NOT_CREATE_USER: "User couldn't be created",
  MISSING_PARAMETER: "Missing parameter, see documentation for details",
  ADDRESS_ALREADY_EXISTS: "Address with the title already exists",
  USER_NOT_FOUND: "User not found",
  PRODUCT_NOT_FOUND: "Product not found",
  WRONG_GOOGLE_TOKEN: "Google token provided is wrong."
  CHECK_UPDATE_PARAMETERS: "Check your update parameters"
};

module.exports.ErrorCode = (message) => {
  const ErrorCodes = {};

  ErrorCodes[ErrorMessage.EMAIL_HAS_BEEN_USED] = 400;
  ErrorCodes[ErrorMessage.WRONG_GOOGLE_TOKEN] = 400;
  ErrorCodes[ErrorMessage.COULD_NOT_CREATE_USER] = 400;
  ErrorCodes[ErrorMessage.MISSING_PARAMETER] = 400;
  ErrorCodes[ErrorMessage.USER_NOT_FOUND] = 404;
  ErrorCodes[ErrorMessage.PRODUCT_NOT_FOUND] = 404;

  return ErrorCodes[message] || 500;
};

module.exports.ErrorMessage = ErrorMessage;
