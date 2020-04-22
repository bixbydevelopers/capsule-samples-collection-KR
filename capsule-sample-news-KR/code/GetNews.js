module.exports.function = function getNews(topic) {
  // 다른 경로의 파일을 가져옴
  const fakeData = require("./data/NewsData.js");
  const console = require('console');
  let result = [];
  
  if(topic){
    result = fakeData[topic];
    for(let i = 0; i < result.length; ++i){
      result[i].topic = topic;
    }
  }
  
  console.log(result);
  return result;
}
