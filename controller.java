import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.solr.common.SolrDocumentList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import finalui.Clustering;
import finalui.LuceneIndexer;
import finalui.LuceneResultObject;
import finalui.SolrIndexer;
import finalui.bingsearch;
import finalui.googlesearch;

/**
 * Servlet implementation class controller
 */
@WebServlet("/controller")
public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	public String somemethod(String uri) {
		Document doc;
		String title = new String();
		try {
			doc = Jsoup.connect(uri).get();
			title = doc.title();
			// System.out.println("Jsoup Can read HTML page from URL, title : "
			// + title);
			if (!title.isEmpty())
				return title;
			else
				return uri;

		} catch (Exception e) {
			e.printStackTrace();
			return uri;
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		// session.setAttribute("searchquery", query);
		// session.setAttribute("searchquery2", "im good");
		// getServletContext().getRequestDispatcher("/mainpage.jsp").forward(request,response);

		ArrayList<ArrayList<String>> bingresults = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> googleresults = new ArrayList<ArrayList<String>>();
		ArrayList<String> indexresults1 = new ArrayList<String>();
		ArrayList<String> indexresults2 = new ArrayList<String>();
		ArrayList<String> indexresults3 = new ArrayList<String>();
		ArrayList<ArrayList<String>> results1 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> results2 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> results3 = new ArrayList<ArrayList<String>>();

		String query = request.getParameter("searchquery");
		System.out.println(query); // what the query is

		String bar = request.getParameter("qe");

		request.setAttribute("g", query);
		request.setAttribute("g1", bar);
		SolrIndexer solri = new SolrIndexer();
		SolrDocumentList resultdocs = solri.runSolrQueriesBM25(query);
		SolrDocumentList resultdocstf = solri.runSolrQueriesTFIDF(query);
		SolrDocumentList resultdocspr = new SolrDocumentList();
		SolrDocumentList qeresults = new SolrDocumentList();
		String qexpanded;
//		try {
////			 qexpanded = solri.runExternalQueryExpander("data");
////			 qeresults = solri.runSolrQueriesTFIDF(qexpanded);
//			 request.setAttribute("qeresults", qeresults);
//		} catch (InterruptedException e3) {
//			// TODO Auto-generated catch block
//			e3.printStackTrace();
//		}
		try {
			resultdocspr = solri.runSolrQueriesWebG(query);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		request.setAttribute("prankresults", resultdocspr);
		request.setAttribute("tfresults", resultdocstf);
		request.setAttribute("bm25results", resultdocs);

		if (resultdocs == null) {
			System.out.println("SOLR results are NULL");
		}
		String[] results = null;
		// JSONObject jsonj = new JSONObject();
		// for (int i = 0; i < 10; i++) {
		//// System.out.println(resultdocs.get(i).getFieldValue("title"));
		//// jsonj.put("title", resultdocs.get(i).getFieldValue("title"));
		// }
		// StringWriter out = new StringWriter();
		// jsonj.writeJSONString(out);
		// System.out.println("JSON formed as :\n" + out.toString());

		// LuceneIndexer ob1 = new LuceneIndexer();
		// indexresults1 = ob1.searchIndex(query, "./luceneIndexDir/Index_New");
		// indexresults2 = ob1.searchIndexWithPageRank(query,
		// "./luceneIndexDir/Index_PR");

		// HITS score
		LuceneIndexer lucindex = new LuceneIndexer();
		TreeMap<Integer, LuceneResultObject> lucenemap = new TreeMap<Integer, LuceneResultObject>();
		List<String[]> lucfinalmap = new ArrayList<String[]>();
		try {
			lucenemap = lucindex.runHitsScoring(query);
			Iterator iter = lucenemap.entrySet().iterator();
			while (iter.hasNext()) {
				Entry en = (Entry) iter.next();
				String[] details = new String[4];
				LuceneResultObject lobj = (LuceneResultObject) en.getValue();
				details[0] = lobj.getTitle();
				details[1] = lobj.getUrl();
				details[2] = lobj.getDocID().toString();
				details[3] = lobj.getHitsScore().toString();
				Integer docid = lobj.getDocID();
				lucfinalmap.add(details);

			}
			System.out.println("Lucene Query result size : " + lucenemap.size());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			System.out.println("Lucene Map retrieval exception !!!");
			e1.printStackTrace();
		}

		// for clustering
		Clustering ob3 = new Clustering();
		ArrayList<ArrayList<String>> clusterResults = ob3.indexResults(lucfinalmap);
		ArrayList<ArrayList<String>> clustering = new ArrayList<ArrayList<String>>();

		Iterator it = clusterResults.iterator();

		for (ArrayList<String> cluster : clusterResults) {
			int flag = 0;
			String c_id = "";
			for (String cluster_url : cluster) {
				String name = "";
				if (flag != 0)
					name = somemethod(cluster_url);
				else {
					c_id = cluster_url;
					flag = 1;
				}
				ArrayList<String> temp = new ArrayList();
				temp.add(name);
				temp.add(cluster_url);
				temp.add(c_id);
				clustering.add(temp);
			}
		}
		
		 while(it.hasNext()) { Map.Entry<Integer, ArrayList<String>>
		  mp=(Map.Entry<Integer, ArrayList<String>>)it.next();
		 System.out.println("Cluster ID: " +mp.getKey()); for (String
		  url:mp.getValue()) { ArrayList<String> temp=new ArrayList();
		  System.out.println(" "+url); String name=somemethod(url);
		  temp.add(name); temp.add(url); temp.add(mp.getKey().toString());
		  clustering.add(temp); }
		 
		}

		// for query expansion

		// Queryexpand ob2 = new Queryexpand();
		// String expandquery = ob2.caller(query);
		// // System.out.println("the expanded query in controller
		// // is:"+expandquery);
		// indexresults3 = ob1.searchIndex(expandquery,
		// "./luceneIndexDir/Index_New");
		//
		// for (int i = 0; i < indexresults1.size(); i++) {
		// ArrayList<String> temp = new ArrayList<String>();
		//
		// String k = somemethod(indexresults1.get(i));
		// temp.add(k);
		// temp.add(indexresults1.get(i));
		// results1.add(temp);
		//
		// }
		//
		// for (int i = 0; i < indexresults2.size(); i++) {
		// ArrayList<String> temp = new ArrayList<String>();
		//
		// String k = somemethod(indexresults2.get(i));
		// temp.add(k);
		// temp.add(indexresults2.get(i));
		// results2.add(temp);
		//
		// }
		//
		// for (int i = 0; i < indexresults3.size(); i++) {
		// ArrayList<String> temp = new ArrayList<String>();
		//
		// String k = somemethod(indexresults3.get(i));
		// temp.add(k);
		// temp.add(indexresults3.get(i));
		// results3.add(temp);
		//
		// }

		// calling bing api and getting results
		bingsearch b = new bingsearch();
		try {
			bingresults = b.bingsearcher(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("couldnot connect to bing");
			e.printStackTrace();
		}

		// calling google to get results
		googlesearch s = new googlesearch();
		googleresults = s.mysearch(query);

		System.out.println("google results:" + googleresults);
		System.out.println();
		System.out.println("bing results:" + bingresults);

		request.setAttribute("googleresults", googleresults);
		request.setAttribute("bingresults", bingresults);
		// request.setAttribute("results1", results1);
		// request.setAttribute("results2", results2);
		// request.setAttribute("queryexpansion", results3);
		
		request.setAttribute("hitresults", lucfinalmap);
		// System.out.println("print once again"+expandquery);
		// request.setAttribute("expand", expandquery);
		 request.setAttribute("clustering", clustering);
		getServletContext().getRequestDispatcher("/mainpage.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
