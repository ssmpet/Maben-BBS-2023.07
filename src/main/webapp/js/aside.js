/**
 * aside menu control을 위해 사용됨
 */

 $(document).ready(function() {
	$('#stateMsgBtn').click(function(e) {
		$('#stateMsgInput').attr({'class': 'mt-2'});
		$('#stateInput').attr({value: $('#stateMsg').text()});
		
	}) 
	$('#stateMsgSubmit').click(changeStateMsg);
 });
 
 function changeStateMsg() {

	 $('#stateMsgInput').attr({'class': 'mt-2 d-none'});
	 let stateInputVal = $('#stateInput').val();

	 $.ajax({
		 type: 'GET',
		 url: '/bbs/aside/stateMsg',
		 data: {stateMsg: stateInputVal},
		 success: function(e) {
			 $('#stateMsg').html(stateInputVal);
		 }		 
	 });
}