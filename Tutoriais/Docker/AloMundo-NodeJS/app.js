const express = require('express');
const app = express();
const PORT = process.env.PORT || 3000;

app.get('/', (req, res) => {
  var s = '<p>Hello World!</p>';
  s += '<p>'+ new Date().toString(); + '</p>';
  res.send(s);
});

app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
