package finalui;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.solr.common.SolrDocumentList;
import org.json.simple.JSONObject;

public class QueryExp {

	static String[] results;

	public QueryExp(SolrDocumentList str) {

		if (str == null) {
			System.out.println("SOLR document list is null");
		} else {
			System.out.println("SOLR document list size : " + str.size());
		}
		for (int i = 0; i < 10; ++i) {
			this.results[i] = (String) str.get(i).toString();
			System.out.println(results[i]);
		}
	}

	public void getQueryExpanse(String query) {

		JSONObject obj = new JSONObject();
		obj.put("name", "foo");

		StringWriter out = new StringWriter();
		try {
			obj.writeJSONString(out);
			System.out.println(out.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem with JSON");
			// e.printStackTrace();
		}

	}

}
