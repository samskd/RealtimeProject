var cassandraHost = "127.0.0.1";
var cassandraPort = 9160;
var keyspace = "TrendingTopics";
var http = require('http');
var url = require('url');
var con;
var defaultWindow = 60000 //1 minutes in milliseconds

setupConnection();

http.createServer(function (req, res) {
  	
  	var now = new Date().getTime();
  	var start = now - defaultWindow;
  	
	var query = url.parse(req.url, true);
	var windowLengthInMin = query.window;
	
	if(windowLengthInMin != null){
		start = now - windowLengthInMin*60000;
	}
  	
  	
  	con.execute("SELECT * FROM wordCount WHERE key > ?", [start], function (err, rows) {
    
    			if (err) {
        			console.log("ERROR: "+err);
    			} else {
        			//console.log(rows.rowCount());
        			
        			var response = {};
        			for(i=0; i<rows.rowCount(); i++){
        				//console.log(rows[i]);
        				response[rows[i].key] = rows[i].colHash;
        			}
        			
        			response = JSON.stringify(response);
   					//console.log("Response: "+response);
    				res.write(response);
    				res.end();
    			}
    	
    	});
	
}).listen(1337, '127.0.0.1');

console.log('Server running at http://127.0.0.1:1337/');



function setupConnection(){
	var Connection = require('cassandra-client').Connection;
	con = new Connection({host:cassandraHost, port:cassandraPort, keyspace:keyspace});
	con.connect(function(err) {
		if(err){
			console.log("ERROR : Error connecting to the keyspace "+keyspace);
			console.log(err);
		}
	});
}
