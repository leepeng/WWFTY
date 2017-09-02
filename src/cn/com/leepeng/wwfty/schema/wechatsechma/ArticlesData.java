package cn.com.leepeng.wwfty.schema.wechatsechma;

import java.util.List;

import cn.com.leepeng.wwfty.annotation.wechat.DataParam;
import cn.com.leepeng.wwfty.annotation.wechat.Data;

@Data(value = "ArticlesData")
public class ArticlesData implements Cloneable{
	@DataParam("articles")
	private List<ArticlesData> articles;
	@DataParam("thumb_media_id")
	private String thumbMediaID;
	@DataParam("author")
	private String author;
	@DataParam("title")
	private String title;
	@DataParam("content_source_url")
	private String contentSourceUrl;
	@DataParam("content")
	private String content;
	@DataParam("digest")
	private String digest;
	@DataParam("show_cover_pic")
	private String showCoverPic;

	public List<ArticlesData> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticlesData> articles) {
		this.articles = articles;
	}

	public String getThumbMediaID() {
		return thumbMediaID;
	}

	public void setThumbMediaID(String thumbMediaID) {
		this.thumbMediaID = thumbMediaID;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentSourceUrl() {
		return contentSourceUrl;
	}

	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl = contentSourceUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getShowCoverPic() {
		return showCoverPic;
	}

	public void setShowCoverPic(String showCoverPic) {
		this.showCoverPic = showCoverPic;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
