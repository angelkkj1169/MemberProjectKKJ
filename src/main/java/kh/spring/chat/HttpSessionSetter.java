package kh.spring.chat;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

//클래스 안의 클래스를 상속받음(inner 클래스)
public class HttpSessionSetter extends ServerEndpointConfig.Configurator{

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response){
		
		//웹소켓의 세션을 우리가 아는 HttpSession에 담는다.
		//ServerEndpointConfig sec에 값을 담으면 나중에 onopen에서 사용가능
		HttpSession hsession = (HttpSession)request.getHttpSession();
			
		//UserProperties에 HttpSession을 담는다.
		sec.getUserProperties().put("httpSession", hsession);
	}	
}



