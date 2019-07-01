package kh.spring.pratice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.spring.dto.AngelBoardDTO;
import kh.spring.service.MemberService;


@Controller
public class BoardController {
	
	@Autowired
	HttpSession session;
	 
	@Autowired
	MemberService mservice;
	
	//메인페이지에서 자유게시판으로 이동
	@RequestMapping("boardmove")
	public String boardmove(HttpServletRequest request){
		return "redirect:pageController?page=1";}

	
		//자유게시판에서 글쓰기폼으로 이동
		@RequestMapping("BoardWriteMove")
		public String BoardWriteMove(){return "BoardWriteForm";}
	
		
		//글을 쓴 결과를 데이터베이스에 전송시키고 이후 글쓰기폼으로 리다이렉트시킨다.
		//(스프링의 바인딩을 이용해보자)
		// session.setAttribute("loginid", dto.getId());
		@RequestMapping("BoardWriteProc")
		public String BoardWriteProc(AngelBoardDTO dto){
				
			System.out.println(dto.getTitle() +" : "+dto.getContent());
			dto.setId((String)session.getAttribute("loginid"));
			
			int result = mservice.BoardWriteInsert(dto);
			
			if(result > 0){System.out.println("정상적으로 db에 게시판글이 저장되었습니다.");}
			
			//redirect:boardmove로 이동하는 순간
			//애플리케이션을 다시 초기화한다고 생각하면 됨
			//이 로직을 생각해보면 어딘가를 이동할 때마다 컨트롤러에 추가해주는 것도 나쁘지 않군 
			return "redirect:boardmove";}
		
			
		//글을 클릭했을 때 작동하는 메소드
		@RequestMapping("boardcontentProc")
		public String boardcontentProc(int BoardNumber , HttpServletRequest request){	
			AngelBoardDTO result = mservice.BoardSeqSelectAndViewUpdate(BoardNumber);		
			request.setAttribute("TextInformation", result);			
			return "BoardContent";}
			
		
		//작성한 글을 보여주는 화면에서 글수정으로 이동할 때 거치는 메소드
		@RequestMapping("modifymove")
		public String boardmodifymove(int BoardNumber , HttpServletRequest request){
				
			//이동하기 전에 미리 검색을 해둔다
			AngelBoardDTO result = mservice.BoardSeqSelect(BoardNumber);
			request.setAttribute("TextInformation", result);
			return "BoardModifyForm";}
				
		
		//글 수정을 하고 전송버튼을 누르면 그 결과가 db에 저장되게 만드는 메소드
		@RequestMapping("BoardModifyProc")
		public String BoardModifyProc(AngelBoardDTO dto , HttpServletRequest request){
				
			System.out.println(dto.getSeq() +" : " + dto.getTitle());
			
			int result =mservice.BoardModifyUpdate(dto);
			if(result > 0){System.out.println("정상적으로 글이 수정되었습니다.");}				
				
			//리다이렉트를 어떻게 써야하는지의 예시임
			return "redirect:boardcontentProc?BoardNumber="+dto.getSeq()+"";}	
		
				
		//게시글 삭제
		@RequestMapping("BoardDeleteProc")
		public String BoardDeleteProc(int BoardNumber){			
		int result = mservice.BoardDelete(BoardNumber);	
		if(result > 0){System.out.println("정상적으로 삭제됨");}		
		return "redirect:boardmove";}
		
					
	    //페이징 알고리즘 실험메소드
		@RequestMapping("pageController")
		public String pageExample(int page, HttpServletRequest request){
						
			int totalcount = mservice.BoardPostNumber();
			
			List<String>pageList =  mservice.Page(page , totalcount);//이 서비스는 쿼리가 2개 필요하다	
			request.setAttribute("boardlist", mservice.SelectPageList(page));
			request.setAttribute("pageList", pageList);//1.보드게시판 아래에 숫자를 출력
			request.setAttribute("page", page);//현재 페이지임
			return "board";}
				
		

	    //검색기능 알고리즘
		//반복상황을 board페이지에서 구별을 확실히 해야 함
		@RequestMapping("PageKeywordSelect")
		public String pageExample(int page, String keyword, HttpServletRequest request){
			
			System.out.println("페이지: " + page);
			System.out.println("키워드: " + keyword);
			int totalcount = mservice.SelectBoardPostNumber(keyword);
			System.out.println(keyword +"로 검색된 총 글의 갯수 "+totalcount);
			
			List<String>pageList =  mservice.Page(page , totalcount);
			request.setAttribute("Selectboardlist",  mservice.PageKeywordSelect(page , keyword));
			request.setAttribute("SelectpageList", pageList);//1.보드게시판 아래에 숫자를 출력
			request.setAttribute("keyword", keyword);//1.보드게시판 아래에 숫자를 출력
			request.setAttribute("page", page);//현재 페이지임
					 
			//List<String>pageList =  mservice.;
		 return "board";
		}
		
		
		
		
		
		
		
			
}
