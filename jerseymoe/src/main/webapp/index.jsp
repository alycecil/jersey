<html>
<head>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/handlebars-v2.0.0.js"></script>
	<script type="text/javascript" src="js/connection.js"></script>
	<script type="text/javascript">
	
	function error(msg){
		alert(msg);
	}
	
	function callback(data){
		var source   = $("#helper-template").html();
		var template = Handlebars.compile(source);
		
		var html    = template(data);
		
		$('body').append(html);
	}
	
	function onLoad(){
		
		conection.get('webapi/common/list','get',callback,error);
	}
	</script>
</head>
<body onLoad="onLoad()">
    <h2>Jersey Moe RESTful Web Application!</h2>
    <p><a href="webapi/common/list">Jersey resource rest method list</a>
    
    
<script id="helper-template" type="text/x-handlebars-template">
<p>
{{#each this}}
<p>		<span class="blockname">{{name}}</span> 
<br>	path <span class="blockpath">{{path}}</span>
 {{#each methods}}
   <p>		<span class="methodname">{{name}}</span> 
   <br>	path <span class="methodpath">{{path}}</span>
   <br>	type <span class="methodtype">{{method}}</span>
<br> params
 {{#each params}}
   <br>		<span class="paramname">{{name}}</span> 
   <br>	type <span class="paramtype">{{type}}</span>
   <br>	form <span class="paramform">{{form}}</span>
 {{/each}}
 {{/each}}
{{/each}}
</script>
</body>
</html>
