var $j = jQuery.noConflict();

function closeWindow(errorContainer, modalId){
	if (hasNoErrors(errorContainer)) {
		$j(modalId).modal('hide');
	}
}

function hasNoErrors(errorContainer) {
	if (document.getElementById(errorContainer) == null){
		return true;
	}
	return false;
}

function setCaretToEnd(e) {
	var control = $((e.target ? e.target : e.srcElement).id);
	if (control.createTextRange) {
		var range = control.createTextRange();
		range.collapse(false);
		range.select();
	} else if (control.setSelectionRange) {
		control.focus();
		var length = control.value.length;
		control.setSelectionRange(length, length);
	}
	control.selectionStart = control.selectionEnd = control.value.length;
};

function copyValue(sourceId, destinationId) {
	$j(destinationId).val($j(sourceId).val());
}

function copyFileName(sourceId, destinationId) {
	var filename = $j(sourceId).val().replace(/^.*[\\\/]/, '');
	$j(destinationId).val(filename);
}