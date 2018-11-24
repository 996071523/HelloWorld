package bean;

import java.sql.Timestamp;

public class Article {
	private Long id;
	private String title;
	private String content;
	private String ketWords;
	private String description;
	private Long columnId;
	private String label;
	private String titleImgs;
	private String status;
	private String type;
	private Timestamp repleaseTime;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getKetWords() {
		return ketWords;
	}
	public void setKetWords(String ketWords) {
		this.ketWords = ketWords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTitleImgs() {
		return titleImgs;
	}
	public void setTitleImgs(String titleImgs) {
		this.titleImgs = titleImgs;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getRepleaseTime() {
		return repleaseTime;
	}
	public void setRepleaseTime(Timestamp repleaseTime) {
		this.repleaseTime = repleaseTime;
	}
	
	
}
