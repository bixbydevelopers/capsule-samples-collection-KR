var console = require('console')
module.exports.function = function itemPunchOut (results) {
  var uri;
  if (results.creator != undefined || results.creator != null) {
    uri = results.uri;
  } else {
    uri = results.itemlink;
  }
  return uri;
}
 