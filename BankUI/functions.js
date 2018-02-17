const { remote, BrowserWindow } = require('electron');
const win = remote.getCurrentWindow();
const path = require('path')
const url = require('url')



function changeWindow(html){
  win.loadURL(url.format({
    pathname: path.join(__dirname, html),
    protocol: 'file:',
    slashes: true
  }))
}

function checkResponse(response){
  if(response == "true"){
    changeWindow("login.html"); 
  }
  else{
    alert("Unique ID not found!");
    changeWindow("index.html");
  }
}



 module.exports.checkResponse = checkResponse;