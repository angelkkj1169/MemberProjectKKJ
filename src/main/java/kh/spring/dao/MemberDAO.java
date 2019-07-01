package kh.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kh.spring.dto.AngelBoardDTO;
import kh.spring.dto.MemberFileDTO;
import kh.spring.dto.PageDTO;

@Component
public class MemberDAO {

@Autowired	
public JdbcTemplate template;	

@Autowired
private SqlSessionTemplate sst;
	
    //template에서의 insert는 매우 간단하다.

/*
	public int insert(MemberFileDTO dto){
		
	String sql = "insert into angelmember values(?,?,?)";	
		
	int result = template.update(sql, dto.getId(),dto.getPassword(),dto.getName()); //?의 갯수에 맞춰서 얻어온 값을 적어주면 됨

	return result;} */

	
	
	public int insert(MemberFileDTO dto){
		
	return sst.insert("MemberDAO.insert",dto);} //여기에서 dto는 값을 집어넣기 위한 파라미터상자라고 생각하면됨
	
	
	
	
/*	
	public int loginCheck(MemberFileDTO dto){
		
		String sql = "select * from angelmember";//다수의 값을 뽑아서 비교하는 개념
	
	List<MemberFileDTO>result = template.query(sql,new RowMapper<MemberFileDTO>(){ 
			
			@Override
			public MemberFileDTO mapRow(ResultSet rs, int rn) throws SQLException {

				MemberFileDTO dto = new MemberFileDTO(rs.getString("id"),rs.getString("password"),rs.getString("name"));
				
				return dto;		
			}				
		});
	
	//변수 카운트를 이용한다.
	//조건에 따라 카운트를 증가시키고 이 카운트가 0,1,2,에 따라 리턴값을 정해주면 된다.	
	int count = 0;
	for(MemberFileDTO idpw : result){

		if(idpw.getId().equals(dto.getId())){

			if(idpw.getPassword().equals(dto.getPassword())){count=0; break;}

			count=1; break;

		}else{count=2;}	
	}

	if(count==0) {return 0;}
	else if(count==1){return 1;}
	else if(count==2){return 2;}
	return -1;}		
	
	*/
		
	public int loginCheck(MemberFileDTO dto){
		
		List<MemberFileDTO>result = sst.selectList("MemberDAO.selectAll"); //데이터베이스에 저장되어 있는 id와 password를 검색
			
		int count = 0;
		for(MemberFileDTO idpw : result){

			if(idpw.getId().equals(dto.getId())){

				if(idpw.getPassword().equals(dto.getPassword())){count=0; break;}

				count=1; break;

			}else{count=2;}	
		}
		
		if(count==0) {return 0;}
		else if(count==1){return 1;}
		else if(count==2){return 2;}
		return -1;		
	}
	
	
	/*
	public int idCheck(String id){//값은 무조건 1아니면 0	
	int count = 0;	
	
	String sql = "select id from angelmember";	
		
	   //먼저 데이터베이스에 있는 모든 id를 출력시킴
		List<String> result = template.query(sql ,new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
			
			return rs.getString("id");
							
			} 			
		});
	
		for(String memberid : result){

			if(memberid.equals(id)){//만일 데이터베이스 내에 id가 존재하는 경우

				return 0;
			}
		}
		return -1;
	}
	*/
	

	public int idCheck(String id){
		
		//mybatis에서 나오는 resultset의 리턴형은 우리가 마음대로 조정이 가능하다.
		List<String>result = sst.selectList("MemberDAO.selectId");
		
		for(String memberid : result){

			if(memberid.equals(id)){//만일 데이터베이스 내에 id가 존재하는 경우

				return 0;
			}
		}
		return -1;
	}
		
/*
 만일 어떤 특정 숫자 하나만 뽑아내고 싶을 경우
public int SelectCount() {
	
	String sql = "select count(*) as cnt from message";
	return template.queryForObject(sql, Integer.class);
} */

	
    //게시판의 모든 내용을 출력시킴
	public List<AngelBoardDTO> BoardSelectAll(){		
	return sst.selectList("angelboardDAO.BoardSelectAll");}
	
	
	//데이터베이스에 누군가가 쓴 글 내용을 저장시킨다.
	public int BoardWriteInsert(AngelBoardDTO dto){		
	return sst.insert("angelboardDAO.BoardWriteInsert",dto);}
	
	
	//글 seq번호를 where값으로 해당 글의 정보를 출력시키는 메소드(글을 클릭했을 때 사용함)
	public AngelBoardDTO BoardSeqSelect(int number){		
	return sst.selectOne("angelboardDAO.BoardSelectSeq", number);}
		
	
	//글 seq번호를 where값으로 해당 글의 조회수를 증가시키는 메소드
	public int BoardViewUpdate(int number){
	return sst.update("angelboardDAO.BoardViewUpdate", number);}
		
	
	//글 수정 관련 메소드
	public int BoardModifyUpdate(AngelBoardDTO dto){
	return sst.update("angelboardDAO.BoardModifyUpdate",dto);}
	

	//글 삭제 관련 메소드
	public int BoardDelete(int BoardNumber){
	return sst.update("angelboardDAO.boardDelete",BoardNumber);}
		
	public int BoardPostNumber(){
	return sst.selectOne("angelboardDAO.BoardPostNumber");}
	
	public int SelectBoardPostNumber(String keyword){
	return sst.selectOne("angelboardDAO.SelectBoardPostNumber",keyword);}
	
	//페이징 알고리즘 관련 메소드
	//여기서 받을 매개변수는 클라이언트에서 입력받은 페이지의 값이다.
	public List<String> Page(int page, int totalCount){    
		
    //데이터베이스에서 검색된 게시물의 갯수(이건 데이터베이스의 select문에 따라 변해야 함)	
	System.out.println("현재 게시글의 갯수: "+totalCount);	
	
	int countList = 10;//한 페이지에 출력될 게시물의 수(이는 웹사이트에 따라 다를 수 있지만 기본적으로는 10개임)
	
	int totalPage = totalCount / countList;//총 게시물의 수 / 한 페이지당 들어갈 게시물의 수로 총 페이지의 수를 구한다		
	
	if (totalCount % 10 > 0){totalPage++;}
    
	//만일 현재 페이지 번호가 총 페이지 번호보다 크다면?
	//->현재 페이지를 강제로 총 페이지 번호로 치환시킬 수 있음
	
	//if(totalPage < page){page = totalPage;}
	
	//int page = 14;//이건 클라이언트에서 사용자가 페이지번호를 클릭했을 때 받는 값이다.
	                //(이 값을 기점으로 아래의 페이지가 변할 것이니 주의하도록)
	
	int countPage = 10; //한 페이지당 보여줄 수 있는 최대 숫자
	
	//보여줄 수 있는 페이지의 시작과 끝값
	int startPage = ((page-1)/10) * 10 + 1;
	int endPage = startPage + countPage -1;
	
/*	
	//각 페이지당 데이터베이스의 글을 보여줄 수 있음
	int startCount = (page - 1) * countPage + 1;
	int endCount = page * countPage;
	*/
	
	//위와 같은 로직으로 짤 경우 맨 마지막 페이지를 보정해줘야 함
	if(endPage > totalPage){endPage = totalPage;}
	
	
	List<String> pageList = new ArrayList<>();
	
	//만일 받아온 페이지의 값이 1보다 클 경우에는 이전을 추가로 달아준다.
	if(page > 1){pageList.add("<이전");}
	
	
	System.out.println(startPage + " : " + endPage);
	//입력받은 페이지번호를 화면에 출력시킨다.
	for(int i = startPage; i<=endPage; i++ ){
	
		pageList.add(" "+ i +" ");}
	
	//만일 현재 페이지보다 전체 페이지의 값이 더 큰 경우 다음을 추가로 달아준다.
	if(page < totalPage){pageList.add("다음>");}
	
	//만일 endPage보다 총 페이지가 더 클 경우 끝을 추가로 달아준다.
	//if(endPage < totalPage){pageList.add("<끝>");}
	
	for(String a : pageList){System.out.print(a);}
	
	return pageList;
	
	}	
		
	//각 페이지에 해당하는 게시글 10개를 출력시키는 메소드(검색없이)
	public List<AngelBoardDTO>SelectPageList(int page){ 
		
		int countPage = 10;
		int startCount = (page - 1) * countPage + 1;
		int endCount = page * countPage;
	
		List<AngelBoardDTO>BoardContentList = 
			sst.selectList("angelboardDAO.PageBoardselect", new PageDTO(startCount,endCount));	
		return BoardContentList;}
	
	
	    //각 페이지에 해당하는 게시글 10개를 출력시키는 메소드(검색 키워드로 이동했을 경우)
		public List<AngelBoardDTO>PageKeywordSelect(int page ,String keyword){ 
			
			int countPage = 10;
			int startCount = (page - 1) * countPage + 1;
			int endCount = page * countPage;
		
			List<AngelBoardDTO>BoardContentList = 
				sst.selectList("angelboardDAO.PageKeywordSelect", new PageDTO(startCount,endCount,keyword));
			
			return BoardContentList;}
	
	
	
	
}
