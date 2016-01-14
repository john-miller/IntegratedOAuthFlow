package com.miller.oauthflow.browser;

public interface OAuthBrowser {
	
	public void launch(String url) throws OAuthBrowserException;

}
