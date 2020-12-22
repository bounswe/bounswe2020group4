const { parse } = require("path");

module.exports.checkCreditCard = async (params) => {
    try {
        const creditCard = params.creditCard;
        
        const creditCardNumber = creditCard.number;
        const expirationMonth = creditCard.expirationMonth;
        const expirationYear = creditCard.expirationYear;
        const cvc = creditCard.cvc; 

        let d = new Date();

        if (d.getFullYear() < parseInt("20" + expirationYear)) {
            return { 
                success: false, 
                msg: "The card is expired.",
            };
        }

        if (d.getFullYear() === parseInt("20" + expirationYear) && d.getMonth() < parseInt(expirationMonth)) {
            return {
                success: false,
                msg: "The card is expired.",
            };
        }

        if (creditCardNumber.length !== 16) {
            return {
                success: false,
                msg: "The credit card format is invalid."
            };
        }

        if (cvc.length !== 3) {
            return {
                success: false,
                msg: "The CVC number format is invalid.",
            }
        }

        return {
            success: true,
            msg: "Success!",
        }
    } catch (error) {
        return error;
    }
};
