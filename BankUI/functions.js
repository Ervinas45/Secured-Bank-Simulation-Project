const { remote, BrowserWindow } = require('electron');
const win = remote.getCurrentWindow();
const path = require('path')
const url = require('url')

function checkResponse(response){
  if(response === "true"){
    changeWindow("login.html"); 
  }
  if(response === "token"){
    changeWindow("home.html");
  }
  if(response === "false"){
    changeWindow("index.html");
  }
}

function changeWindow(html){
  win.loadURL(url.format({
    pathname: path.join(__dirname, html),
    protocol: 'file:',
    slashes: true
  }))
}





 module.exports.checkResponse = checkResponse;