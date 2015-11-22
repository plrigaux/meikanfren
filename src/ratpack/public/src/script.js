function submitForm() {
	document.translate.submit();
}

document.onkeydown = function() {
	if (window.event.keyCode == '13') {
		submitForm();
	}
}

function setFocusToTextBox(){
    document.getElementById("word").focus();
}