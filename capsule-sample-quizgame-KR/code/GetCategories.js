module.exports.function = function getCategories () {
  // 다른 경로의 파일을 호출
  const categoryList = require("./utils/CategoryList.js");
  
  let categories = [];
  
  for(let i =0; i < categoryList.length; i++)
    categories.push(categoryList[i].name);
  
  return categories;
}
