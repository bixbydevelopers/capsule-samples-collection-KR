module.exports.function = function getNewsDetail (news) {
  const console = require('console');
  
  console.log(news);
  let tel = undefined;
  if(news.tel != undefined){
    tel = 'tel:' + news.tel;
  }
  
  return {
    title: news.title,
    url: news.url,
    tel: tel,
    topic: news.topic
  };
}
