const Response = require('../utils/response');
const Request = require('request'); // "Request" library
const Constants = require('../constants');

module.exports.getRandomCoronaPlaylist = async function (request, response) {
    try {
        // get spotify client credentials for authorization
        var client_id = Constants.SPOTIFY_CLIENT_ID; // Your client id
        var client_secret = Constants.SPOTIFY_CLIENT_SECRET; // Your secret

        // configure api authorization options
        var authOptions = {
            url: 'https://accounts.spotify.com/api/token',
            headers: {
                'Authorization': 'Basic ' + (new Buffer.from(client_id + ':' + client_secret).toString('base64'))
            },
            form: {
                grant_type: 'client_credentials'
            },
            json: true
        };
        // use Spotify Web API with the authorization you have
        Request.post(authOptions, async function(auth_error, auth_res, auth_body) {
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
                Request.get(options, function(api_error, api_res, body) {
                    if (!api_error && !body.error) {
                        // get 20 playlists and choose one of them randomly
                        const playlists = body.playlists.items;// TODO: change this so that playlist will be taken from db.
                        const randomPlaylist = playlists[Math.floor(Math.random() * playlists.length)];
                        // set the response body, playlist_id is used to embed the playlist to the website
                        // using the format below:
                        // <iframe src="https://open.spotify.com/embed/playlist/'playlist_id'" width="300" height="380" frameborder="0" allowtransparency="true" allow="encrypted-media"></iframe>
                        const responseBody = {
                            playlist_id: randomPlaylist.id,
                            name: randomPlaylist.name,
                            description: randomPlaylist.description,
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
