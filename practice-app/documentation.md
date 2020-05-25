# Base URL: 

## How to run the app

Once you are inside the practice-app folder, run the following commands in your terminal.

`cd backend` \
`npm install` \
`node app` 

Web app is available on the base URL, you will see a website that shows the responses from our 11 endpoints.

## Endpoints

All of the endpoints returns responses in JSON format.

### Random quote

Returns an inspirational quote from a famous, historical person.

**Endpoint:** `/quote` \
**Method:** `GET` \
**Authorization:** None \
**Parameters:** None

**Response:** ` {
                    status: {
                        success: true,
                        code: 200
                    }, error: false,
                    data: {
                			quote: "In three words I can sum up everything I've learned about life: it goes on.",
                			author: "Robert Frost"
                   }
                }`
               
               
### Corona Playlist

Gets the playlists containing the string "coronavirus" from Spotify Web API and returns a randomly chosen one.

**Endpoint:** `/random-playlist` \
**Method:** `GET` \
**Authorization:** API key from .env file \
**Parameters:** None

**Response:** ` {
                    status: {
                        success: true,
                        code: 200
                    }, error: false, 
                    data: {
                               playlist: {
                                   randomPlaylist.id: "2KkboYqP5llsT9gU7wcbco",
                                   name: "coronavirus",
                                   description: "",
                                   owner: "Liliana Mendoza",
                               }
                           }
                }`

### TRY-USD Rate

Returns the latest TRY-USD currency rate.

**Endpoint:** `/tryusd` \
**Method:** `GET` \
**Authorization:** API key from .env file \
**Parameters:** None

**Response:** ` {
                    status: {
                        success: true,
                        code: 200
                    }, error: false,
                     data: {
                               "TRY":6.8085
                           }
                }`

### Nearest Vendor

Gets the user location from Google API and vendor objects from the database. Calculates distance between vendors and the user and responds with the nearest vendor.

**Endpoint:** `/vendor/nearest` \
**Method:** `GET` \
**Authorization:** API key from .env file \
**Parameters:** None

**Response:** ` {
                    status: {
                        success: true,
                        code: 200
                    }, error: false,
                     data: {
                              vendor: {
                                  name: 'John',
                                  location: {
                                      latitude: 32.23,
                                      langitude: 38.21
                                  }
                              },
                              distance: 32811.13
                          }
                }`
                
### Nearest Hospitals

Gets the list of nearest hospitals to a location. Accepts latitude and longitude and optionally radius in meters. Due to Google API restrictions, only 1000 request per 24 hours can be made.

**Endpoint:** `/nearesthospitals` \
**Method:** `GET` \
**Authorization:** API key from .env file \
**Parameters:** `{ lat: 41.0862, long: 29.0444, radius: 1000 }`

**Response:**  `{
                    status: {
                        success: true,
                        code: 200
                    }, error: false,
                     data: {
                              count: 3,
                              names: [
                                "Bahçeşehir University Health Center",
                                "T. C. The Ministry of Health Sinanpaşa Family Health Center",
                                "İstanbul Medical Group"
                              ]
                          }
                      }
                      }`
