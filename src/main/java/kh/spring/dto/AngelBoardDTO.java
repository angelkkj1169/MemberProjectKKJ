package kh.spring.dto;

import java.sql.Timestamp;

public class AngelBoardDTO {

	private int seq;
	private String id;
	private String title;
	private String content;
	private int boardview;
	private Timestamp boardwritetime;
		
	public AngelBoardDTO(){}

	public AngelBoardDTO(int seq, String id, String title, String content, int boardview, Timestamp boardwritetime) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.boardview = boardview;
		this.boardwritetime = boardwritetime;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public int getBoardview() {
		return boardview;
	}

	public void setBoardview(int boardview) {
		this.boardview = boardview;
	}

	public Timestamp getBoardwritetime() {
		return boardwritetime;
	}

	public void setBoardwritetime(Timestamp boardwritetime) {
		this.boardwritetime = boardwritetime;
	};	
	
	
    
}
