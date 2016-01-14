package com.miller.oauthflow.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SimpleSocketServerImpl implements OAuthCallBackServer {
	
	private int serverSocketPort = 80;
	private String responseHTML = "";
	private final String[] HEADERS = {"HTTP/1.0 200 OK", "Content-Type: text/html", "Server: Bot", ""};
	private Collection<OAuthCallBackParamListener> listeners = new ArrayList<OAuthCallBackParamListener>();
	private ServerSocket serverSocket;
	private Socket remote;
	private BufferedReader in;
	private PrintWriter out;
	public SimpleSocketServerImpl() {
		
	}
	
	public SimpleSocketServerImpl(int serverSocketPort, String responseHTML) {
		this.serverSocketPort = serverSocketPort;
		this.responseHTML = responseHTML;
	}

	public void start() throws OAuthCallBackWebServerException {
		try {
			serverSocket = new ServerSocket(serverSocketPort);
			remote = serverSocket.accept();
			out = new PrintWriter(remote.getOutputStream());
			
			for(String header : HEADERS) 
				out.println(header);
			out.println(responseHTML);
			out.flush();

			in = new BufferedReader(new InputStreamReader(remote.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if(inputLine.startsWith("GET")) {
					Map<String, String> params = parseParams(inputLine);
					for(OAuthCallBackParamListener listener : listeners)
						listener.parsedParams(params);
					break;
				}
			}

			out.close();
			in.close();
			remote.close();
			serverSocket.close();
		} catch (IOException e) {
			throw new OAuthCallBackWebServerException("Web server failed", e);
		}
	}
	
	private Map<String, String> parseParams(String url) {
		Map<String, String> params = new HashMap<String, String>();
		String cutURL = url.substring(url.indexOf("?")+1, url.length());
		String[] argArray = cutURL.split("&");
		for(int i = 0 ; i < argArray.length; i++)
		{
			String[] argSplits = argArray[i].split("=");
			if(argSplits.length > 1)
				params.put(argSplits[0], argSplits[1]);
		}
		return params;
	}

	@Override
	public void addHeaderParamListener(OAuthCallBackParamListener listener) {
		listeners.add(listener);
	}

	@Override
	public void stop() throws OAuthCallBackWebServerException {
		try {
			if(in != null)
				in.close();
			if(out != null)
				out.close();
			if(remote != null)
				remote.close();
			if(serverSocket != null)
				serverSocket.close();
		} catch (IOException e) {
			throw new OAuthCallBackWebServerException("Could not stop web server", e);
		}
	}

}
