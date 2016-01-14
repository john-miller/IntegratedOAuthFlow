package com.miller.oauthflow.webserver;

public interface OAuthCallBackServer {
	
	public void addHeaderParamListener(OAuthCallBackParamListener listener);
	
	public void start() throws OAuthCallBackWebServerException;
	
	public void stop() throws OAuthCallBackWebServerException;

}
