// This file is required by the index.html file and will
// be executed in the renderer process for that window.
// All of the Node.js APIs are available in this process.
const queryString = require('query-string');
const http = require('http');
const remote = require('electron');
const functions = require('./functions.js');
const Store = require('electron-store');

const store = new Store();

module.exports.store = store;

let Token;
let Response;
let ID;
//let Password;

function sendRequest(variable){

  var postData = {};

  if(variable == "unique_id"){
    var value = document.getElementById(variable).value;
    postData['unique_id'] = value;

    store.set('unique_id', value);
  }

  if(variable == "password"){

    var value = document.getElementById(variable).value;
    var unique_id = store.get('unique_id');

    postData = {
      'unique_id' : unique_id,
      'password' : value
    }

  }
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
      if(response.token != null){
        store.set('token', response.token);
        console.log(response.token);
        functions.checkResponse("token");
      }
      else{
        if(response.answer == "false"){
          document.getElementById("notfound-id").innerHTML = "Incorrect";
        }
        if(response.answer == "true"){
          Response = response.answer;
          console.log(Response);
          functions.checkResponse(Response);
        }

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

// submitFormButton.addEventListener("submit", function(event){
//     event.preventDefault();   // stop the form from submitting
//     let unique_id = document.getElementById("unique_id").value;
//     // let password = document.getElementById("password").value;

//     // var Token;

//     var postData = {
//         'unique_id' : unique_id,
//         // 'password' : password
//     };
//       console.log(JSON.stringify(postData));
//       var options = {
//         hostname: '127.0.0.1',
//         port: 100,
//         path: '/test',
//         method: 'POST',
//         body:    JSON.stringify(postData),
//         headers: {
//           'Content-Type': 'application/json'
//         }
//       };
    
//       var req = http.request(options, (res) => {
//         console.log(`STATUS: ${res.statusCode}`);
//         console.log(`HEADERS: ${JSON.stringify(res.headers)}`);
//         res.setEncoding('utf8');
//         res.on('data', (chunk) => {
//           var response = JSON.parse(`${chunk}`);
//           Response = response.answer;
//           functions.checkResponse(Response);
//           document.getElementById("token").innerHTML = Response;
//         });
//         res.on('end', () => {
//           console.log('No more data in response.')
//         })
//       });
      
//       req.on('error', (e) => {
//         console.log(`problem with request: ${e.message}`);
//       });
      
//       // write data to request body
//       req.write(JSON.stringify(postData));
//       req.end();
// });



