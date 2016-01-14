package com.miller.oauthflow.browser;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DefaultBrowserLauncher implements OAuthBrowser {
	
	public void launch(String url) throws OAuthBrowserException {
		try {
			if(Desktop.isDesktopSupported())
			  Desktop.getDesktop().browse(new URI(url));
			else
				throw new OAuthBrowserException("Default browser access is not supported on this machine");
		}
		catch(IOException e) {
			throw new OAuthBrowserException("Could not launch default browser", e);
		} catch (URISyntaxException e) {
			throw new OAuthBrowserException("Could not launch default browser due to incorrect URI syntax", e);
		}
	}

}
