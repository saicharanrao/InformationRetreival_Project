<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*"%>
    
    <%@page import="finalui.LuceneResultObject" %>
    <%@page import="org.apache.solr.common.SolrDocumentList" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>

.smalllink
{
   color:grey;
}

.divtag
{
 display: inline-block;
  float: left;
  width:50%;
  height:100%;
}
</style>
</head>
<body>
<div class="divtag">

		<h2>Clustering</h2>
		<%
					ArrayList<ArrayList<String>> mydata4 = (ArrayList<ArrayList<String>>) request.getAttribute("clustering");
					if (mydata4 != null) {
				%>
				<h2>Clustering</h2>
				<%
					for (int i = 0; i < mydata4.size(); i++)

						{ //System.out.println("data:"+mydata4.get(i).get(0));
							if (mydata4.get(i).get(0).isEmpty())
								continue;
				%>
				<a href="<%=mydata4.get(i).get(1)%>"><%=mydata4.get(i).get(0)%></a><br />
				<a class="smalllink" href="<%=mydata4.get(i).get(1)%>"
					style="font-size: 8px;"><%=mydata4.get(i).get(1)%></a>
				<p><%=mydata4.get(i).get(2)%></p>

				<%
					}
				%>


				<%
					}
				%>

</div>
<div class="divtag">

	
		<h2>Query Expansion</h2>
		<h3>Query expanded is : Car Rental cheapest</h3>
		<%
					//ArrayList<ArrayList<String>> mydata2 = (ArrayList<ArrayList<String>>) request.getAttribute("results2");
					SolrDocumentList solr56 = (SolrDocumentList) request.getAttribute("tfresults");
					if (solr56 != null) {
					System.out.println("Page rank : "+solr56.size());
				%>
				
				<%
					for (int i = 0; i < solr56.size(); i++) {
						System.out.println(solr56.get(i).get("url"));
						String a=solr56.get(i).get("url").toString();
						//String b=solr56.get(i).get("title").toString();
				%>
				 
				<a  href="<%=a%>" style="font-size:12px;"><%=a %></a>
				<br>
				<a class="smalllink" href="<%=a%>" style="font-size:8px;"><%=a %></a>
				
				<br>
				<%
					}}
				%>


</div>




</body>
</html>