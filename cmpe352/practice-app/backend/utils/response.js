module.exports = {
    /**
     * Respond with error data.
     * params: {
     *      response: Response object from the requests
     *      error: Error object
     * }
     */
    handleError: function (response, error) {
        response.json({
            status: {
                success: false,
                code: 404
            },
            error: error.message
        });
    },
    /**
     * Respond with expected data. 
     * params: {
     *      response: Response object from the requests
     *      data: Data of the response, it's type should be an object
     * }
     */
    send: function (response, data) {
        response.json({
            status: {
                success: true,
                code: 200
            },
            error: false,
            data: data
        });
    }
};