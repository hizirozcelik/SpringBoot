function getEvent(eventId) {

	if (document.getElementById("event" + eventId).innerHTML == "") {

		fetch('http://localhost:8080/getEvent/' + eventId) // use HomeController to fetch from our service
			.then(event => event.json()) // JSONify the data returned
			.then(function(event) { // with the JSON data
				// modify textToDisplay below here!

				var textToDisplay = ""; // create and append to a blank var 
				if (event.eventDate == null){
					textToDisplay += "Date: " + "" + "<br>";
					} else {
				textToDisplay += "Date: " + event.eventDate + "<br>"; 
				}
				if (event.eventTime == null){
					textToDisplay += "Time: " + "" + "<br>";
					} else {
					textToDisplay += "Time: " + event.eventTime + "<br>";
				}
				if (event.details == null){
					textToDisplay += "Details: " + "" + "<br>";
					} else {
					textToDisplay += "Details: " + event.details + "<br>";
				}
				
				textToDisplay += "Category: " + event.category + "<br>";
				textToDisplay += "Tag: " + event.tag + "<br>";
				// finally, change our relevant div to display the var
				document.getElementById("event" + eventId).innerHTML = textToDisplay;

			});

	} else {
		document.getElementById("event" + eventId).innerHTML = "";
	}
};
