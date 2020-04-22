module.exports.function = function showQuiz (quizlist, answer, ordinal)  {
  // const console = require('console'); 
  let quiz = null;
  
  if(ordinal == undefined && answer != undefined){    
    quiz = quizlist.quizzes[quizlist.nowNum];
    
    if(String(quiz.correct_answer) == String(answer)){

      quiz.result = true;
      quizlist.correctNum += 1;
    }
    
    for(let i = 0; i < quiz.answers_list.length; i++){
      if(String(quiz.answers_list[i]) == String(answer)){
        quiz.selected = i;
        break;
      }        
    }
    
    quizlist.nowNum += 1;
    
  }else if(ordinal != undefined && ordinal < 5 && ordinal > 0){    
    quiz = quizlist.quizzes[quizlist.nowNum];
    
    if((quiz.type == "boolean" && ordinal < 2) || quiz.type == "multiple"){
      if(String(quiz.correct_answer) == String(quiz.answers_list[ordinal - 1])){

        quiz.result = true;
        quizlist.correctNum += 1;
      }

      quiz.selected = ordinal - 1;
      quizlist.nowNum += 1;
    }
  }
  
  return quizlist;
}
