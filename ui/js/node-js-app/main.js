const express = require('express');
const path = require('path');

const app = express();
const port = 8080;

// Serve static files from the "public" directory
app.use(express.static(path.join(__dirname, 'static')));

// Start the server
app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});

app.get('/', function(request, response){
    response.sendFile(__dirname + '/static/pages/products.html');
});