package kh.spring.chat;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

	//필요로 하는 정보를 뽑아올려면 여기로 오기 전에 먼저 사전작업을 해두어야 함(id라든가 ip라든가)
	//configurator=HttpSessionSetter.class : Handshake하는 과정 도중에 관여하는 클래스를 설정해 준다.
	@ServerEndpoint(value = "/webchat" , configurator=HttpSessionSetter.class)
	public class WebChat {
	
		//여기의 hsession은 웹소켓이 아닌 http의 세션으로 핸드쉐이킹 과정에서 여러가지 정보를 가져올 수 있다. 
		private HttpSession hsession;	
				
		//모두가 같은 서버에서 채팅을 할 것이므로 정적 변수가 필요하다.
		//여기의 Session은 웹소켓의 session이다.
		//코드가 복잡해지는 이유(synchronizedSet)는 동시성 수정오류를 막기 위해서 사용함
		//(이렇게 설정해 주면 for문이 작동한 후 remove가 일어난다고 함)
		private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
			
			
		@OnMessage
		public void onMessage(String message , Session session) throws IOException{
		
			//synchronized를 달아주면 for문이 완전히 끝난 이후에야 remove를 할 수 있다.
			synchronized(clients) {
				
				for(Session each : clients){  

					if(each != session ){

						if(message.equals(hsession.getAttribute("loginid") + "님이 접속했습니다.")){

							each.getBasicRemote().sendText(message);}

						else if(message.equals(hsession.getAttribute("loginid") + "님이 채팅방에서 나갔습니다.")){

							each.getBasicRemote().sendText(message);

						}else{

							each.getBasicRemote().sendText(hsession.getAttribute("loginid")+": "+ message);

						}
					}	
				}		
			}	
		}
		
		
			
	    //누군가가 접속하는 순간 clients에 정보를 저장시킴
		//이 메소드는 서버에 접속하자마자 가장 먼저 실행될 것이다
		@OnOpen
		public void onOpen(Session session, EndpointConfig ec) throws IOException{
		        
			System.out.println("접속자 발생");	
			hsession = (HttpSession)ec.getUserProperties().get("httpSession");
			
		  //System.out.println(hsession.getAttribute("loginid")+"님이 접속했습니다.");
	      String message = hsession.getAttribute("loginid")+"님이 접속했습니다.";
			
			this.onMessage(message, session);
			clients.add(session);}
		
			
		
		//누군가가 서버에서 나가는 순간 clients에 정보를 삭제시킴
		@OnClose
		public void onClose(Session session) throws IOException{

			//System.out.println(hsession.getAttribute("loginid") + "님이 채팅방에서 나갔습니다.");
			String message = hsession.getAttribute("loginid") + "님이 채팅방에서 나갔습니다.";
			
			this.onMessage(message, session);
			clients.remove(session);}
		
		
}
	
