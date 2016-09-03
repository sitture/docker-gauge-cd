(function() {

	const express = require('express');
	const PORT = 8080;
	const app = express();

	app.set('view engine', 'ejs');

	app.get('/', function(req, res) {
	    res.render('index');
	});

	app.listen(PORT);

	console.log('Running on http://localhost:' + PORT);

})();
