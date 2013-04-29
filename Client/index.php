<html>
<head>
	<title> Trending Topics </title>

	<script src="scripts/jquery-1.9.1.min.js"></script>
	<script src="scripts/jquery.awesomeCloud-0.2.min.js"></script>
	<script src="scripts/wordCloud.js"> </script>
	<link rel="stylesheet" type="text/css" href="stylesheets/wordCloud.css">
	
</head>

<body>
	<div role="main">
		<div id="wordcloud" class="wordcloud">
			<?php
				$response = file_get_contents("http://localhost:1337/");
				echo $response;
				
			?>
		</div>
	</div>
</body>
</html>