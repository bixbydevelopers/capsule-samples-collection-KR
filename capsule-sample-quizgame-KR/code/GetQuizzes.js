module.exports.function = function getQuizzes (category, type) {
  const categoryList = require("./utils/CategoryList.js");
  const config = require('config');
  const baseAPIURL = config.get("baseAPIURL");
  const http = require('http');
  const fail = require('fail');
  
  let textLibutils = require("./utils/CleanText.js");
  let url = baseAPIURL;
  category = parseInt(category);
  
  if(type == '랜덤형'){
    type = 'any'
  }else if(type == '사지선다형'){
    type = 'multiple'
  }else{
    type = 'boolean'
  }
  
  if(type != 'any' && categoryList[category].number != 'any'){
    url += '&type=' + type + '&category=' + categoryList[category].number;
  }else if(type == 'any' && categoryList[category].number != 'any'){
    url += '&category=' + categoryList[category].number;
  }else if(type != 'any' && categoryList[category].number == 'any'){
    url += '&type=' + type;
  }
  
  let response = http.getUrl(url, {format:"json", cacheTime: 0, returnHeaders:true});

  if(response.status == 404 || response.status == 500 || response.status == 502 || response.status == 504){ 
    throw fail.checkedError("Server Error", "ServerProblem"); 
  }
  
  if(response.parsed.response_code == 2){  
    throw fail.checkedError("Server Error", "ServerProblem"); 
  }
  
  if(response.parsed.response_code == 1){ 
    throw fail.checkedError("No Data", "NODATA");
  }
  
  let quizzes = response.parsed.results;
  let rand = 0;
  
  for(let i = 0; i < quizzes.length; ++i){
    rand = Math.floor(Math.random() * 4);
    quizzes[i].id = i + 1;    
    quizzes[i].question = textLibutils.CleanText(quizzes[i].question.trim());
    
    if(quizzes.type == "boolean"){
      quizzes[i].incorrect_answers.splice(rand, 0, quizzes[i].correct_answer);
    }else{
      if(quizzes[i].correct_answer == "True"){
        quizzes[i].incorrect_answers.splice(0, 0, quizzes[i].correct_answer);
      }else{
        quizzes[i].incorrect_answers.push(quizzes[i].correct_answer);
      }
    }
    
    quizzes[i].answers_list = quizzes[i].incorrect_answers;
    
    for(let j = 0; j < quizzes[i].answers_list.length; ++j){
      quizzes[i].answers_list[j] = textLibutils.CleanText(quizzes[i].answers_list[j].trim());
    }
    
    quizzes[i].result = false;
    quizzes[i].incorrect_answers = null;
    quizzes[i].selected = -1;
  }

  return {
    quizzes: quizzes,
    nowNum: 0,
    totalNum: quizzes.length,
    correctNum: 0
  };
}
