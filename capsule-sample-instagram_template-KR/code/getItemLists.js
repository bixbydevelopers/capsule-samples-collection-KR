var http = require('http')
var console = require('console')
module.exports.function = function getItemLists() {
  let url = "https://www.instagram.com/instagram/"; // instagram url
  let timeline = url + "?__a=1"; // timeline
  
  let results = [];
  if (timeline != "undefined") {
    let response = http.getUrl(timeline, {
      format: "json",
      cacheTime: 0,
    });
    // console.debug('response', response);

    results.push({
      creator: response.graphql.user.full_name,
      thumbnail: response.graphql.user.profile_pic_url_hd,
      uri: url
    });
    // console.debug('results', results);

    let items = [].concat(response.graphql.user.edge_owner_to_timeline_media.edges);
    items.forEach(function(item) {
      var temp = {}; 
      temp.itemlink = "https://www.instagram.com/p/" + item.node.shortcode; 
      temp.liked = item.node.edge_liked_by.count;
      if (typeof(item.node.edge_media_to_caption.edges[0]) == "undefined") {
        temp.title = null; 
        temp.description = null; 
      } else {
        temp.title = item.node.edge_media_to_caption.edges[0].node.text; 
        temp.description = item.node.edge_media_to_caption.edges[0].node.text; 
      }
      temp.thumbnail = item.node.thumbnail_src;
      results = results.concat(temp);   
    }); 
    // console.debug('results', results);
  }

  return results;
}
