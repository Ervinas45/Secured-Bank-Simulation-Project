const remote = require('./renderer.js');
const functions = require('./functions.js');
const transactions = require('./transactions.js');
const http = require('http');

const addMoneyButton = document.getElementById('addMoney');
const removeMoneyButton = document.getElementById('removeMoney');
const loanAddButton = document.getElementById('addLoan');
const payLoanButton = document.getElementById('payLoan');

var regex = /^(?:[1-9]\d*|\d)$/;


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
    if(regex.test(funds) == true){
      if(funds < 0 || funds == 0){
        alert("Entered value is below or equal to 0! Refused transaction!");
      }
      else{
        var answer = transactions.transaction('add', funds);
      }
    }
    else{
      alert("Only Integer values are acceptable and no leading zero : 0xxxx!");
    }
  })

  removeMoneyButton.addEventListener('click', function(){
    var funds = parseInt(document.getElementById('moneyToRemove').value);
    var currentMoney = parseInt(document.getElementById('funds').innerHTML);
    if(regex.test(funds) == true){
      if(funds < 0 || funds == 0){
        alert("Entered value is below or equal to 0! Refused transaction!");
      }
      else{
        if(currentMoney > funds){
          var answer = transactions.transaction('withdraw', funds);
        }
        else{
          alert("Transaction refused, you don't have money to withdraw");
        }
      }
    }
    else{
      alert("Only Integer values are acceptable and no leading zero : 0xxxx!");
    }   
  })

  loanAddButton.addEventListener('click', function(){
    var loanToAdd = document.getElementById('loanToTake').value;
    if(regex.test(loanToAdd) == true){
      if(loanToAdd < 0 || loanToAdd == 0){
        alert("Entered value is below or equal to 0! Refused transaction!");
      }
      else{
        var answer = transactions.transaction('loan', loanToAdd);
      }
    }
    else{
      alert("Only Integer values are acceptable and no leading zero : 0xxxx!");
    }      
  })
  
  payLoanButton.addEventListener('click', function(){
    var dept = parseInt(document.getElementById('dept').innerHTML);
    var currentMoney = parseInt(document.getElementById('funds').innerHTML);
    if(dept > currentMoney){
      console.log("1");
      alert('Your current balance is not higher then current dept! Transaction refused.');
    }
    if(dept == 0 && currentMoney == 0){
      console.log("2");
      alert('Your current balance is not higher then current dept! Transaction refused.');
    }
    if(dept <= currentMoney && dept != 0 && currentMoney != 0){
      transactions.transaction('dept', dept);
    }
    if(dept == 0){
      alert('Your current dept balance is 0! Transaction refused.');
    }
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