function submitForm() {
	// document.myform.action =
	// document.myform.word.disabled = true;
	// document.myform.submit();
	window.location = transtype + document.getElementById("word").value
}

document.onkeydown = function() {
	if (window.event.keyCode == '13') {
		submitForm();
	}
}