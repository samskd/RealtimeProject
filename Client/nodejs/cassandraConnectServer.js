var cassandraHost = "127.0.0.1";
var cassandraPort = 9160;
var keyspace = "TrendingTopics";
var http = require('http');
var con;
var timeDifference = 600000 //10 minutes in milliseconds

setupConnection();

http.createServer(function (req, res) {
  	
  	
  	var now = new Date().getTime();
  	var tenMinutesAgo = now - timeDifference;
  	
  	con.execute("SELECT * FROM wordCount WHERE key = ?", [now], function (err, rows) {
    
    if (err) {
        console.log("ERROR: "+err);
    } else {
        console.log(rows.rowCount());
        console.log(rows[0]);
        
        var response = {};
        for(i=0; i<rows.rowCount(); i++){
        	response[rows[i].key] = rows[i].colHash;
        }
        response = JSON.stringify(response);
        console.log("Response: "+response);
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
