package com.miller.oauthflow.webserver;

public class OAuthCallBackWebServerException extends Exception {

	private static final long serialVersionUID = 4439009501217504849L;

	public OAuthCallBackWebServerException(String message) {
        super(message);
    }

    public OAuthCallBackWebServerException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
