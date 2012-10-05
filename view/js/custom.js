var $j = jQuery.noConflict();

function closeWindow(container, modalId){
	if (document.getElementById(container) == null){
		$j(modalId).modal('hide');
	};
};

function setCaretToEnd (e) {
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