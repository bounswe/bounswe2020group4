# Base URL:  http://localhost:8080/
# Base URL on Amazon server: http://bounswe2020group4.eu-west-1.elasticbeanstalk.com

## What is this app and why you should try it
With the COVID-19 pandemic around, there might be all kinds of information you wish you had. Whether you need to find the current numbers or the nearest hospitals, get suggestions for books and playlists or need some words for inspiration, this app has it all for you in just one place.

## How to run the app

Once you are inside the practice-app folder, install docker and run `commands.sh`.

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

### Current Covid19 numbers

Gets the lastest update on the numbers of Coronovirus cases in Turkey.

**Endpoint:** `/covid19` \
**Method:** `GET` \
**Authorization:** None \
**Parameters:** None

**Response:** `{
                  status: {
                      success: true,
                      code: 200
                  },
                  error: false,
                  data: {
                      NewConfirmed: 1114,
                      TotalConfirmed: 139771,
                      NewDeaths: 55,
                      TotalDeaths: 3841,
                      NewRecovered: 3089,
                      TotalRecovered: 95780,
                      ActiveCases: 40150,
                      lastUpdate: "2020-05-12"
              }`
              
### Covid19 News

Returns up to ten news articles about COVID-19.

**Endpoint:** `/covid/news` \
**Method:** `GET` \
**Authorization:** API key from .env file \
**Parameters:** None

**Response:** `{
                  status: {
                      success: true,
                      code: 200
                  },
                  data: {
                      articles: [
                          {
                          title: 'Headline for the first article',
                          description: 'A short description of this article',
                          url: 'https://www.example.org'
                          },
                          {  
                          title: 'Headline for the second article',
                          description: 'A short description of this article',
                          url: 'https://www.example2.org'
                          }, ... //the number of articles varies, max num = 10
                       ]
                    }        
              }`
              
### Weather in Istanbul

Returns the weather of Istanbul for the next 8 days.

**Endpoint:** `/weather/daily` \
**Method:** `GET` \
**Authorization:** API key from .env file \
**Parameters:** None

**Response:** `{
                  status: {
                      success: true,
                      code: 200
                  },
                  data: {
                      day1: {
                          minTemp: 18,
                          maxTemp: 26,
                          desc: 'broken clouds',
                          icon: '04d'
                      },
                      day2: {
                          minTemp: 17,
                          maxTemp: 27,
                          desc: 'broken clouds',
                          icon: '04d'
                      },
                      day3: {
                          minTemp: 19,
                          maxTemp: 26,
                          desc: 'overcast clouds',
                          icon: '03d'
                      },
                      day4: {
                          minTemp: 16,
                          maxTemp: 20,
                          desc: 'light rain',
                          icon: '09d'
                      },
                      day5: {
                          minTemp: 15,
                          maxTemp: 19,
                          desc: 'broken clouds',
                          icon: '04d'
                      },
                      day6: {
                          minTemp: 14,
                          maxTemp: 18,
                          desc: 'broken clouds',
                          icon: '04d'
                      },
                      day7: {
                          minTemp: 13,
                          maxTemp: 18,
                          desc: 'clear sky',
                          icon: '01d'
                      }
                  }
             }`
             
### Weather in Important Cities

Returns the current weather of some important cities.

**Endpoint:** `/weather-important-cities` \
**Method:** `GET` \
**Authorization:** API key from .env file \
**Parameters:** None

**Response:** `{
                  status: {
                      success: true,
                      code: 200
                  },
                  data: {
                      currentWeathers: [
                          {
                              cityName: 'name of the City1',
                              currentTemp: 'current temperature for given city',
                              description: 'A short description of current weather',
                              iconUrl: 'https://www.example.org'
                          },
                          {  
                              cityName: 'name of the City2',
                              currentTemp: 'current temperature for given city',
                              description: 'A short description of current weather',
                              iconUrl: 'https://www.example2.org'
                          }, ... // max num = 20
                       ]
                    }        
              }`
              
### Book Suggestions

Makes a search api call to the Goodreads API with a book name given by the user, gets the first book's ID in the search results and gathers all relevant info on the book by using the ID. Returns a list of similar books from the query result.

**Endpoint:** `/suggestBook` \
**Method:** `GET` \
**Authorization:** API key from .env file \
**Parameters:** None

### Astronomy Picture of the Day

Gets NASA's Astronomy Picture of the Day.

**Endpoint:** `/apod` \
**Method:** `GET` \
**Authorization:** API key from .env file \
**Parameters:** None

**Response:** ` {
                    status: {
                        success: true,
                        code: 200
                    },
                    data: {
                			apodURL: ""https://apod.nasa.gov/apod/image/1707/M63-HST-Subaru-S1024.jpg",
                			apodTitle: "Messier 63: The Sunflower Galaxy"
                   }
                }`


