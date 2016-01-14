package com.miller.oauthflow.webserver;

import java.util.Map;

public interface OAuthCallBackParamListener {
	
	public void parsedParams(Map<String,String> params);

}
