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

		
				<h4>TF results</h4>

				<%
					//ArrayList<ArrayList<String>> mydata2 = (ArrayList<ArrayList<String>>) request.getAttribute("results2");
					SolrDocumentList solr3 = (SolrDocumentList) request.getAttribute("tfresults");
					if (solr3 != null) {
					System.out.println("Page rank : "+solr3.size());
				%>
				
				<%
					for (int i = 0; i < solr3.size(); i++) {
						System.out.println(solr3.get(i).get("url"));
						String a=solr3.get(i).get("url").toString();
						//String b=solr3.get(i).get("title").toString();
				%>
				 
				<a  href="<%=a%>" style="font-size:12px;"><%=a %></a>
				<br>
				<a class="smalllink" href="<%=a%>" style="font-size:8px;"><%=a %></a>
				
				<br>
				<%
					}}
				%>






				   

</div>
<div class="divtag">

	
		
				<h4>BM25 results</h4>

				<%
					//ArrayList<ArrayList<String>> mydata2 = (ArrayList<ArrayList<String>>) request.getAttribute("results2");
					SolrDocumentList solr4 = (SolrDocumentList) request.getAttribute("bm25results");
					if (solr3 != null) {
					System.out.println("Page rank : "+solr4.size());
				%>
				
				<%
					for (int i = 0; i < solr4.size(); i++) {
						System.out.println(solr4.get(i).get("url"));
						String a=solr4.get(i).get("url").toString();
						//String b=solr4.get(i).get("title").toString();
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