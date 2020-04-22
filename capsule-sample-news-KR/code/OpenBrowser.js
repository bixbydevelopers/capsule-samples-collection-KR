module.exports.function = function openBrowser (url, tel) {
  const console = require('console');
  console.log(url);
  console.log(tel);
  
  let result = '';
  if(tel != undefined){
    result = tel;
  }else{
    result = url;
  }
  
  return result;
}
