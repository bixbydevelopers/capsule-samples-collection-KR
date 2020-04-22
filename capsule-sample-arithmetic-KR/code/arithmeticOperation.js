module.exports.function = function arithmeticOperation (leftOperand, rightOperand, operator) {// 예) (3, 4, plus(더하기))
  const console = require('console');
  let result = 0;
  let name = '';
  
  operator = String(operator);
  
  if(operator === "plus"){
    name = "더하기";
    result = leftOperand + rightOperand;
  }else if(operator === "subtract"){
    name = "빼기";
    result = leftOperand - rightOperand;
  }else if(operator === "multiplication"){
    name = "곱하기";
    result = leftOperand * rightOperand;
  }else if(operator === "division"){
    name = "나누기";
    result = leftOperand / rightOperand;
  }
  
  console.log(operator);
   
  return {
    operator: name,
    result: result
  };
}
