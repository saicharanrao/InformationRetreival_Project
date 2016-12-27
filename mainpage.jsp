<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

<title>Grand Search Auto</title>
<style>
.parent div {
	display: inline;
	/* Child style. */
}
.borderexample
{
border-style:solid;
border-color:#287EC7;
} 
</style>

<script>
	$(document).ready(
			function() {
				$("#tab2").hide();
				$("#tab1").show();
				$("#tab3").hide();

				$("#tab1link").click(function() {
					$("#tab2").hide();
					$("#tab1").show();
					$("#tab3").hide();
				});
				$("#tab2link").click(function() {
					$("#tab1").hide();
					$("#tab2").show();
					$("#tab3").hide();
				});
				$("#tab3link").click(function() {
					$("#tab1").hide();
					$("#tab3").show();
					$("#tab2").hide();
				});
				function showInput() {
					document.getElementById('display').innerHTML = document
							.getElementById("qr1").value;
				}

			});
</script>
<script>
	function showInput() {
		document.getElementById('display').innerHTML = document
				.getElementById("qr1").value;
	}
</script>



</head>
<body>
	<center>
		<p class="borderexample"><h1>Grand Search Auto</h1></p>
			</center>
	<div align="center" class="par1">
		<br />
		<form class="form-inline" method="get" action="controller">
			<input id="qr1" type="text" class="form-control" size="80"
				name="searchquery" />
			<button type="submit" onclick="showInput();" class="btn btn-primary">submit</button><br>
			<label> Association Cluster <input type="checkbox" name="qe"
				value="Association Cluster"  />
			</label> <label> Metric Cluster  <input type="checkbox" name="qe"
				value="Metric Cluster " />
			</label> <label>Scalar<input type="checkbox" name="qe"
				value="Scalar" />
			</label>
			</label> <label>Rocchio<input type="checkbox" name="qe"
				value="Rocchio" />
			</label>
		</form>


	</div>
	<div  class="par1" align="center">
	<p>
		Query: "<%=request.getAttribute("g") == null ? "" : request.getAttribute("g")%>"
		<br>
		Query Expansion technique :"<%=request.getAttribute("g1") == null ? "" : request.getAttribute("g1")%>"
	</p>
	</div>
	
	
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li class="active"><a id="tab1link" href="#">Tab 1</a></li>
			<li><a id="tab2link" href="#">Tab 2</a></li>
			<li><a id="tab3link" href="#">Tab 3</a></li>
		</ul>
		<div id="tab1">
		<%@include file="tabpage1.jsp" %>  
		</div>
		<div id="tab2">
		<%@include file="tabpage2.jsp" %>  
		</div>
		<div id="tab3">
		<%@include file="tabpage3.jsp" %>  
		</div>
		<br>
	</div>

</body>
</html>