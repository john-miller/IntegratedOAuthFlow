package com.miller.intuit.oauthflow.browser;

public interface OAuthBrowser {
	
	public void launch(String url) throws OAuthBrowserException;

}
