package com.miller.oauthflow.browser;

import static org.junit.Assert.*;

import org.junit.Test;

import com.miller.oauthflow.browser.DefaultBrowserLauncher;
import com.miller.oauthflow.browser.OAuthBrowserException;

public class DefaultBrowserLauncherTest {

	@Test
	public void construct() {
		assertNotNull(new DefaultBrowserLauncher());
	}
	
	@Test(expected = OAuthBrowserException.class)
	public void launch() throws OAuthBrowserException {
		new DefaultBrowserLauncher().launch("this is not a url");
	}
}
