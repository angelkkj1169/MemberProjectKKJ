package kh.spring.dto;

import org.springframework.web.multipart.MultipartFile;

public class MemberFileDTO {

	private String id;
	private String password;
	private String name;
	private MultipartFile image;
	
	public MemberFileDTO(){}
	
	public MemberFileDTO(String id, String password, String name) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
	}
	
	public MemberFileDTO(String id, String password, String name, MultipartFile image) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.image = image;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
	
	
}
