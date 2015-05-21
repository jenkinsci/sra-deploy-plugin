/* ===========================================================================
 *  Copyright (c) 2015 Serena Software. All rights reserved.
 *
 *  Use of the Sample Code provided by Serena is governed by the following
 *  terms and conditions. By using the Sample Code, you agree to be bound by
 *  the terms contained herein. If you do not agree to the terms herein, do
 *  not install, copy, or use the Sample Code.
 *
 *  1.  GRANT OF LICENSE.  Subject to the terms and conditions herein, you
 *  shall have the nonexclusive, nontransferable right to use the Sample Code
 *  for the sole purpose of developing applications for use solely with the
 *  Serena software product(s) that you have licensed separately from Serena.
 *  Such applications shall be for your internal use only.  You further agree
 *  that you will not: (a) sell, market, or distribute any copies of the
 *  Sample Code or any derivatives or components thereof; (b) use the Sample
 *  Code or any derivatives thereof for any commercial purpose; or (c) assign
 *  or transfer rights to the Sample Code or any derivatives thereof.
 *
 *  2.  DISCLAIMER OF WARRANTIES.  TO THE MAXIMUM EXTENT PERMITTED BY
 *  APPLICABLE LAW, SERENA PROVIDES THE SAMPLE CODE AS IS AND WITH ALL
 *  FAULTS, AND HEREBY DISCLAIMS ALL WARRANTIES AND CONDITIONS, EITHER
 *  EXPRESSED, IMPLIED OR STATUTORY, INCLUDING, BUT NOT LIMITED TO, ANY
 *  IMPLIED WARRANTIES OR CONDITIONS OF MERCHANTABILITY, OF FITNESS FOR A
 *  PARTICULAR PURPOSE, OF LACK OF VIRUSES, OF RESULTS, AND OF LACK OF
 *  NEGLIGENCE OR LACK OF WORKMANLIKE EFFORT, CONDITION OF TITLE, QUIET
 *  ENJOYMENT, OR NON-INFRINGEMENT.  THE ENTIRE RISK AS TO THE QUALITY OF
 *  OR ARISING OUT OF USE OR PERFORMANCE OF THE SAMPLE CODE, IF ANY,
 *  REMAINS WITH YOU.
 *
 *  3.  EXCLUSION OF DAMAGES.  TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE
 *  LAW, YOU AGREE THAT IN CONSIDERATION FOR RECEIVING THE SAMPLE CODE AT NO
 *  CHARGE TO YOU, SERENA SHALL NOT BE LIABLE FOR ANY DAMAGES WHATSOEVER,
 *  INCLUDING BUT NOT LIMITED TO DIRECT, SPECIAL, INCIDENTAL, INDIRECT, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, DAMAGES FOR LOSS OF
 *  PROFITS OR CONFIDENTIAL OR OTHER INFORMATION, FOR BUSINESS INTERRUPTION,
 *  FOR PERSONAL INJURY, FOR LOSS OF PRIVACY, FOR NEGLIGENCE, AND FOR ANY
 *  OTHER LOSS WHATSOEVER) ARISING OUT OF OR IN ANY WAY RELATED TO THE USE
 *  OF OR INABILITY TO USE THE SAMPLE CODE, EVEN IN THE EVENT OF THE FAULT,
 *  TORT (INCLUDING NEGLIGENCE), STRICT LIABILITY, OR BREACH OF CONTRACT,
 *  EVEN IF SERENA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.  THE
 *  FOREGOING LIMITATIONS, EXCLUSIONS AND DISCLAIMERS SHALL APPLY TO THE
 *  MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW.  NOTWITHSTANDING THE ABOVE,
 *  IN NO EVENT SHALL SERENA'S LIABILITY UNDER THIS AGREEMENT OR WITH RESPECT
 *  TO YOUR USE OF THE SAMPLE CODE AND DERIVATIVES THEREOF EXCEED US$10.00.
 *
 *  4.  INDEMNIFICATION. You hereby agree to defend, indemnify and hold
 *  harmless Serena from and against any and all liability, loss or claim
 *  arising from this agreement or from (i) your license of, use of or
 *  reliance upon the Sample Code or any related documentation or materials,
 *  or (ii) your development, use or reliance upon any application or
 *  derivative work created from the Sample Code.
 *
 *  5.  TERMINATION OF THE LICENSE.  This agreement and the underlying
 *  license granted hereby shall terminate if and when your license to the
 *  applicable Serena software product terminates or if you breach any terms
 *  and conditions of this agreement.
 *
 *  6.  CONFIDENTIALITY.  The Sample Code and all information relating to the
 *  Sample Code (collectively "Confidential Information") are the
 *  confidential information of Serena.  You agree to maintain the
 *  Confidential Information in strict confidence for Serena.  You agree not
 *  to disclose or duplicate, nor allow to be disclosed or duplicated, any
 *  Confidential Information, in whole or in part, except as permitted in
 *  this Agreement.  You shall take all reasonable steps necessary to ensure
 *  that the Confidential Information is not made available or disclosed by
 *  you or by your employees to any other person, firm, or corporation.  You
 *  agree that all authorized persons having access to the Confidential
 *  Information shall observe and perform under this nondisclosure covenant.
 *  You agree to immediately notify Serena of any unauthorized access to or
 *  possession of the Confidential Information.
 *
 *  7.  AFFILIATES.  Serena as used herein shall refer to Serena Software,
 *  Inc. and its affiliates.  An entity shall be considered to be an
 *  affiliate of Serena if it is an entity that controls, is controlled by,
 *  or is under common control with Serena.
 *
 *  8.  GENERAL.  Title and full ownership rights to the Sample Code,
 *  including any derivative works shall remain with Serena.  If a court of
 *  competent jurisdiction holds any provision of this agreement illegal or
 *  otherwise unenforceable, that provision shall be severed and the
 *  remainder of the agreement shall remain in full force and effect.
 * ===========================================================================
 */

package com.urbancode.ds.jenkins.plugins.serenarapublisher;

import java.io.Serializable;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.lang.StringUtils;

import com.urbancode.commons.util.https.OpenSSLProtocolSocketFactory;

import org.codehaus.jettison.json.JSONObject;

public class UrbanDeploySite implements Serializable {

    private static final long serialVersionUID = -8723534991244260459L;

    /** The profile name. */
	private String profileName;

	/** The url. */
	private String url;

	/** The username. */
	private String user;

	/** The password. */
	private String password;

	/**
	 * Instantiates a new UrbanDeploy site.
	 *
	 * @param profileName
	 *          the profile name
	 * @param url
	 *          the url of the UrbanDeploy instance
	 * @param username
	 *          the username
	 * @param password
	 *          the password
	 */
	 public UrbanDeploySite() {

	}
	 
	public UrbanDeploySite(String profileName, String url, String user, String password) {
		this.profileName = profileName;
		this.url = url;
		this.user = user;
		this.password = password;
	}


	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName() {
		if (StringUtils.isEmpty(profileName)) {
			return url;
		} else {
			return profileName;
		}
	}

	/**
	 * Gets the profile name.
	 *
	 * @return the profile name
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * Sets the profile name.
	 *
	 * @param profileName
	 *          the new profile name
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *          the new url
	 */
	public void setUrl(String url) {
		this.url = url;
        if (this.url != null) {
            this.url = this.url.replaceAll("\\\\", "/");
        }
        while (this.url != null && this.url.endsWith("/")) {
            this.url = this.url.substring(0, this.url.length() - 1);
        }
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *          the new username
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *          the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

    public void verifyConnection() throws Exception {
        URI uri = UriBuilder.fromPath(url).path("rest").path("state").build();
        executeJSONGet(uri);
    }

    public void verifyComponentExists(String componentName) throws Exception {
        URI uri = UriBuilder.fromPath(url).path("cli").path("component").path("info").queryParam("component", componentName).build();
        executeJSONGet(uri);
    }

    public void verifyApplicationExists(String applicationName) throws Exception {
        URI uri = UriBuilder.fromPath(url).path("cli").path("application").path("info").queryParam("application", applicationName).build();
        executeJSONGet(uri);
    }

    public void verifyEnvironmentExists(String environmentName, String applicationName) throws Exception {
        URI uri = UriBuilder.fromPath(url).path("cli").path("environment").path("info").queryParam("environment", environmentName).queryParam("application", applicationName).build();
        executeJSONGet(uri);
    }

    public void verifyApplicationProcessExists(String applicationProcess, String applicationName) throws Exception {
        URI uri = UriBuilder.fromPath(url).path("cli").path("applicationProcess").path("info").queryParam("application", applicationName).queryParam("applicationProcess", applicationProcess).build();
        executeJSONGet(uri);
    }

    // TODO: this is an expensive operation, we need a new cli class for statuses or rest query by status name
    public void verifyStatusExists(String stateName) throws Exception {
        URI uri = UriBuilder.fromPath(url).path("rest").path("deploy").path("status").path("versionStatuses").build();
        String jsonOut = executeJSONGet(uri);
        if (!jsonOut.contains(stateName)) throw new Exception(stateName + " not found");
    }

    public void verifyResourceExists(String resourceName) throws Exception {
        URI uri = UriBuilder.fromPath(url).path("cli").path("resource").path("info").queryParam("resource", resourceName).build();
        executeJSONGet(uri);
    }

    public String getResourceId(String resourceName) throws Exception {
        URI uri = UriBuilder.fromPath(url).path("cli").path("resource").path("info").queryParam("resource", resourceName).build();
        String resourceJson = executeJSONGet(uri);
        JSONObject resourceObj = new JSONObject(resourceJson);
        return resourceObj.getString("id");
    }

    public void verifyProcessExists(String processName) throws Exception {
        URI uri = UriBuilder.fromPath(url).path("rest").path("process").path("false").build();
        String processContent = executeJSONGet(uri);
        String processStr = "\"name\":\"" + processName + "\"";
        if (!processContent.contains(processStr)) throw new Exception(processName + " not found");
    }

    // TODO: this is an expensive operation, we need a new cli class for generic processes or rest query by process name
    public String getProcessId(String processName) throws Exception {
        URI uri = UriBuilder.fromPath(url).path("rest").path("process").path("false").build();
        String processContent = executeJSONGet(uri);
        String processStr = "\"name\":\"" + processName + "\"";
        int start = processContent.indexOf("\"name\":\"" + processName + "\"");
        if (processContent.contains(processStr)) {
            String processId = processContent.substring(start-38, start-2);
            return processId;
        }
        return null;
    }

    public String executeJSONGet(URI uri) throws Exception {
        String result = null;
        HttpClient httpClient = new HttpClient();

        if ("https".equalsIgnoreCase(uri.getScheme())) {
            ProtocolSocketFactory socketFactory = new OpenSSLProtocolSocketFactory();
            Protocol https = new Protocol("https", socketFactory, 443);
            Protocol.registerProtocol("https", https);
        }

        GetMethod method = new GetMethod(uri.toString());
        setDirectSsoInteractionHeader(method);          
        try {
            HttpClientParams params = httpClient.getParams();
            params.setAuthenticationPreemptive(true);

            UsernamePasswordCredentials clientCredentials = new UsernamePasswordCredentials(user, password);
            httpClient.getState().setCredentials(AuthScope.ANY, clientCredentials);

            int responseCode = httpClient.executeMethod(method);
            //if (responseCode < 200 || responseCode < 300) {
            if (responseCode == 401) {
                throw new Exception("Error connecting to Serena DA: Invalid user and/or password");
            }
            else if (responseCode != 200) {
                throw new Exception("Error connecting to Serena DA: " + responseCode);
            }
            else {
                result = method.getResponseBodyAsString();
            }
        }
        finally {
            method.releaseConnection();
        }

        return result;
    }

    public String executeJSONPut(URI uri, String putContents) throws Exception {
        String result = null;
        HttpClient httpClient = new HttpClient();

        if ("https".equalsIgnoreCase(uri.getScheme())) {
            ProtocolSocketFactory socketFactory = new OpenSSLProtocolSocketFactory();
            Protocol https = new Protocol("https", socketFactory, 443);
            Protocol.registerProtocol("https", https);
        }

        PutMethod method = new PutMethod(uri.toString());
        setDirectSsoInteractionHeader(method);
        if (putContents != null)
            method.setRequestBody(putContents);
        method.setRequestHeader("Content-Type", "application/json");
        method.setRequestHeader("charset", "utf-8");
        try {
            HttpClientParams params = httpClient.getParams();
            params.setAuthenticationPreemptive(true);

            UsernamePasswordCredentials clientCredentials = new UsernamePasswordCredentials(user, password);
            httpClient.getState().setCredentials(AuthScope.ANY, clientCredentials);

            int responseCode = httpClient.executeMethod(method);

            //if (responseCode < 200 || responseCode < 300) {
            if ( responseCode != 200 && responseCode != 204 ) {
                throw new Exception("Serena DA returned error code: " + responseCode);
            }
            else {
                result = method.getResponseBodyAsString();
            }
        }
        catch (Exception e) {
            throw new Exception("Error connecting to Serena DA: " + e.getMessage());
        }
        finally {
            method.releaseConnection();
        }

        return result;
    }

    public String executeJSONPost(URI uri, String postContents) throws Exception {
        String result = null;
        HttpClient httpClient = new HttpClient();

        if ("https".equalsIgnoreCase(uri.getScheme())) {
            ProtocolSocketFactory socketFactory = new OpenSSLProtocolSocketFactory();
            Protocol https = new Protocol("https", socketFactory, 443);
            Protocol.registerProtocol("https", https);
        }

        PostMethod method = new PostMethod(uri.toString());
        setDirectSsoInteractionHeader(method);
        if (postContents != null)
            method.setRequestBody(postContents);
        method.setRequestHeader("Content-Type", "application/json");
        method.setRequestHeader("charset", "utf-8");
        try {
            HttpClientParams params = httpClient.getParams();
            params.setAuthenticationPreemptive(true);

            UsernamePasswordCredentials clientCredentials = new UsernamePasswordCredentials(user, password);
            httpClient.getState().setCredentials(AuthScope.ANY, clientCredentials);

            int responseCode = httpClient.executeMethod(method);

            if (responseCode != 200 ) {
                throw new Exception("Serena DA returned error code: " + responseCode);
            }
            else {
                result = method.getResponseBodyAsString();
            }
        }
        catch (Exception e) {
            throw new Exception("Error connecting to Serena DA: " + e.getMessage());
        }
        finally {
            method.releaseConnection();
        }

        return result;
    }

    public String encodePath(String path) {
        String result;
        URI uri;
        try {
            uri = new URI(null,null, path, null);
            result = uri.toASCIIString();
        }
        catch (Exception e) {
            result = path;
        }
        return result;
    }
    
    private void setDirectSsoInteractionHeader(HttpMethodBase method) {
    	method.setRequestHeader("DirectSsoInteraction", "true");   
    }     
}
