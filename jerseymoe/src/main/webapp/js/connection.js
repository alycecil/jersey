var conection = new function() {
	var _base;
	var _token;
	this.init = function(base, token) {
		_base = base;
		_token = token;
	};
	this.get = function(url, type,callback, error) {
		if(_base!=null){
			url=_base+url;
		}
		if(type==null){
			type='get';
		}
		$.ajax(url, {

			'crossDomain' : true,
			'type' : type,
			'processData' : false,

			'dataType' : 'json',
			'success' : callback,
			'error' : error,

			beforeSend : function(req) {
				if (_token != null) {
					req.setRequestHeader('Authorization', 'Token ' + _token);
				}
			}

		});
	};
};