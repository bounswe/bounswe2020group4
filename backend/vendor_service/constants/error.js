module.exports.ErrorMessage = {
  EMAIL_HAS_BEEN_USED: "This email has been already used",
  COULD_NOT_CREATE_USER: "User couldn't be created",
  MISSING_PARAMETER: "Missing parameter, see documentation for details",
  ADDRESS_ALREADY_EXISTS: "Address with the title already exists",
  USER_NOT_FOUND: "User not found",
  VENDOR_NOT_FOUND: "Vendor not found"
};

module.exports.ErrorCode = (message) => {
  const ErrorCodes = {
    EMAIL_HAS_BEEN_USED: 400,
    COULD_NOT_CREATE_USER: 400,
    MISSING_PARAMETER: 400,
    USER_NOT_FOUND: 404,
    VENDOR_NOT_FOUND: 404
  };

  return ErrorCodes[message] || 500;
};