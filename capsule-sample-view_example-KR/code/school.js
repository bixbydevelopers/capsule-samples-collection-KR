var console = require('console');

module.exports.function = function school() {
  var data = require('/data/schoolinfo.js');
  data = data[0];

  var iter = data.count;

  var res = [];
  var date = data.date;

  for (var i = 0; i < iter; i++) {
    var tmp_dish = [];
    var tmp_cnt = 0;

    if (data.data[i].rice != null) {
      tmp_dish[tmp_cnt++] = data.data[i].rice;
    }
    if(data.data[i].soup != null) {
      tmp_dish[tmp_cnt++] = data.data[i].soup;
    }
    if(data.data[i].side1 != null) {
      tmp_dish[tmp_cnt++] = data.data[i].side1;
    }
    if(data.data[i].side2 != null) {
      tmp_dish[tmp_cnt++] = data.data[i].side2;
    }
    if(data.data[i].side3 != null) {
      tmp_dish[tmp_cnt++] = data.data[i].side3;
    }
    if(data.data[i].side4 != null) {
      tmp_dish[tmp_cnt++] = data.data[i].side4;
    }
    console.log(tmp_dish[5])
    if(tmp_dish[5] == "")
      tmp_dish[5] = " ";

    res[i] = {
      schoolname: data.data[i].name,
      date: date,
      url: data.data[i].url,
      dish : tmp_dish,
      address : data.data[i].address,
    }
  }
  
  return res;
}
