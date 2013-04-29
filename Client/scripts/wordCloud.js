$(document).ready(function(){
		
		$("#wordCountCloud").awesomeCloud({
					"size" : {
						"grid" : 9, // word spacing, smaller is more tightly packed
						"factor" : 0, // font resize factor, 0 means automatic
						"normalize" : true // reduces outliers for more attractive output
					},
					"color" : {
						"background" : "rgba(255,255,255,0)", // background color, transparent by default
						"start" : "#20f", // color of the smallest font, if options.color = "gradient""
						"end" : "rgb(200,0,0)" // color of the largest font, if options.color = "gradient"
					},
					"options" : {
						"color" : "random-dark",  // default = "gradient" if "random-light" or "random-dark", color.start and color.end are ignored
						"rotationRatio" : 0.5, // 0 is all horizontal, 1 is all vertical
						"printMultiplier" : 1, // set to 3 for nice printer output; higher numbers take longer
						"sort" : "highest" // "highest" to show big words first, "lowest" to do small words first, "random" to not care				
					},
					"font" : "'Times New Roman', Times, serif", // the CSS font-family string
					"shape" : "circle" // the selected shape keyword, or a theta function describing a shape
				});
				
	//setInterval('window.location.reload()', 10000);
				
});