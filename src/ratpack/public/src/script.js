function submitForm() {
	window.location = transtype + document.getElementById("word").value
}

document.onkeydown = function() {
	if (window.event.keyCode == '13') {
		submitForm();
	}
}