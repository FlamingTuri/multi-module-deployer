const express = require('express');
const app = express();
const axios = require('axios');

pingSentCount = 0;

app.get('/api/status', function (req, res) {
  res.send('pinger ms status: active');
});

app.get('/api/ping', function (req, res) {
  const jsonResponse = {
    "ping": pingSentCount
  }
  res.send(jsonResponse);
});

function sendPing() {
  axios.post('http://localhost:8080/api/ping', {
    "ping": ++pingSentCount
  }).then(response => {
    const pongCount = response.data.pong;
    if (pongCount) {
      console.log(`received pong ${pongCount}`);
      setTimeout(sendPing, 2000);
    } else {
      console.log(`wrong data received ${response.data}`);
    }
  }).catch(error => {
    console.log(error);
  });
  console.log(`sent ping ${pingSentCount}`);
}

app.listen(3000, () => {
  console.log('Ping service listening on port 3000');

  sendPing();
});
