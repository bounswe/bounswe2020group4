/**
 * Checks if the credit card information is in valid format.
 * @param {
 *    creditCard: {
 *      number: {Number | String},
 *      expirationMonth: String,
 *      expirationYear: String,
 *      cvc: {Number | String}
 *    }
 * } params 
 * 
 * @returns { success: {true|false}, msg: String } 
 */
module.exports.checkCreditCard = async (params) => {
    try {
      const creditCard = params.creditCard;
      const creditCardNumber = String(creditCard.number);
      const expirationMonth = creditCard.expirationMonth;
      const expirationYear = creditCard.expirationYear;
      const cvc = String(creditCard.cvc);
      
      let d = new Date();
      
      if (d.getFullYear() > parseInt("20" + expirationYear)) {
        return {
          success: false,
          msg: "The card is expired.",
        };
      }
      if (d.getFullYear() === parseInt("20" + expirationYear) && d.getMonth() > parseInt(expirationMonth)) {
        return {
          success: false,
          msg: "The card is expired.",
        };
      }
      if (creditCardNumber.length !== 16) {
        return {
          success: false,
          msg: "The credit card format is invalid.",
        };
      }
      if (!(/\d/.test(creditCardNumber))) {
        return {
          success: false,
          msg: "Credit card number contains alphanumeric.",
        };
      }
      
      if (cvc.length !== 3) {
        return {
          success: false,
          msg: "The CVC number format is invalid.",
        };
      }
      return {
        success: true,
        msg: "Success!",
      };
    } catch (error) {
      console.log(error);
      return error;
    }
  };