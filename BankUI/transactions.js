const remote = require('./renderer.js');
const http = require('http');


function transaction(key, value){

    var postData = {}

    postData['unique_id'] = remote.store.get('unique_id');
    postData['token'] = remote.store.get('token');
    postData[key] = value;

    console.log(postData);
    var options = {
        hostname: '127.0.0.1',
        port: 100,
        path: '/test',
        method: 'POST',
        body:    JSON.stringify(postData),
        headers: {
          'Content-Type': 'application/json'
        }
      };
    
      var req = http.request(options, (res) => {
        console.log(`STATUS: ${res.statusCode}`);
        console.log(`HEADERS: ${JSON.stringify(res.headers)}`);
        res.setEncoding('utf8');
        res.on('data', (chunk) => {
          var response = JSON.parse(`${chunk}`);
            if(response.answer === 'true'){
                location.reload();
            }
            else{
                alert("Somethings wrong! Try again.");
            }
        });
        res.on('end', () => {
          console.log('No more data in response.')
        })
      });
      
      req.on('error', (e) => {
        console.log(`problem with request: ${e.message}`);
      });
      
      // write data to request body
      req.write(JSON.stringify(postData));
      req.end();
}

module.exports.transaction = transaction;