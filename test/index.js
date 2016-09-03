var expect  = require("chai").expect;
var request = require("request");
var cheerio = require('cheerio');

describe("Seed", function() {

  var url = "http://localhost:8080";

  it("returns status 200", function(done) {
    request(url, function(error, response, body) {
      expect(response.statusCode).to.equal(200);
      done();
    });
  });

  it("says hello to the world", function(done) {
    request(url, function(error, response, body) {
      var $ = cheerio.load(body);
      expect($('#title').text()).to.equal('Hello world!');
      done();
    });
  });

});
