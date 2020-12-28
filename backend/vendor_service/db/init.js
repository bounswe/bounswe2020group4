const mongoose = require("mongoose");

module.exports.initialize = async function () {
  try {
    await mongoose.connect(process.env.MONGO_URL, { useNewUrlParser: true, useUnifiedTopology: true });
  } catch (err) {
    console.log(err);
  }
};
