const remote = require('./renderer.js');
const http = require('http');

console.log(remote.store.get('unique_id'));

console.log(remote.store.get('token'));

var postData = {
    'unique_id' : remote.store.get('unique_id'),
    'token' : remote.store.get('token')
}
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
        setValues(response);
        console.log(response);


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

  function setValues(response){
    document.getElementById('name').innerHTML = response.name;
    document.getElementById('name_lastname').innerHTML = response.name + ' ' + response.lastname;
    document.getElementById('email').innerHTML = response.email;
    document.getElementById('bank_name').innerHTML = response.bank;
    document.getElementById('bank_city').innerHTML = response.city;
    document.getElementById('type').innerHTML = response.type;
    document.getElementById('funds').innerHTML = response.funds;
    document.getElementById('dept').innerHTML = response.dept;

  }