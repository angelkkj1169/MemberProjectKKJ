package kh.spring.dto;

public class PageDTO {

	private int startCount;
	private int endCount;
	private String keyWord;
	
	public PageDTO(){}
	
	public PageDTO(int startCount, int endCount) {
		super();
		this.startCount = startCount;
		this.endCount = endCount;
	}
	
	public PageDTO(int startCount, int endCount, String keyWord) {
		super();
		this.startCount = startCount;
		this.endCount = endCount;
		this.keyWord = keyWord;
	}

	public int getStartCount() {
		return startCount;
	}
	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}
	public int getEndCount() {
		return endCount;
	}
	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
}
