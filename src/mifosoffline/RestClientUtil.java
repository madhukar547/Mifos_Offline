package mifosoffline;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
/**
 * The <code>RestClientUtil</code> used to get <code>HttpClient</code> object
 * @author Prudhvi
 *
 */

public class RestClientUtil {
	
	static HttpClient httpConn=null;
	public static String URL=null;
	static String USERNAME=null;
	static String PASSWORD=null;
	static int httpRes;
	static{
		Properties p=new Properties();
		try {
			p.load(new FileInputStream("./Authentication.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		USERNAME=p.getProperty("username");
		PASSWORD=p.getProperty("password");
		URL=p.getProperty("url");
	}
	
	
	public static HttpClient prepareConnection() throws HttpException, IOException{
	  
	  httpConn = new HttpClient();
	  Credentials credentials = new UsernamePasswordCredentials(USERNAME,PASSWORD);
	  
	  httpConn.getParams().setAuthenticationPreemptive(true);
	 
	  httpConn.getState().setCredentials(AuthScope.ANY, credentials);
	 
	  HttpConnectionManagerParams conpar = new HttpConnectionManagerParams();
	 
	  conpar.setConnectionTimeout(45 * 60000); // 5 minutes
	 
	  conpar.setSoTimeout(45 * 60000); // 5 minutes
	 
	  httpConn.getHttpConnectionManager().setParams(conpar);
	 
	  GetMethod get = new GetMethod(URL);
	 
	  get.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
	 
	  get.setDoAuthentication(true);
	 
	  httpConn.executeMethod(get);
	 
	  String responseBody = get.getResponseBodyAsString();
          //System.out.println("Authentication..."+responseBody);
	 
	  if (responseBody.contains("j_spring_security_check")) {
	 
	  String loginUrl = URL +
	  "/j_spring_security_check?j_username="+USERNAME+"&j_password="+PASSWORD;
	 
	  PostMethod authpost = new PostMethod(loginUrl);
	 
	  // Release Get Connection
	 
	  get.releaseConnection();
	 
	  httpRes = httpConn.executeMethod(authpost);
	 
	  responseBody = authpost.getResponseBodyAsString();
	 
	  if (httpRes == 301 || httpRes == 302 || httpRes == 307) {
	 
	  // redirected, get content page
	 
	  get = new GetMethod(authpost.getResponseHeader("Location")
	 
	  .getValue());
	 
	 
	  get.setRequestHeader("Content-Type","text/plain; charset=UTF-8");
	 
	  authpost.releaseConnection();
	 
	  httpRes = httpConn.executeMethod(get);
	 
	  responseBody = get.getResponseBodyAsString();
	 
	  get.releaseConnection();
	 
	  }
	 
	  } else {
             get.releaseConnection();
	 
	  }
	 
	  return httpConn;
	 }
	public static HttpClient getClient() throws HttpException, IOException{
		if(httpConn!=null)
			return httpConn;
		return prepareConnection();
	}
	

}
