package kh.spring.pratice;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kh.spring.dao.MemberDAO;
import kh.spring.dto.AngelBoardDTO;
import kh.spring.dto.MemberFileDTO;
import kh.spring.service.MemberService;



@Controller
public class HomeController {
     	
	@Autowired
	HttpSession session;
	 
	@Autowired
	MemberService mservice;
	 
	//url창에 localhost를 입력하면 메인화면으로 이동
	//이 시점에서 해야할 일은? angelboard 테이블을 select시키는 것
	@RequestMapping("/")
	public String index(){
					
		return "home";	
		
	}
	
	
	@RequestMapping("joinForm")
	public String joinFormMove(){
						
		return "JoinForm";	
	}
	
	
	@RequestMapping("mypage")
	public Object MypageMove(){
						
		return "myPage";	
		
	}
	
	//int result = dao.insert(dto);
	
	//if(result > 0) {
		
	//	System.out.println("정상적으로 삽입됨");
	//	return "redirect:/";//샌드리다이렉트를 할 때 사용함
	//}
	
//	return "error";
	//return "home";//단순 리턴값은 forword한다는 점에 주의합시다.		
	//return "redirect:/";//샌드리다이렉트를 할 때 사용함		
	
	//파일을 업로드할 수 있는 형태로 바꿈(두번째 방법)
	@RequestMapping("joinProc")  
	public String joinProc(MemberFileDTO dto){
			
		System.out.println(dto.getName());
		System.out.println(dto.getImage());//현 상황에선 ram에 있는 주소값이 출력됨
		
	String resourcePath = session.getServletContext().getRealPath("/resources");	
	System.out.println(resourcePath);
	
	try {
		
		//누군가가 회원가입을 하는 순간 폴더를 생성시킴
		dto.getImage().transferTo(new File(resourcePath + "/"+ dto.getId() + "/profile_image.png"));
		
	}catch (Exception e) {
		
		System.out.println("에러에러에러!");
		e.printStackTrace();
	}		
	
	int result = mservice.insert(dto); //이제부터 dao와의 소통은 서비스에서만 담당하고 컨트롤러는 단순히 서비스를 호출하고 그 결과만 받을 뿐이다.
	
	if(result > 0) {
		
		System.out.println("정상적으로 삽입됨");
		return "redirect:/";
	}
						
			return "angel";	
			
	    }
	
/*	
	@RequestMapping("upload.do")
	public String uploadProc(HttpServletRequest request){
		
		String resourcePath = session.getServletContext().getRealPath("/resources");
		System.out.println(resourcePath); //realPath의 경로가 실제로 경로가 저장되는 곳임
				
		int maxSize = 10 * 1024 * 1024;
				
		//Spring인걸 쓰면 안됨
		//이 라이브러리는 파일 이름이 겹칠 경우도 편하게 대처할 수 있는 것이 매력임
		try {
		
			//mul이 생성되는 순간 임시 파일이 생김
			MultipartRequest mul = new MultipartRequest(request, 
					resourcePath , 
					maxSize , 
					"utf-8" , 
					new DefaultFileRenamePolicy());
							
			String name = mul.getParameter("name");
			File oriFile = mul.getFile("image");	
			System.out.println(name);	
			System.out.println(oriFile.getAbsolutePath());	
						
						
			//라이브러리를 이용하면 원본파일을 이름을 바꾸면서 /resources폴더에 저장시키는 과정이 코드 1줄로 간단히 해결된다.
			FileUtils.moveFile(oriFile, 
			new File(resourcePath +"/"+System.currentTimeMillis()+"_id+profile.png"));
				    			    
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
			
		return "home";	
	}
	
	*/
		
	
	//로그인체크(AJAX가 있다면 HOME페이지에서 바로 체크할 수도 있겠지)
	@RequestMapping("LoginCheck")
	public String joinForm(MemberFileDTO dto , HttpServletRequest request){
						
		int login = mservice.loginCheck(dto);
		//이 값이 0일 경우 로그인 성공, 아니면 실패하도록 출력	
		
      if(login == 0){//정상적으로 로그인이 된 경우
    	  
    	  System.out.println(session.getServletContext().getRealPath("/resources/profile_image.png"));
    	  
    	  session.setAttribute("contextPath", session.getServletContext().getRealPath("/resources/profile_image.png"));
    	  session.setAttribute("loginid", dto.getId());
    	  
    	  request.setAttribute("loginTrueFalse", login);  	 
    	  
    	  return "loginResult";}  	
       
    	  request.setAttribute("loginTrueFalse", login);     	  
    	  return "loginResult";	        
	}
	

	//ajax를 이용한 비동기 id체크방식
	//produces = "application/text; charset=utf8"
	//ResponseBody를 달아주면 return값이 forword 링크가 아닌 단순한 String값을 리턴하게 된다.
	@ResponseBody
	@RequestMapping("IdCheck")
	public String AjaxIdCheck(String id){
		
		int result = mservice.IdCheck(id);
				
		if(result == 0){
		
			return "0";		
					
		}else {
			
			return "1";						
		}	
	}
	
	
	//로그아웃하는 순간 저장되어 있던 세션을 초기화시킨다.
	@RequestMapping("logoutProc")
	public String Logout(){session.invalidate(); return "redirect:/";}
	
	
	@RequestMapping("WebChat")
	public String WebChat(){return "WebChat";}
	
	
		
}
