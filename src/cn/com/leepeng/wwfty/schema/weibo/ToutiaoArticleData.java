package cn.com.leepeng.wwfty.schema.weibo;

import cn.com.leepeng.wwfty.annotation.Data;
import cn.com.leepeng.wwfty.annotation.DataParam;
import net.sf.json.JSONObject;

/**
 * 发布头条文章数据
 * 
 * @author LEEPENG
 */
@Data("ToutiaoArticleData")
public class ToutiaoArticleData extends WeiboBaseData implements Cloneable {
	/**
	 * 文章标题，限定32个中英文字符以内
	 */
	@DataParam("title")
	private String title;
	/**
	 * 正文内容，限制90000个中英文字符内，需要urlencode
	 */
	@DataParam("content")
	private String content;
	/**
	 * 文章封面图片地址url
	 */
	@DataParam("cover")
	private String cover;
	/**
	 * 文章导语
	 */
	@DataParam("summary")
	private String summary;
	/**
	 * 与其绑定短微博内容，限制1900个中英文字符内
	 */
	@DataParam("text")
	
	private String text;

	public ToutiaoArticleData(String title, String content, String cover, String summary, String text) {
		super();
		this.title = title;
		this.content = content;
		this.cover = cover;
		this.summary = summary;
		this.text = text;
	}

	public ToutiaoArticleData(String title, String content, String cover, String text) {
		super();
		this.title = title;
		this.content = content;
		this.cover = cover;
		this.text = text;
	}
	
	public ToutiaoArticleData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return JSONObject.fromBean(this).toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
