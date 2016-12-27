package finalui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneIndexer {
	@SuppressWarnings({ "deprecation" })
	public static void main(String args[]) throws ParseException {

		LuceneIndexer lucindex = new LuceneIndexer();
		TreeMap<Integer, LuceneResultObject> lucmap = new TreeMap<Integer, LuceneResultObject>();
		lucmap = lucindex.runHitsScoring("car");
		Iterator it = lucmap.entrySet().iterator();
		while (it.hasNext()) {
			Entry rn = (Entry) it.next();
			Integer m = (Integer) rn.getKey();
			System.out.println("docid : " + m);
		}

	}

	public TreeMap<Integer, LuceneResultObject> runHitsScoring(String newquery) throws ParseException {
		File file = new File("E:\\IR\\solr\\index3");

		TreeMap<Integer, LuceneResultObject> lucenemap = new TreeMap<Integer, LuceneResultObject>();
		try {
			Directory dirIndex = FSDirectory.open(file);
			IndexReader reader = DirectoryReader.open(dirIndex);

			System.out.println("doccount for the : " + reader.getDocCount("the"));
			System.out.println("Total Docs : " + reader.maxDoc());
			IndexSearcher searcher = new IndexSearcher(reader);

			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_4_10_4);
			String queries = null;
			String field = "content";
			String queryString = null;
			BufferedReader bfr = null;
			boolean raw = false;
			int repeat = 0;
			int hitsPerPage = 10;
			bfr = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

			QueryParser parser = new QueryParser(Version.LUCENE_4_10_0, field, analyzer);

			// while (true) {
			// if (queries == null && queryString == null) {
			// System.out.println("Enter query : ");
			// }
			// String line = queryString != null ? queryString : bfr.readLine();
			// if (line == null || line.length() == -1) {
			// break;
			// }
			//
			// line = line.trim();
			// if (line.length() == 0) {
			// System.out.println("line length is 0");
			// break;
			// }
			//
			// if (repeat > 0) {
			// Date start = new Date();
			// for (int i = 0; i < repeat; i++) {
			//
			// }
			// Date end = new Date();
			// System.out.println("Time : " + (end.getTime() - start.getTime())
			// + " ms");
			// }
			// // doPagingSearch(bfr, searcher, query, hitsPerPage, raw,
			// // queries == null && queryString == null);
			// // getAllDocuments(reader);
			//
			// }

			Query query = parser.parse(newquery);
			searcher.search(query, 100);
			System.out.println("Searching for: " + query.toString(field));

			lucenemap = getHitsScores(bfr, searcher, query, hitsPerPage);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lucenemap;

	}

	public static TreeMap<Integer, LuceneResultObject> getHitsScores(BufferedReader bfr, IndexSearcher indexsearcher,
			Query query, int hitsPerPage) throws IOException {

		TreeMap<Integer, LuceneResultObject> luceneobjectmap = new TreeMap<Integer, LuceneResultObject>();
		TopDocs results = indexsearcher.search(query, 1000);
		ScoreDoc[] pagehits = results.scoreDocs;
		System.out.println("pagehits size : " + pagehits.length);
		int i = 0;
		while (i < pagehits.length) {

			if (indexsearcher.doc(pagehits[i].doc) != null) {
				Document doc = indexsearcher.doc(pagehits[i].doc);
				System.out.println(
						"url:" + doc.get("url") + "pageid:" + pagehits[i].doc + ", hitscore : " + pagehits[i].score);
				LuceneResultObject luceneobj = new LuceneResultObject();
				luceneobj.setDocId(pagehits[i].doc);
				Float hitscore = pagehits[i].score;
				luceneobj.setHitScore((Float) pagehits[i].score);
				luceneobj.setTitle(doc.get("title"));
				luceneobj.setUrl(doc.get("url"));
				luceneobjectmap.put(luceneobj.getDocID(), luceneobj);

			}
			i++;

		}

		return luceneobjectmap;
	}

	public static void doPagingSearch(BufferedReader bfr, IndexSearcher indexsearcher, Query query, int hitsPerPage,
			boolean raw, boolean interactive) throws IOException {

		// Collect enough docs to show 5 pages
		TopDocs results = indexsearcher.search(query, 5 * hitsPerPage); // throws
																		// the
																		// IO
																		// Exception

		ScoreDoc[] hits = results.scoreDocs; // Matching Documents.

		int numTotalHits = results.totalHits;
		System.out.println("Total matching documents found : " + numTotalHits);

		int start = 0;
		int end = Math.min(numTotalHits, hitsPerPage);

		while (true) {
			if (end > hits.length) {
				System.out.println("Only results 1 - " + hits.length + " of " + numTotalHits
						+ " total matching documents collected.");
				System.out.println("Collect more - (y/n) ?");
				String line = bfr.readLine();
				if (line.length() == 0 || line.charAt(0) == 'n') {
					break;
				}

				hits = indexsearcher.search(query, numTotalHits).scoreDocs;
			}

			end = Math.min(hits.length, start + hitsPerPage);

			for (int i = start; i < end; i++) {
				if (raw) {// output raw format
					System.out.println("doc id = " + hits[i].doc + " ,score = " + hits[i].score);
					continue;
				}

				Document doc = indexsearcher.doc(hits[i].doc);
				String path = doc.get("url");
				if (path != null) {
					System.out.println((i + 1) + ". " + path);
					String title = doc.get("title");
					if (title != null) {
						System.out.println("Title : " + doc.get("title"));
					}
					String content = doc.get("content");
					if (content != null) {
						System.out.println("Content : " + content);
					}

				} else {
					// If path is null
					System.out.println((i + 1) + ". No path for this document");
				}

			}

			if (!interactive || end == 0) {
				break;
			}

			if (numTotalHits >= end) {
				boolean quit = false;
				while (true) {
					System.out.println("Press ");
					if (start - hitsPerPage >= 0) {
						System.out.println("(p)revious page, ");
					}
					if (start + hitsPerPage < numTotalHits) {
						System.out.println("(n)ext page, ");
					}
					System.out.println("(q)uit or enter page number to change to.");

					String line = bfr.readLine();
					if (line.length() == 0 || line.charAt(0) == 'q') {
						quit = true;
						break;
					}

					if (line.charAt(0) == 'p') {
						start = Math.max(0, start = hitsPerPage);
						break;
					} else if (line.charAt(0) == 'n') {
						if (start + hitsPerPage < numTotalHits) {
							start += hitsPerPage;
						}
						break;
					} else {
						int page = Integer.parseInt(line);
						if ((page - 1) * hitsPerPage < numTotalHits) {
							start = (page - 1) * hitsPerPage;
							break;
						} else {
							System.out.println("No such page");
						}
					}

				}
				if (quit)
					break;
				end = Math.min(numTotalHits, start + hitsPerPage);
			}
		}

	}
}
