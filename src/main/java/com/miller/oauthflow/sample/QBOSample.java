package com.miller.oauthflow.sample;

import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.miller.oauthflow.browser.DefaultBrowserLauncher;
import com.miller.oauthflow.browser.OAuthBrowserException;
import com.miller.oauthflow.webserver.OAuthCallBackParamListener;
import com.miller.oauthflow.webserver.OAuthCallBackServer;
import com.miller.oauthflow.webserver.OAuthCallBackWebServerException;
import com.miller.oauthflow.webserver.SimpleSocketServerImpl;

import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

public class QBOSample {
	
	private static final String QB_APP_TOKEN_SECRET_ID = "oauth_verifier";
	private static final String QB_REALM_ID = "realmId";
	private static final String OAUTH_CONSUMER_KEY = "GET FROM QB";
	private static final String OAUTH_CONSUMER_SECRET = "GET FROM QB";
	private static final String INTUIT_REQUEST_TOKEN_URL = "https://oauth.intuit.com/oauth/v1/get_request_token";
	private static final String INTUIT_ACCESS_TOKEN_URL = "https://oauth.intuit.com/oauth/v1/get_access_token";
	private static final String INTUIT_AUTHORIZE_URL = "https://appcenter.intuit.com/Connect/Begin";
	private static final int LOCALHOST_OAUTH_CALLBACK_URL_PORT = 8089;
	private static final String LOCALHOST_OAUTH_CALLBACK_URL = "http://localhost" + ":" + LOCALHOST_OAUTH_CALLBACK_URL_PORT;
	private final DefaultOAuthConsumer consumer = new DefaultOAuthConsumer(OAUTH_CONSUMER_KEY, OAUTH_CONSUMER_SECRET);
	private final DefaultOAuthProvider provider = new DefaultOAuthProvider(INTUIT_REQUEST_TOKEN_URL, INTUIT_ACCESS_TOKEN_URL, INTUIT_AUTHORIZE_URL);
	private static final String HTMLRESPONSE = "<html><title>OAuth Browser</title><body><h2>Process complete, you may now close this window.</h2></body></html>";
	
	public static void main(String[] args) {
		new QBOSample();
	}
		
	public QBOSample() {
		try {
			
			/* Get the auth url */
			String authUrl = provider.retrieveRequestToken(consumer, LOCALHOST_OAUTH_CALLBACK_URL);
			
			try {
				/* Launch the browser for the user to interact with */
				new DefaultBrowserLauncher().launch(authUrl);
			}
			catch(OAuthBrowserException e) {
				/* Maybe try another implementation? */
				JOptionPane.showMessageDialog(null, new JTextField(authUrl), "Could not open default browser!", JOptionPane.ERROR_MESSAGE);
			}
			
			/* Initialize the server to listen for a response */
			OAuthCallBackServer server = new SimpleSocketServerImpl(LOCALHOST_OAUTH_CALLBACK_URL_PORT, HTMLRESPONSE);
			server.addHeaderParamListener(new OAuthCallBackParamListener() {
				@Override
				public void parsedParams(Map<String,String> params) {
					try {
						
						/* Retrieve access token */
						String appTokenSecretId = params.get(QB_APP_TOKEN_SECRET_ID);
						provider.retrieveAccessToken(consumer, appTokenSecretId);
						
						/* Store the new values in preferences to be brought back later */
						String consumerKey = consumer.getConsumerKey();
						String consumerSecret = consumer.getConsumerSecret();
						String realmId = params.get(QB_REALM_ID);
						String consumerToken = consumer.getToken();
						String consumerTokenSecret = consumer.getTokenSecret();
						
						/* Store these so we can reconstruct everything later */
						System.out.println("App Token: " + appTokenSecretId);
						System.out.println("Consumer Key: " + consumerKey);
						System.out.println("Consumer Secret: " + consumerSecret);
						System.out.println("RealmId: " + realmId);
						System.out.println("Consumer Token: " + consumerToken);
						System.out.println("Consumer Token Secret: " + consumerTokenSecret);
						
					} catch (OAuthMessageSignerException e) {
						e.printStackTrace();
					} catch (OAuthNotAuthorizedException e) {
						e.printStackTrace();
					} catch (OAuthExpectationFailedException e) {
						e.printStackTrace();
					} catch (OAuthCommunicationException e) {
						e.printStackTrace();
					}
				}
			});
			
			/* Start the server */
			server.start();

		} catch (OAuthMessageSignerException e2) {
			e2.printStackTrace();
		} catch (OAuthNotAuthorizedException e2) {
			e2.printStackTrace();
		} catch (OAuthExpectationFailedException e2) {
			e2.printStackTrace();
		} catch (OAuthCommunicationException e2) {
			e2.printStackTrace();
		} catch (OAuthCallBackWebServerException e) {
			e.printStackTrace();
		} 	
	}
	
	


}
