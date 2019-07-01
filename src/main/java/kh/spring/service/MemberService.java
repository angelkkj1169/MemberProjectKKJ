package kh.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.spring.dao.MemberDAO;
import kh.spring.dto.AngelBoardDTO;
import kh.spring.dto.MemberFileDTO;

//기능은 컴포넌트와 똑같지만 서비스라고 쉽게 구별시키기 위해 달아줌
@Service
public class MemberService {

	@Autowired
	MemberDAO dao;	//dao:데이터에 접근하는 모든 것을 통칭한다.
	                //데이터를 단순히 뽑아내는 역할은 컨트롤러가 하고, 뽑아온 데이터를 처리하는 것은 서비스가 한다고 생각하면 된다.
	
	public int insert(MemberFileDTO dto){
	return dao.insert(new MemberFileDTO(dto.getId(), dto.getPassword(), dto.getName())); }
		
	public int loginCheck(MemberFileDTO dto){return dao.loginCheck(dto);}

	public int IdCheck(String id){return dao.idCheck(id);}
	
	public List<AngelBoardDTO> BoardSelectAll(){return dao.BoardSelectAll();}
	
	//글 저장 관련 서비스(데이터의 흐름은 컨트롤러 - 서비스 dao로 이동한다.)
	public int BoardWriteInsert(AngelBoardDTO dto){return dao.BoardWriteInsert(dto);}
	
	//글을 클릭했을 때 일어나는 2개의 상황을 가정함
	@Transactional("transactionManager")
	public AngelBoardDTO BoardSeqSelectAndViewUpdate(int BoardNumber){
		
		       dao.BoardViewUpdate(BoardNumber);
		return dao.BoardSeqSelect(BoardNumber);}
	
	//글 번호를 키워드로 행 하나를 선택
	public AngelBoardDTO BoardSeqSelect(int BoardNumber){return dao.BoardSeqSelect(BoardNumber);}
	
	//글 수정 서비스
	public int BoardModifyUpdate(AngelBoardDTO dto){return dao.BoardModifyUpdate(dto);}
	
	//글 삭제 서비스
	public int BoardDelete(int BoardNumber){return dao.BoardDelete(BoardNumber);}

	public List<String>Page(int page, int totalcount){return dao.Page(page, totalcount);}
	
	//일반 전체글의 갯수 또는 검색 결과의 글갯수를 출력시키는 메소드
	public int BoardPostNumber(){return dao.BoardPostNumber();}
	public int SelectBoardPostNumber(String keyword){return dao.SelectBoardPostNumber(keyword);}
		
	//SelectPageList
	public List<AngelBoardDTO>SelectPageList(int page){return dao.SelectPageList(page);}
	
	//PageKeywordSelect
	public List<AngelBoardDTO>PageKeywordSelect(int page ,String keyword){return dao.PageKeywordSelect(page, keyword);}
		
	
	
	}

