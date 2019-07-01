package kh.spring.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import kh.spring.dao.MemberDAO;

//단순 메소드가 수행할때마다 기록 로그를 남길떈 유지보수를 위해 @before를 사용하는 것이 좋다.

@Component
@Aspect
public class PerfCheckAdvice {
	
	@Autowired
	MemberDAO dao;
	
	@Autowired
    HttpSession session;
	
	//(..)은 있을수도 있고 없을 수도 있다는 뜻임 기억해두셈
    //()는 매개변수가 아예 없는 것을,(String, ..)는 매개변수가 String , 두번째 매개변수 이후론 없을수도 있을수도 있다는 뜻
	@Pointcut("execution(* kh.spring.pratice.HomeController.*(..))")
	public void HomeCut(){}
		
	@Pointcut("execution(* kh.spring.pratice.HomeController.MypageMove(..))")
	public void MypageCut(){}
	
	
	@Around("HomeCut()")
	public Object perfCheck(ProceedingJoinPoint pjp){

		//시작과 끝을 선언
		long startTime = System.currentTimeMillis();

		Object retval = null;
		
		try{
		
			retval = pjp.proceed();
			
		}catch (Throwable e){

			e.printStackTrace();}

		long endTime = System.currentTimeMillis();	
		System.out.println(pjp.getSignature().getName()+"이(가) 메소드가 수행하는데 걸린 시간은 " +(endTime - startTime)/1000.0 +" 초 입니다.");

		return retval; 
	}
	

	
	//MypageCut()
	//마이페이지 버튼을 눌렀을때 로그인이 되지 않았을 경우 아예 접근 자체를 통제해버린다면?(get 방식일 경우를 생각해보자)
	//@Around메소드는 자기 전용 매개변수 말고 다른 매개변수 선언이 불가능하다는 걸 알아두자
	@Around("MypageCut()")
	public Object LoginCheck(ProceedingJoinPoint pjp) throws Throwable{
		 
		String loginid = (String)session.getAttribute("loginid");

		int result = dao.idCheck(loginid);

		if(result==0){

			return pjp.proceed(); //단순히 이렇게만 해도 리턴값을 다시 원 메소드로 보내는구나
                                  //(이 메소드의 before/after 시점을 잘 기억해두셈)
		}else {
							
			//ModelAndView는 과거 request처럼 무언가 세팅할떄 사용하지 보통은 걍 String값으로 보내도 상관없음
			//return값에 주의하면서 코드를 만들어보자
			ModelAndView mov = new ModelAndView();
					
		   //mov.addObject는 지금까지 했던 request.set에트리튜브와 같다
			mov.addObject("error","angel");		
			mov.setViewName("errorAlert");
			return mov;
			
			//단순히 페이지를 이동하고 싶다면 return "errorAlert";라고 써주자
			
		}
	}
	
	
	
	
	
	
	//메소드의 시점을 생각해보면 된다
	//스프링의 흐름상 
	
	
	
	
	
	
}
