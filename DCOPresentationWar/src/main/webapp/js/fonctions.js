function getData(url, callback)
{
	var ret = $.ajax({
		async:false,
		type: 'GET',
		url: url,
		dataType: 'json',
		cache:false,
		success: function(data){
				callback(data);
		}
	});
	
	return ret;
}

function setData(url2, data2)
{
	var ajaxResponse;
	$.ajax({
		type: 'POST',
		url: url2,
		data: data2,
		error: function(jqXHR, exception) {
            if (jqXHR.status === 0) {
                alert('Not connect.\n Verify Network.');
            } else if (jqXHR.status == 404) {
                alert('Requested page not found. [404]');
            } else if (jqXHR.status == 500) {
                alert('Internal Server Error [500].');
            } else if (exception === 'parsererror') {
                alert('Requested JSON parse failed.');
            } else if (exception === 'timeout') {
                alert('Time out error.');
            } else if (exception === 'abort') {
                alert('Ajax request aborted.');
            } else {
                alert('Uncaught Error.\n' + jqXHR.responseText);
            }
        },
        success: function(response) {
        	ajaxResponse = response;
        }
	});
	return ajaxResponse;
}