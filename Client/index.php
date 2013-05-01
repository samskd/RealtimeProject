<html>
<head>
	<title> Trending Topics </title>

	<script src="scripts/jquery-1.9.1.min.js"></script>
	<script src="scripts/jquery.awesomeCloud-0.2.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="stylesheets/wordCloud.css">
	
</head>

<body>
	<div role="main">
		<div id="wordCountCloud" class="wordcloud">
			<?php
				$window = 1/6; //10 sec default
				if(isset($_GET['window'])){
					$window = $_GET['window'];
				}
				$response = file_get_contents("http://localhost:1337/?window=$window");
				
				if($response){
					$json = json_decode($response);
				}
				
				$highestCount = 1;
				$wc = array();
				
				//combine all the word counts.
				foreach($json as $timestamp => $wordCount){
					foreach($wordCount as $word => $count){
						$tempCount = 0;
						if(array_key_exists($word, $wc)){
							$tempCount = $wc[$word] + $count;
						}else{
							$tempCount = $count;
						}
						
						if($tempCount > 0){
							$wc[$word] = $tempCount;
							if($tempCount > $highestCount){
								$highestCount = $tempCount;
							}
						}
					}
				}
				
				//display the words with the normalized weights.
				foreach($wc as $word => $count){
					echo "<span data-weight=$count>$word</span>";
				}
				
			?>
		</div>
	</div>
	<script src="scripts/wordCloud.js"> </script>
</body>
</html>