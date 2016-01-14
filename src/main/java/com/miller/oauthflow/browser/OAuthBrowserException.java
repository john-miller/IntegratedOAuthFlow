package com.miller.oauthflow.browser;

public class OAuthBrowserException  extends Exception {

	private static final long serialVersionUID = 501201422281107133L;

	public OAuthBrowserException(String message) {
        super(message);
    }

    public OAuthBrowserException(String message, Throwable throwable) {
        super(message, throwable);
    }

}