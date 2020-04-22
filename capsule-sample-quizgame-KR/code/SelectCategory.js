module.exports.function = function selectCategory (category) {
  // 다른 경로의 파일을 호출
  const categoryList = require("./utils/CategoryList.js");
  const console = require('console');
  
  let categoryNum = '';
  console.log(category);

  for(let i = 0; i < categoryList.length; i++){
    if(categoryList[i].name == category){
      categoryNum = String(i);
      break;
    }
  }   
  
  return categoryNum;
}
