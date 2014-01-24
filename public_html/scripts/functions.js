function getDaysToDate(date, elementId){
	
	var today = new Date();
	var diff = Math.abs(date - today);
	var days = Math.floor(diff / (1000 * 60 * 60 * 24));
	if (!isNaN(days)){	
		document.getElementById(elementId).innerHTML = " (" + days + " days left!)";
	}
}

function moveTo(elementId, opt_percent){
	
	percent = typeof opt_percent !== 'undefined' ? opt_percent : 10;
	
	var fixedHeader = document.getElementById("fixed-header");
	var headerOffset = fixedHeader.offsetHeight;
	
	var element = document.getElementById(elementId);
	var topPosition = element.offsetTop;
	var newPosition = topPosition - headerOffset;

	scrollTo(0, (newPosition * (percent/100)));
	
	if (percent < 100){
		setTimeout(function(){moveTo(elementId, percent + 10);}, 30);
	}
}
