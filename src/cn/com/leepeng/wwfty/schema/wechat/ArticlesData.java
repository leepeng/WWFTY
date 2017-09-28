package cn.com.leepeng.wwfty.schema.wechat;

import cn.com.leepeng.wwfty.annotation.Data;
import cn.com.leepeng.wwfty.annotation.DataParam;

@Data(value = "ArticlesData")
public class ArticlesData implements Cloneable{
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
	@DataParam("url")
	private String url;
	@DataParam("show_cover_pic")
	private String showCoverPic;

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
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ArticlesData [thumbMediaID=" + thumbMediaID + ", author=" + author + ", title=" + title
				+ ", contentSourceUrl=" + contentSourceUrl + ", content=" + content + ", digest=" + digest
				+ ", showCoverPic=" + showCoverPic + "]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
