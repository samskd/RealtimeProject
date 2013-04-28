var cassandraHost = "127.0.0.1";
var cassandraPort = 9160;
var keyspace = "TrendingTopics";
var http = require('http');
var con;

setupConnection();

http.createServer(function (req, res) {
  	
  	con.execute('SELECT * FROM wordCount WHERE key >= ? and key <= ?', [12345677, 12345678], function (err, rows) {
    
    if (err) {
        console.log("ERROR: "+err);
    } else {
        console.log(rows.rowCount());
        console.log(rows[0]);
                    //assert.strictEqual(rows[0].colCount(), 1);
                    //assert.ok(rows[0].colHash['cola']);
                    //assert.ok(rows[0].cols[0].name === 'cola');
                    //assert.ok(rows[0].cols[0].value === 'valuea');
    }
});
	
}).listen(1337, '127.0.0.1');

console.log('Server running at http://127.0.0.1:1337/');



function setupConnection(){
var Connection = require('cassandra-client').Connection;
con = new Connection({host:cassandraHost, port:cassandraPort, keyspace:keyspace});
console.log(con);
con.connect(function(err) {
	console.log("ERROR : Error connecting to the keyspace "+keyspace);
	console.log(err);
});
}