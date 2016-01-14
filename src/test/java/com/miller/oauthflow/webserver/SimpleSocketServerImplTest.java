package com.miller.oauthflow.webserver;

import static org.junit.Assert.*;

import org.junit.Test;

import com.miller.oauthflow.webserver.OAuthCallBackServer;
import com.miller.oauthflow.webserver.OAuthCallBackWebServerException;
import com.miller.oauthflow.webserver.SimpleSocketServerImpl;

public class SimpleSocketServerImplTest {

	@Test
	public void construct() {
		assertNotNull(new SimpleSocketServerImpl());
		assertNotNull(new SimpleSocketServerImpl(80, ""));
	}
	
	@Test
	public void start() throws OAuthCallBackWebServerException {
		OAuthCallBackServer server = new SimpleSocketServerImpl(8084, "");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					server.start();
				} catch (OAuthCallBackWebServerException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		server.stop();
	}

}
