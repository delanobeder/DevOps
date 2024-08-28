
// index.js 
const express = require("express");
const bodyParser = require("body-parser");
const ejs = require("ejs");
const mongoose = require("mongoose");

const app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static("public"));

const HOST = process.env.MONGO_INITDB_HOST
const USER = process.env.MONGO_INITDB_ROOT_USERNAME
const PASS = process.env.MONGO_INITDB_ROOT_PASSWORD

const URI = "mongodb://"+ USER + ":" + PASS + "@" + HOST;
console.log(URI);
mongoose.connect(URI);

const taskSchema = {
  name: {
    type: String,
    required: true
  }
};

const Task = mongoose.model("Task", taskSchema);

app.set("view engine", "ejs");

app.get("/", async (req, res) => {

  let today = new Date();
  let options = { weekday: "long", day: "numeric", month: "long" };
  let day = today.toLocaleDateString("pt-BR", options);

  try {
    const tasks = await Task.find();
    res.render("index", { today: day, tasks: tasks });
  } catch (err) {
    console.log(err)
  }
});

app.post("/", function (req, res) {
  const taskName = req.body.newTask;
  if (taskName) {
    const task = new Task({
      name: taskName,
    });
    task.save().then(() => {
      res.redirect("/");
    });
  } else {
    res.redirect("/");

  }
});

app.post("/delete", async (req, res) => {
  const checkedItemId = '' + req.body.checkbox;
  try {
    await Task.deleteOne({ _id: new mongoose.Types.ObjectId(checkedItemId)});
  } catch (err) {
    console.log(err);
  }
  console.log("Successfully deleted checked item.");
  res.redirect("/");
});

const PORT = process.env.PORT || 3000
app.listen(PORT, function () {
  console.log("Server running at port "+ PORT);
});
