const Response = require('../utils/response');
const Request = require('../utils/request');
const parseString = require('xml2js').parseString;




module.exports.suggestBook = function (request, response){
    try {
        //get book name from query and make a search api call to goodreads.api with it
        const q = request.query.q;
        const url = 'https://www.goodreads.com/search/index.xml?q='+q+'&key='+process.env.GOODREADS_API_KEY;
        Request.send({
            url: url,
            method: 'GET',
            headers: {
                'Authorization': 'Basic ' + (new Buffer.from(process.env.GOODREADS_API_SECRET).toString('base64'))
            },
        }, async function (err, res, body) {
            if (err || body.error) {
                return Response.handleError(response, err || body.error);
            }
            //when response comes, parse the result and get the book ID of the first book in the search results (assumed the most relevant)
            let nq = 0;
            parseString(res.body, function (err, parseRes) {
                if(err){
                    return Response.handleError(response, err );
                }
                nq = (parseRes.GoodreadsResponse.search[0].results[0].work[0].best_book[0].id[0]._);

            });
            //make api call to get the all relevant info on the book using the ID
            const nurl = 'https://www.goodreads.com/book/show/'+nq+'.xml?key='+process.env.GOODREADS_API_KEY+'&format=xml';
            Request.send({
                url: nurl,
                method: 'GET',
                headers: {
                    'Authorization': 'Basic ' + (new Buffer.from(process.env.GOODREADS_API_SECRET).toString('base64'))
                },
            }, async function (err, res, body2) {
                if (err || res.body.error) {
                    return Response.handleError(response, err || res.body.error);
                } else {
                    //parse xml to get the list of similar books from the query result
                    parseString(res.body, function (err, parseRes) {
                        if(parseRes === undefined){
                            return Response.handleError(response, err);
                        } else {
                            const bookList = (parseRes.GoodreadsResponse.book[0].similar_books[0]);
                            Response.send(response, bookList);
                        }

                    });
                }


            })


        });
    } catch (error) {
        Response.handleError(response, error);
    }
}
