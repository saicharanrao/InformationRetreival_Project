package finalui;

public class LuceneResultObject {

	String title;
	Float hitscore;
	Integer docid;
	String url;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setHitScore(Float hitscore) {
		this.hitscore = hitscore;
	}

	public void setDocId(Integer docid) {
		this.docid = docid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUrl() {
		return this.url;
	}

	public Float getHitsScore() {
		return this.hitscore;
	}

	public Integer getDocID() {
		return this.docid;
	}

}

