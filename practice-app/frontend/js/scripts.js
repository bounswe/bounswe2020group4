$(document).ready(function () {
  $("#useMyLocation").click(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(success, () =>
        alert("Sorry, we can't use location services since we do not have a SSL certificate")
      );
    } else {
      alert("Sorry, we can't use location services since we do not have a SSL certificate");
    }
    function success(position) {
      document.getElementById("long").value = position.coords.longitude;
      document.getElementById("lat").value = position.coords.latitude;
    }
  });
  $("#hospitalForm").submit(function (event) {
    var lat = parseFloat(document.getElementById("lat").value, 10);
    var long = parseFloat(document.getElementById("long").value, 10);
    var radius = parseFloat(document.getElementById("radius").value, 10);
    if (!isNaN(lat) && !isNaN(long)) {
      $.ajax({
        type: "GET",
        url: "/nearesthospitals",
        data: { lat, long, radius },
        success: function (response, data) {
          if (response.data.count === 0) {
            alert("You shall press submit button wisely!");
          } else {
            var i = 1;
            var final_text = "";
            response.data.names.forEach(function (hospitalName) {
              final_text += "Hospital " + i + ": " + hospitalName + "\n";
              i++;
            });
            document.getElementById("hospitalList").innerText = final_text;
          }
        }
      });
    } else {
      alert("PLEASE FILL LONG AND LAT!!!");
    }
    event.preventDefault();
  });
  $("#hospitalSubmit").click(function () {
    $("#hospitalForm").submit();
  });
});

$(document).ready(function () {
  var final_data = undefined;
  $.get("/vendor/nearest", {}, function (response, data) {
    final_data = response.data;
    if (final_data !== undefined) {
      var vendor = response.data.vendor.name;
      var distance = response.data.distance;
      var distance_final = distance.toFixed(3);
      var latitude = response.data.vendor.location.latitude;
      var longitude = response.data.vendor.location.longitude;
      document.getElementById("vendorName").innerText =
        vendor + " is " + distance_final + " km away from you.";
      document
        .getElementById("vendorLink")
        .setAttribute("href", "http://maps.google.com?q=" + latitude + "," + longitude);
    }
  });
});

$(document).ready(function () {
  var final_data = undefined;
  $.get("/covid19", {}, function (response, data) {
    // response has the attribute 'data', which is the object API returns.
    // For other attributes, see backend/utils/response.js
    final_data = response.data;
    if (final_data.NewConfirmed !== undefined) {
      document.getElementById("newConfirmed").innerHTML = response.data.NewConfirmed;
      document.getElementById("totalConfirmed").innerHTML = response.data.TotalConfirmed;
      document.getElementById("newDeaths").innerHTML = response.data.NewDeaths;
      document.getElementById("totalDeaths").innerHTML = response.data.TotalDeaths;
      document.getElementById("newRecovered").innerHTML = response.data.NewRecovered;
      document.getElementById("totalRecovered").innerHTML = response.data.TotalRecovered;
      document.getElementById("activeCases").innerHTML = response.data.ActiveCases;
      document.getElementById("lastUpdate").innerHTML = response.data.lastUpdate;
    }
  });
});

$(document).ready(function () {
  // Return random playlist
  $.get("/random-playlist", {}, function (response, data) {
    // response has the attribute 'data', which is the object API returns.
    // For other attributes, see backend/utils/response.js
    document.getElementById("playlist-name").innerHTML = response.data.name;
    document.getElementById("playlist-description").innerHTML = response.data.description
      ? response.data.description
      : "A Coronavirus Related Playlist";
    document.getElementById("playlist-owner").innerHTML = response.data.owner;
    document
      .getElementById("spotify")
      .setAttribute("src", "https://open.spotify.com/embed/playlist/" + response.data.playlist_id);
    document.getElementById("spotify").setAttribute("width", "300");
    document.getElementById("spotify").setAttribute("height", "380");
    document.getElementById("spotify").setAttribute("frameborder", "0");
    document.getElementById("spotify").setAttribute("allowtransparency", "true");
    document.getElementById("spotify").setAttribute("allow", "encrypted-media");
    document.getElementById("spotify").setAttribute("name", response.data.name);
  });
});
// Default: call the function when the document is loaded
$(document).ready(function () {
  // it listens for click on the button with given id
  $("#playlist-button").click(function () {
    // GET Request from API and fill the elements' HTML content with data taken from response
    $.get("/random-playlist", {}, function (response, data) {
      // response has the attribute 'data', which is the object API returns.
      // For other attributes, see backend/utils/response.js
      document.getElementById("playlist-name").innerHTML = response.data.name;
      document.getElementById("playlist-description").innerHTML = response.data.description
        ? response.data.description
        : "A Coronavirus Related Playlist";
      document.getElementById("playlist-owner").innerHTML = response.data.owner;
      document
        .getElementById("spotify")
        .setAttribute(
          "src",
          "https://open.spotify.com/embed/playlist/" + response.data.playlist_id
        );
      document.getElementById("spotify").setAttribute("width", "300");
      document.getElementById("spotify").setAttribute("height", "380");
      document.getElementById("spotify").setAttribute("frameborder", "0");
      document.getElementById("spotify").setAttribute("allowtransparency", "true");
      document.getElementById("spotify").setAttribute("allow", "encrypted-media");
      document.getElementById("spotify").setAttribute("name", response.data.name);
    });
  });
});
// Default Booklist after the first load
$(document).ready(function () {
  var q = "coronavirus";
  $.post("/suggestBook?q=" + q, {}, function (response, data) {
    for (i = 0; i < 5; i++) {
      book = response.data.book[i];
      var authors = Array.from(book.authors[0].author, (author) => author.name[0]);
      var author = authors.join();
      var title = book.title[0];
      var rating = book.average_rating[0];
      var link = book.link[0];
      var small_image_url = book.image_url[0];
      document.getElementById("cover" + (i + 1)).setAttribute("src", small_image_url);
      document.getElementById("title" + (i + 1)).innerHTML = title;
      document.getElementById("title" + (i + 1)).setAttribute("href", link);
      document.getElementById("rating" + (i + 1)).innerHTML = rating;
      document.getElementById("author" + (i + 1)).innerHTML = author;
    }
  });
});
// Default search for a book
$(document).ready(function () {
  $("#book-form").submit(function (event) {
    var q = document.getElementById("book-name").value;
    if (q.length === 0) {
      alert("PLEASE ENTER SOMETHING TO SEARCH!!!");
    } else {
      $.post("/suggestBook?q=" + q, {}, function (response, data) {
        for (i = 0; i < 5; i++) {
          book = response.data.book[i];
          var author = book.authors[0].author[0].name[0];
          var title = book.title[0];
          var rating = book.average_rating[0];
          var link = book.link[0];
          var small_image_url = book.image_url[0];
          document.getElementById("cover" + (i + 1)).setAttribute("src", small_image_url);
          document.getElementById("title" + (i + 1)).innerHTML = title;
          document.getElementById("title" + (i + 1)).setAttribute("href", link);
          document.getElementById("rating" + (i + 1)).innerHTML = rating;
          document.getElementById("author" + (i + 1)).innerHTML = author;
        }
      });
    }
    event.preventDefault();
  });
  $("#book-button").click(function () {
    $("#book-form").submit();
  });
});

$(document).ready(function () {
  var final_data = undefined;
  $.get("/apod", {}, function (response, data) {
    final_data = response.data;
    if (final_data !== undefined) {
      document.getElementById("apod").setAttribute("src", response.data.apodURL);
      document.getElementById("apodTitle").innerText = response.data.apodTitle;
    }
  });
});

$(document).ready(function () {
  var final_data = undefined;
  $.get("/quote", {}, function (response, data) {
    final_data = response.data;
    if (final_data !== undefined) {
      document.getElementById("quote").innerText = response.data.quote;
      document.getElementById("author").innerText = response.data.author;
    }
  });
});

$(document).ready(function () {
  var final_data = undefined;
  $.get("/weather/daily", {}, function (response, data) {
    document.getElementById("day1_desc").innerHTML = response.data.day1.desc;
    document
      .getElementById("day1_icon")
      .setAttribute(
        "src",
        "http://openweathermap.org/img/wn/" + response.data.day1.icon + "@2x.png"
      );
    document.getElementById("day1_min").innerHTML = response.data.day1.minTemp;
    document.getElementById("day1_max").innerHTML = response.data.day1.maxTemp;
    document.getElementById("day2_desc").innerHTML = response.data.day2.desc;
    document
      .getElementById("day2_icon")
      .setAttribute(
        "src",
        "http://openweathermap.org/img/wn/" + response.data.day2.icon + "@2x.png"
      );
    document.getElementById("day2_min").innerHTML = response.data.day2.minTemp;
    document.getElementById("day2_max").innerHTML = response.data.day2.maxTemp;
    document.getElementById("day3_desc").innerHTML = response.data.day3.desc;
    document
      .getElementById("day3_icon")
      .setAttribute(
        "src",
        "http://openweathermap.org/img/wn/" + response.data.day3.icon + "@2x.png"
      );
    document.getElementById("day3_min").innerHTML = response.data.day3.minTemp;
    document.getElementById("day3_max").innerHTML = response.data.day3.maxTemp;
    document.getElementById("day4_desc").innerHTML = response.data.day4.desc;
    document
      .getElementById("day4_icon")
      .setAttribute(
        "src",
        "http://openweathermap.org/img/wn/" + response.data.day4.icon + "@2x.png"
      );
    document.getElementById("day4_min").innerHTML = response.data.day4.minTemp;
    document.getElementById("day4_max").innerHTML = response.data.day4.maxTemp;
    document.getElementById("day5_desc").innerHTML = response.data.day5.desc;
    document
      .getElementById("day5_icon")
      .setAttribute(
        "src",
        "http://openweathermap.org/img/wn/" + response.data.day5.icon + "@2x.png"
      );
    document.getElementById("day5_min").innerHTML = response.data.day5.minTemp;
    document.getElementById("day5_max").innerHTML = response.data.day5.maxTemp;
    document.getElementById("day6_desc").innerHTML = response.data.day6.desc;
    document
      .getElementById("day6_icon")
      .setAttribute(
        "src",
        "http://openweathermap.org/img/wn/" + response.data.day6.icon + "@2x.png"
      );
    document.getElementById("day6_min").innerHTML = response.data.day6.minTemp;
    document.getElementById("day6_max").innerHTML = response.data.day6.maxTemp;
    document.getElementById("day7_desc").innerHTML = response.data.day7.desc;
    document
      .getElementById("day7_icon")
      .setAttribute(
        "src",
        "http://openweathermap.org/img/wn/" + response.data.day7.icon + "@2x.png"
      );
    document.getElementById("day7_min").innerHTML = response.data.day7.minTemp;
    document.getElementById("day7_max").innerHTML = response.data.day7.maxTemp;
  });
});

$(document).ready(function () {
  var days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
  var d = new Date();
  var today = d.toString().slice(0, 3);
  var index = days.indexOf(today);
  document.getElementById("day2").innerHTML = days[(index + 1) % 7];
  document.getElementById("day3").innerHTML = days[(index + 2) % 7];
  document.getElementById("day4").innerHTML = days[(index + 3) % 7];
  document.getElementById("day5").innerHTML = days[(index + 4) % 7];
  document.getElementById("day6").innerHTML = days[(index + 5) % 7];
  document.getElementById("day7").innerHTML = days[(index + 6) % 7];
});

$(document).ready(function () {
  $.get("/covid/news", {}, function (response, data) {
    var news = response.data.articles;
    var x1 = document.getElementById("news_cont1");
    var x2 = document.getElementById("news_cont2");
    var x3 = document.getElementById("news_cont3");
    var x4 = document.getElementById("news_cont4");
    if (news.length === 0) {
      document.getElementById("news_header").innerHTML = "You are up to date!";
      x1.style.display = "none";
      x2.style.display = "none";
      x3.style.display = "none";
      x4.style.display = "none";
    } else if (news.length === 1) {
      document.getElementById("news_title1").innerHTML = response.data.articles[0].title;
      document.getElementById("news_desc1").innerHTML = response.data.articles[0].description;
      document.getElementById("news_link1").setAttribute("href", response.data.articles[0].url);
      x2.style.display = "none";
      x3.style.display = "none";
      x4.style.display = "none";
    } else if (news.length === 2) {
      document.getElementById("news_title1").innerHTML = response.data.articles[0].title;
      document.getElementById("news_desc1").innerHTML = response.data.articles[0].description;
      document.getElementById("news_link1").setAttribute("href", response.data.articles[0].url);
      document.getElementById("news_title2").innerHTML = response.data.articles[1].title;
      document.getElementById("news_desc2").innerHTML = response.data.articles[1].description;
      document.getElementById("news_link2").setAttribute("href", response.data.articles[1].url);
      x3.style.display = "none";
      x4.style.display = "none";
    } else if (news.length === 3) {
      document.getElementById("news_title1").innerHTML = response.data.articles[0].title;
      document.getElementById("news_desc1").innerHTML = response.data.articles[0].description;
      document.getElementById("news_link1").setAttribute("href", response.data.articles[0].url);
      document.getElementById("news_title2").innerHTML = response.data.articles[1].title;
      document.getElementById("news_desc2").innerHTML = response.data.articles[1].description;
      document.getElementById("news_link2").setAttribute("href", response.data.articles[1].url);
      document.getElementById("news_title3").innerHTML = response.data.articles[2].title;
      document.getElementById("news_desc3").innerHTML = response.data.articles[2].description;
      document.getElementById("news_link3").setAttribute("href", response.data.articles[2].url);
      x4.style.display = "none";
    } else {
      document.getElementById("news_title1").innerHTML = response.data.articles[0].title;
      document.getElementById("news_desc1").innerHTML = response.data.articles[0].description;
      document.getElementById("news_link1").setAttribute("href", response.data.articles[0].url);
      document.getElementById("news_title2").innerHTML = response.data.articles[1].title;
      document.getElementById("news_desc2").innerHTML = response.data.articles[1].description;
      document.getElementById("news_link2").setAttribute("href", response.data.articles[1].url);
      document.getElementById("news_title3").innerHTML = response.data.articles[2].title;
      document.getElementById("news_desc3").innerHTML = response.data.articles[2].description;
      document.getElementById("news_link3").setAttribute("href", response.data.articles[2].url);
      document.getElementById("news_title4").innerHTML = response.data.articles[3].title;
      document.getElementById("news_desc4").innerHTML = response.data.articles[3].description;
      document.getElementById("news_link4").setAttribute("href", response.data.articles[3].url);
    }
  });
});

$(document).ready(function () {
  $.get("/tryusd", {}, function (response, data) {
    document.getElementById("usdtry").innerHTML = response.data.TRY;
  });
});

$(document).ready(function () {
  var final_data = undefined;
  $.get("/weather-important-cities", {}, function (response, data) {
    var i = 1;
    response.data.currentWeathers.forEach(function (weather) {
      document.getElementById("name" + i).innerHTML = weather.cityName;
      document.getElementById("desc" + i).innerHTML = weather.description;
      document.getElementById("icon" + i).setAttribute("src", weather.iconUrl);
      document.getElementById("temp" + i).innerHTML = weather.currentTemp;
      i++;
    });
  });
});
