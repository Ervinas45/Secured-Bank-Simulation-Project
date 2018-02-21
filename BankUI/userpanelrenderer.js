const remote = require('./renderer.js');
const functions = require('./functions.js');
const transactions = require('./transactions.js');
const http = require('http');

const addMoneyButton = document.getElementById('addMoney');
const removeMoneyButton = document.getElementById('removeMoney');
const loanAddButton = document.getElementById('addLoan');
const payLoanButton = document.getElementById('removeMoney');

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


  addMoneyButton.addEventListener('click', function(){
    var funds = document.getElementById('moneyToAdd').value;
    var answer = transactions.transaction('add', funds);
  })

  removeMoneyButton.addEventListener('click', function(){
    var funds = document.getElementById('moneyToRemove').value;
    var answer = transactions.transaction('withdraw', funds);
  })

  loanAddButton.addEventListener('click', function(){
    var loanToAdd = document.getElementById('loanToTake').value;
    var answer = transactions.transaction('loan', loanToAdd);
  })
  
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

  function logout(parameter){
    remote.store.set('token', null);
    remote.store.set('unique_id', null);
    functions.checkResponse(parameter);
  }

  function refresh(parameter){
    location.reload();
  }