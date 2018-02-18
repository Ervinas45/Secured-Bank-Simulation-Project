// This file is required by the index.html file and will
// be executed in the renderer process for that window.
// All of the Node.js APIs are available in this process.
const queryString = require('query-string');
const http = require('http');
const remote = require('electron');
const functions = require('./functions.js');

const os = require('os');
const localStorage = require('electron-json-storage');

localStorage.setDataPath(os.tmpdir());
let Token;
let Response;
let ID;
//let Password;

function sendRequest(variable){

  var postData = {};

  if(variable == "unique_id"){
    var value = document.getElementById(variable).value;
    postData['unique_id'] = value;
    localStorage.set('session', { unique_id: value }, function(error) {
      if (error) throw error;
    });
  }

  if(variable == "password"){

    var value = document.getElementById(variable).value;

    //random skipping this part
    localStorage.get('session', function(error, data) {
      if (error) throw error;
    
      console.log("A");
    });

    postData = {
      // 'unique_id' : unique_id,
      'password' : value
    }

    alert(JSON.stringify(postData));
  }
  
  console.log(JSON.stringify(postData));
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
      Response = response.answer;
      functions.checkResponse(Response);
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

  console.log(postData);
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



