const request = require('request');

/**
 * Send a request to an endpoint.
 * settings: {
 *      url: 'http://example.endpoint.url'
 *      method: 'POST', 'GET' etc.
 *      body: {
 *          exampleParameter1: 1,
 *          exampleParameter2: 2
 *      }
 * }
 */
module.exports.send = function (settings, callback) {
    try {
        const config = {
            url: settings.url,
            method: settings.method,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            json: true,
            body: settings.body
        }

        request(config, callback);
    } catch (error) {
        console.log('Error at sending request: ', error, ' settings: ', settings);
    }
};