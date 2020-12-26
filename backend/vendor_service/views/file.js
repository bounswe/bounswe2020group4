const AWS = require("aws-sdk");
const s3 = new AWS.S3({
  accessKeyId: process.env.AWS_ACCESS_KEY_ID,
  secretAccessKey: process.env.AWS_SECRET_ACCESS_KEY,
});

module.exports.upload = async (files) => {
  try {
    const promises = [];

    Object.keys(files).map((key, index) => {
      promises.push(
        s3
          .upload({
            Bucket: process.env.AWS_BUCKET_NAME,
            Key: "images/" + new Date().getTime() + index,
            Body: files[key].data,
            ContentType: "image/png",
            ACL: "public-read",
          })
          .promise()
      );
    });

    return (await Promise.all(promises)).map((data) => data.Location);
  } catch (error) {
    console.log(error);
    return error;
  }
};
