const Response = require('../utils/response');
const Request = require('../utils/request');

/**
 * Explanation
 * Response: {
 *    status: {
 *        success: true,
 *        code: 200
 *    }, data: {
 *        articles: [
 *            {
 *                title: 'Headline for the first article',
 *                description: 'A short description of this article',
 *                url: 'https://www.example.org'
 *            },             
 *            {
 *                title: 'Headline for the second article',
 *                description: 'A short description of this article',
 *                url: 'https://www.example2.org'
 *            },
 *            ... // the number of articles varies, max num = 10
 *        ]
 *    }
 *}
 */
module.exports.getNews = async function (request, response) {
    let current_date = new Date().toISOString();
    current_date = current_date.substring(0, current_date.indexOf('T'));

    try {
        Request.send({
            url: 'https://newsapi.org/v2/top-headlines?q=koronavirüs&from=' + current_date + '&sortBy=publishedAt&apiKey=' + process.env.COVID_NEWS_API_KEY + '&pageSize=10&page=1&country=tr',
            method: 'GET',
            body: {}
        }, async function (err, res, body) {
            if (err || body.error) { // If there is an error respond with the error.
                return Response.handleError(response, err || body.error);
            }

            let articles = []

            body.articles.forEach(function (article) {
                articles.push({
                    title: article.title,
                    description: article.description,
                    url: article.url
                })
            });

            // Set the response body.
            const responseBody = {
                articles: articles
            }

            // Respond to the front-end.
            Response.send(response, responseBody);
        });
    } catch (error) {
        Response.handleError(response, error);
    }
};