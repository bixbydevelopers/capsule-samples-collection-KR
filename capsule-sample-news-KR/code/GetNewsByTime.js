module.exports.function = function getNewsByTime (dateTimeExpression, dayOfWeek, detachedTime) {
  const console = require('console');
  const dates = require('dates');
  const fakeData = require("./data/NewsData.js"); // 정확한 데이터를 가져오시려면, data내 date값을 변경하여 주시기 바랍니다.
  const dow = ["월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"];

  let news = [];
  let date = '';
  let baseTime = new dates.ZonedDateTime.now();
  console.log(baseTime);
   
  if(detachedTime){ // time 라이브러리를 통한 시간 계산
    console.log(detachedTime);

    let temp = fakeData['Recent'];
    date = baseTime.getYear() + "/" + baseTime.getMonth() + "/" + baseTime.getDay();

    for(let i = 0; i < temp.length; ++i){
      if(temp[i].date == date){
        temp[i].time = detachedTime.amPM + ' ' + detachedTime.hour;
        news.push(temp[i]);
      }    
    }

  }else{ // time 라이브러리를 통한 날짜 계산
    if(dayOfWeek){ 
      console.log(dayOfWeek);
      console.log(dateTimeExpression); 

      let temp = fakeData['Recent'];
      let tempDate = dateTimeExpression.dateInterval.start;
      let dateObject = baseTime.withYear(tempDate.year).withMonth(tempDate.month).withDay(tempDate.day);

      for(let i = 0; i< dow.length; ++i){
        if(dow[i] == dayOfWeek){
          tempDate = dateObject.plusDays(i);
        }
      }
      console.log(dateObject);
      date = tempDate.getYear() + "/" + tempDate.getMonth() + "/" + tempDate.getDay();
      
      for(let i = 0; i < temp.length; ++i){
        if(temp[i].date == date){
          temp[i].dayofweek = dayOfWeek;
          news.push(temp[i]);
        }    
      }   

    }else{
      console.log(dateTimeExpression);

      let temp = fakeData['Recent'];
      let tempDate = dateTimeExpression.date;
      let dateObject = baseTime.withYear(tempDate.year).withMonth(tempDate.month).withDay(tempDate.day);
      let dayOfWeekResult = dateObject.getDayOfWeek();
      date = tempDate.year + "/" + tempDate.month + "/" + tempDate.day;
      
      console.log(dateObject);

      for(let i = 0; i < temp.length; ++i){
        if(temp[i].date == date){
          temp[i].dayofweek = dow[dayOfWeekResult];
          news.push(temp[i]);
        }    
      }    
    }
  }

  return news;
}
