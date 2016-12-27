<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page import="java.util.Map.Entry" %>
<%@page import="org.apache.solr.common.SolrDocumentList" %>
<%@page import="finalui.LuceneResultObject" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<style>
.smalllink {
	color: grey;
}

.rowTag {
	text-wrap: word-wrap;
}

.left {
	float: left;
}

.right {
	float: right;
}

#cluster {
	height: 800px;
	overflow: auto;
}

#hits {
	height: 800px;
	overflow: auto;
}

#pagerank {
	height: 800px;
	overflow: auto;
}

#queryexp {
	height: 800px;
	overflow: auto;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row rowTag">
			<div class="col-sm-3" id="hits">
				<h4>Hits</h4>
				<%-- <%
					ArrayList<ArrayList<String>> mydata1 = (ArrayList<ArrayList<String>>) request.getAttribute("results1");
					if (mydata1 != null) {
				%>
				
				<%
					for (int i = 0; i < mydata1.size(); i++) {
				%>
				<a href="<%=mydata1.get(i).get(1)%>"><%=mydata1.get(i).get(0)%></a><br />
				<a class="smalllink" href="<%=mydata1.get(i).get(1)%>"
					style="font-size: 8px;"><%=mydata1.get(i).get(1)%></a><br /> <br />

				<%
					}
				%>
				<%
					}
				%>  --%>
				<%
				//TreeMap<Integer,LuceneResultObject> lucmap = (TreeMap<Integer,LuceneResultObject>)request.getAttribute("hitresults");
				List< String[]> hitsmap = new ArrayList< String[]>();
				hitsmap = (ArrayList<String[]>)request.getAttribute("hitresults");
				int ik=0;
				if(hitsmap!=null){
					Iterator iter = hitsmap.iterator();
					while((iter.hasNext())&&(ik<100)){
						String[] details = (String[])iter.next();
						
						System.out.println("Doc Id : "+details[2]+" Title:"+details[0]);
						ik++;
						%>
						
						<a  href="<%=details[1]%>" style="font-size:12px;"><%=details[0] %></a>
				<br>
				<a class="smalllink" href="<%=details[1]%>" style="font-size:8px;"><%=details[1] %></a>
						<br/>
						<% 
					}
				
				}
				
				%>
			</div>
			<div class="col-sm-3" id="cluster">
			<h4>Page Ranking</h4>

				<%
					//ArrayList<ArrayList<String>> mydata2 = (ArrayList<ArrayList<String>>) request.getAttribute("results2");
					SolrDocumentList solrpr1 = (SolrDocumentList) request.getAttribute("prankresults");
					if (solrpr1 != null) {
					System.out.println("SOLR size : "+solrpr1.size());
				%>
				
				<%
					for (int i = 0; i < solrpr1.size(); i++) {
						System.out.println(solrpr1.get(i).get("url"));
						String a=solrpr1.get(i).get("url").toString();
						String b=solrpr1.get(i).get("title").toString();
				%>
				 
				<a  href="<%=a%>" style="font-size:12px;"><%=b %></a>
				<br>
				<a class="smalllink" href="<%=a%>" style="font-size:8px;"><%=a %></a>
				
				<br>
				<%
					}}
				%>






			</div>
			<div class="col-sm-3" id="pagerank">
			<h2>Google Search</h2>
				<%
			ArrayList<ArrayList<String>> google43 = (ArrayList<ArrayList<String>>) request.getAttribute("googleresults");
			if (google43 != null) {%>
				
				   <% for(int i=0;i<google43.size();i++)
				   {%>
				   <a href="<%=google43.get(i).get(1)%>"><%=google43.get(i).get(0)%></a><br/>
				   <a class="smalllink" href="<%=google43.get(i).get(1)%>" style="font-size:8px;"><%=google43.get(i).get(1)%></a><br/><br/>
				   
				  <%} %> 
					
					
			<% 	}
          %>

			</div>

			<div class="col-sm-3" id="queryexp">
			<h2>Bing Search</h2>
		<%
			ArrayList<ArrayList<String>> bing23 = (ArrayList<ArrayList<String>>) request.getAttribute("bingresults");
			if (bing23 != null) {%>
				
				  <% for(int i=0;i<8;i++)
				   {%>
				   <a href="<%=bing23.get(i).get(1)%>"><%=bing23.get(i).get(0)%></a><br/>
				   <a class="smalllink" href="<%=bing23.get(i).get(1)%>" style="font-size:8px;"><%=bing23.get(i).get(1)%><br/><br/>
				      
				  <%} %> 
					
					
			<% 	}
          %>
			
			</div>

		</div>
	</div>



</body>
</html>