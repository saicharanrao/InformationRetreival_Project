# InformationRetreival_Project
Search Engine for Automobiles


Developed a Search Engine for Automobiles. 

This search engine enables users to search regarding price, features and variants of different automobiles in the current market.

-> Used techniques like Clustering, Query Expansion to make the search engine efficient.

1)	User Interface Design:

 

Technologies used in the Design: 
a)	JSP, HTML, CSS , Bootstrap , JQuery, Servlet, Apache Tomcat

2)	Working:  The Home page ( mainpage.jsp) consists of a text box for entering the query , submit button , two tabs of which one is for displaying our Search results and the other is for displaying Google and Bing results.
Steps:
a)	User enters query and clicks submit (Get request).

b)	Query passed to servlet. (getAttribute method used for retrieval of query).

c)	The servlet passes this query for processes like Clustering, Query Expansion, Google and Bing API’s, Page Ranking, Hits calculation.

d)	Every process (excluding Query Expansion) , returns URL’s and their Headings as the final output for displaying on the UI.

e)	Query expansion returns an expanded query. Subsequently relevance model is applied and then the result is returned. The expanded query is once again sent for query.

f)	All these results are then set in the response parameter and returned to the home page for displaying. These are displayed in Tab1. 

g)	Google and Bing results alone are printed in a separate tab(Tab2) in the home page.



