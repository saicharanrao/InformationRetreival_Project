package finalui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.NoOpResponseParser;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SolrIndexer {

	public SolrDocumentList runSolrQueriesBM25(String q1) {
		String urlString = "http://localhost:8983/solr/bm25";

		HttpSolrServer s1 = new HttpSolrServer(urlString);
		s1.setParser(new XMLResponseParser());
		s1.setMaxRetries(1); // >1 not recommended
		s1.setConnectionTimeout(5000); // 5 seconds to establish tCP

		SolrQuery query = new SolrQuery();
		// query.setQuery("*:*");
		// query.addFilterQuery("");
		query.setFields("title", "id", "url", "content");
		// query.setFilterQueries("the");
		query.set("q", "{!q.op=OR df=content}" + q1);
		query.set("defType", "edismax");

		SolrDocumentList results = new SolrDocumentList();
		try {
			QueryResponse response = s1.query(query);
			results = response.getResults();
			System.out.println("The Size of results : "+results.size());
			File outputfile = new File("E:\\IR\\solr", "output.txt");
//			BufferedWriter bfr = new BufferedWriter(new FileWriter(outputfile, false));
//			for (int i = 0; i < results.size(); ++i) {
//				System.out.println(results.get(i).toString());
//				bfr.write(results.get(i).toString());
//			}
//			bfr.close();

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return results;
	}
	

	public SolrDocumentList runSolrQueriesTFIDF(String q1) {
		String urlString = "http://localhost:8983/solr/normal_core";

		HttpSolrServer s1 = new HttpSolrServer(urlString);
		s1.setParser(new XMLResponseParser());
		s1.setMaxRetries(1); // >1 not recommended
		s1.setConnectionTimeout(5000); // 5 seconds to establish tCP

		SolrQuery query = new SolrQuery();
		// query.setQuery("*:*");
		// query.addFilterQuery("");
		query.setFields("title", "id", "url", "content");
		// query.setFilterQueries("the");
		query.set("q", "{!q.op=OR df=content}" + q1);
		query.set("defType", "edismax");

		SolrDocumentList results = new SolrDocumentList();
		try {
			QueryResponse response = s1.query(query);
			results = response.getResults();
			System.out.println("The Size of results : "+results.size());
			File outputfile = new File("E:\\IR\\solr", "output.txt");
//			BufferedWriter bfr = new BufferedWriter(new FileWriter(outputfile, false));
//			for (int i = 0; i < results.size(); ++i) {
//				System.out.println(results.get(i).toString());
//				bfr.write(results.get(i).toString());
//			}
//			bfr.close();

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return results;

	}

	public SolrDocumentList runSolrQueriesWebG(String q1) throws InterruptedException {
		String urlString = "http://localhost:8983/solr/prank2";

		HttpSolrServer s1 = new HttpSolrServer(urlString);
		s1.setParser(new XMLResponseParser());
		s1.setMaxRetries(1); // >1 not recommended
		s1.setConnectionTimeout(5000); // 5 seconds to establish tCP

		SolrQuery query = new SolrQuery();
		// query.setQuery("*:*");
		// query.addFilterQuery("");
		query.setFields("title", "id", "url", "content");
		// query.setFilterQueries("the");
		query.set("q", "{!q.op=OR df=content}" + q1);
		query.set("defType", "edismax");

		SolrDocumentList results = new SolrDocumentList();
		try {
			QueryResponse response = s1.query(query);
			results = response.getResults();
			System.out.println("The Size of PageRank results : "+results.size());
			File outputfile = new File("E:\\IR\\solr", "output.txt");
//			BufferedWriter bfr = new BufferedWriter(new FileWriter(outputfile, false));
//			for (int i = 0; i < results.size(); ++i) {
////				System.out.println(results.get(i).toString());
////				bfr.write(results.get(i).toString());
//			}
//			bfr.close();

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return results;

	}

	public String runExternalQueryExpander(String data) throws IOException, InterruptedException {
		List<String> command = new ArrayList<String>();

		// BufferedWriter bfr = new BufferedWriter(new
		// FileWriter("E:\\IR\\solr\\1.json"));
		// bfr.write(data);
		// bfr.close();
		command.add("D:\\Python27\\python");
		command.add("E:\\IR\\solr\\assocqe.py");
		command.add("car");

		// System.out.println(data);
		SystemCommandExecutor commandExecutor = new SystemCommandExecutor(command);
		StringBuilder sbd = new StringBuilder();
		int exitcode = commandExecutor.executeCommand();
//		System.out.println("Command executed, exit code : " + exitcode);
		StringBuilder sbdo = new StringBuilder();
		sbdo = commandExecutor.getStandardOutputFromCommand();
		System.out.println(sbdo.toString());
		return sbdo.toString();
	}
	public static void main(String args[]) throws InterruptedException {
		SolrIndexer solrindex = new SolrIndexer();
//		solrindex.runSolrQueriesWebG(); // for BM25 results.
		System.out.println("---------------------------------------------------------------------------");
		// solrindex.runSolrQueriesTFIDF(); // for TFIDF results.

		// for webgraph pagerank results

		// for clustered results.

		// for topic based results.
	}
}
