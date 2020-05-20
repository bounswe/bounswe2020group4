const Response = require('../utils/response');
const Request = require('request'); // "Request" library

/**
 * Get the playlists containing the string "coronavirus" from spotify web api and
 * randomly choose one. Then, respond with the playlist.
 * Response: {
 *    status: {
 *        success: true,
 *        code: 200
 *    },
 *    data: {
 *       playlist: {
 *           randomPlaylist.id: 2KkboYqP5llsT9gU7wcbco,
 *           name: coronavirus
 *           description: "",
 *           owner: Liliana Mendoza,
 *       }
 *    }
 * }
 */
module.exports.getRandomCoronaPlaylist = async function (request, response) {
    try {

        // configure api authorization options
        var authOptions = {
            url: 'https://accounts.spotify.com/api/token',
            headers: {
                'Authorization': 'Basic ' + (new Buffer.from(process.env.SPOTIFY_CLIENT_ID + ':' + process.env.SPOTIFY_CLIENT_SECRET).toString('base64'))
            },
            form: {
                grant_type: 'client_credentials'
            },
            json: true
        };
        // use Spotify Web API with the authorization you have
        Request.post(authOptions, async function (auth_error, auth_res, auth_body) {
            if (!auth_error && !auth_body.error) {
                // use the access token to configure the options for getting playlists
                var token = auth_body.access_token;
                var options = {
                    url: 'https://api.spotify.com/v1/search?q=coronavirus&type=playlist',
                    headers: {
                        'Authorization': 'Bearer ' + token
                    },
                    json: true
                };
                // get playlists from Spotify Web API
                Request.get(options, function (api_error, api_res, body) {
                    if (!api_error && !body.error) {
                        // get 20 playlists and choose one of them randomly
                        const playlists = body.playlists.items;
                        const randomPlaylist = playlists[Math.floor(Math.random() * playlists.length)];
                        // set the response body, playlist_id is used to embed the playlist to the website
                        // using the format below:
                        // <iframe src="https://open.spotify.com/embed/playlist/'playlist_id'" width="300" height="380" frameborder="0" allowtransparency="true" allow="encrypted-media"></iframe>
                        const responseBody = {
                            playlist_id: randomPlaylist.id,
                            name: randomPlaylist.name,
                            description: randomPlaylist.description, // TODO: investigate further the issue with special characters being represented with hexcode inside the string. 
                            owner: randomPlaylist.owner.display_name,
                        }
                        // respond to the front-end
                        Response.send(response, responseBody);
                    } else {
                        Response.handleError(response, api_error || body.error);
                    }
                });
            } else {
                Response.handleError(response, auth_error || auth_body.error);
            }
        });
    } catch (error) {
        Response.handleError(response, error);
    }
};