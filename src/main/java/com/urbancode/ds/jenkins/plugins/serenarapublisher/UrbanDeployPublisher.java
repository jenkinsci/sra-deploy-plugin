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

import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jettison.json.JSONObject;

/**
 * <p> This class implements the UrbanDeploy publisher process by using the {@link
 * com.urbancode.ds.jenkins.plugins.serenarapublisher.UrbanDeploySite}. </p>
 */
public class UrbanDeployPublisher extends Notifier {

    /**
     * Hold an instance of the Descriptor implementation for the UrbanDeploy Publisher.
     */
    @Extension
    public static final UrbanDeployPublisherDescriptor DESCRIPTOR = new UrbanDeployPublisherDescriptor();

    private String siteName;
    private Boolean useAnotherUser;
    private String anotherUser;
    private String anotherPassword;
    private Boolean skip = false;
    private String component;
    private String baseDir;
    private String directoryOffset;
    private String fileIncludePatterns;
    private String fileExcludePatterns;
    private String versionName;
    private Boolean addPropertiesToVersion = false;
    private String versionProps;
    private Boolean addStatus = false;
    private String statusName;
    private Boolean deploy = false;
    private String deployIf;
    private String deployApp;
    private String deployEnv;
    private String deployProc;
    private String deployProps;
    private Boolean runProcess = false;
    private String processIf;
    private String processName;
    private String resourceName;
    private String processProps;
    private String deploymentResult;
    private String processResult;
    private Map<String, String> envMap = null;

    /**
     * Default constructor
     */
    public UrbanDeployPublisher(String siteName, Boolean useAnotherUser, String anotherUser, String anotherPassword,
                                Boolean skip, String component, String versionName, String directoryOffset, String baseDir,
                                String fileIncludePatterns, String fileExcludePatterns,
                                String versionProps,
                                Boolean addStatus, String statusName,
                                Boolean deploy, String deployIf,
                                String deployApp, String deployEnv, String deployProc, String deployProps,
                                Boolean runProcess, String processIf,
                                String processName, String resourceName, String processProps) {
        this.siteName = siteName;
        this.useAnotherUser = useAnotherUser;
        this.anotherUser = anotherUser;
        this.anotherPassword = anotherPassword;
        this.skip = skip;
        this.component = component;
        this.versionName = versionName;
        this.baseDir = baseDir;
        this.directoryOffset = directoryOffset;
        this.fileIncludePatterns = fileIncludePatterns;
        this.fileExcludePatterns = fileExcludePatterns;
        this.versionProps = versionProps;
        this.addStatus = addStatus;
        this.statusName = statusName;
        this.deploy = deploy;
        this.deployIf = deployIf;
        this.deployApp = deployApp;
        this.deployEnv = deployEnv;
        this.deployProc = deployProc;
        this.deployProps = deployProps;
        this.runProcess = runProcess;
        this.processIf = processIf;
        this.processName = processName;
        this.resourceName = resourceName;
        this.processProps = processProps;
    }

    public String getSiteName() {
        String name = siteName;
        if (name == null) {
            UrbanDeploySite[] sites = DESCRIPTOR.getSites();
            if (sites.length > 0) {
                name = sites[0].getProfileName();
            }
        }
        return name;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setUseAnotherUser(boolean useAnotherUser) {
        this.useAnotherUser = useAnotherUser;
    }

    public boolean isUseAnotherUser() {
        return Boolean.TRUE.equals(this.useAnotherUser);
    }

    public String getAnotherUser() {
        return this.anotherUser;
    }

    public void setAnotherUser(String anotherUser) {
        this.anotherUser = anotherUser;
    }

    public String getAnotherPassword() {
        return this.anotherPassword;
    }

    public void setAnotherPassword(String anotherPassword) {
        this.anotherPassword = anotherPassword;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public boolean isSkip() {
        return Boolean.TRUE.equals(this.skip);
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getBaseDir() {
        if (baseDir == null || baseDir.length() == 0) {
            baseDir = "${WORKSPACE}";
        }
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir= baseDir;
    }

    public String getDirectoryOffset() {
        return directoryOffset;
    }

    public void setDirectoryOffset(String directoryOffset) {
        this.directoryOffset = directoryOffset;
    }

    public String getFileIncludePatterns() {
        if (fileIncludePatterns == null || fileIncludePatterns.trim().length() == 0) {
            fileIncludePatterns = "**/*";
        }
        return fileIncludePatterns;
    }

    public void setFileIncludePatterns(String fileIncludePatterns) {
        this.fileIncludePatterns = fileIncludePatterns;
    }

    public String getFileExcludePatterns() {
        return fileExcludePatterns;
    }

    public void setFileExcludePatterns(String fileExcludePatterns) {
        this.fileExcludePatterns = fileExcludePatterns;
    }

    public void setAddPropertiesToVersion(boolean addPropertiesToVersion) {
        this.addPropertiesToVersion = addPropertiesToVersion;
    }

    public boolean isAddPropertiesToVersion() {
        return Boolean.TRUE.equals(this.addPropertiesToVersion);
    }

    public void setVersionProps(String versionProps) {
        this.versionProps = versionProps;
    }

    public String getVersionProps() {
        return versionProps;
    }

    public void setAddStatus(boolean addStatus) {
        this.addStatus = addStatus;
    }

    public boolean isAddStatus() {
        return Boolean.TRUE.equals(this.addStatus);
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getVersionName() {
        if (versionName == null || versionName.length() == 0) {
            versionName = "${BUILD_NUMBER}";
        }
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setDeploy(boolean deploy) {
        this.deploy = deploy;
    }

    public boolean isDeploy() {
        return Boolean.TRUE.equals(this.deploy);
    }

    public void setDeployIf(String deployIf) {
        this.deployIf = deployIf;
    }

    public String getDeployIf() {
        return deployIf;
    }

    public void setDeployApp(String deployApp) {
        this.deployApp = deployApp;
    }

    public String getDeployApp() {
        return deployApp;
    }

    public void setDeployEnv(String deployEnv) {
        this.deployEnv = deployEnv;
    }

    public String getDeployEnv() {
        return deployEnv;
    }

    public void setDeployProc(String deployProc) {
        this.deployProc = deployProc;
    }

    public String getDeployProc() {
        return deployProc;
    }

    public void setDeployProps(String deployProps) {
        this.deployProps = deployProps;
    }

    public String getDeployProps() {
        return deployProps;
    }

    public void setRunProcess(boolean runProcess) {
        this.runProcess = runProcess;
    }

    public boolean isRunProcess() {
        return Boolean.TRUE.equals(this.runProcess);
    }

    public void setProcessIf(String processIf) {
        this.processIf = processIf;
    }

    public String getProcessIf() {
        return processIf;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setProcessProps(String processProps) {
        this.processProps = processProps;
    }

    public String getProcessProps() {
        return processProps;
    }

    /**
     * This method returns the configured UrbanDeploySite object which match the siteName of the UrbanDeployPublisher
     * instance. (see Manage Hudson and System Configuration point UrbanDeploy)
     *
     * @return the matching UrbanDeploySite or null
     */
    public UrbanDeploySite getSite() {
        UrbanDeploySite[] sites = DESCRIPTOR.getSites();
        if (siteName == null && sites.length > 0) {
            // default
            return sites[0];
        }
        for (UrbanDeploySite site : sites) {
            if (site.getDisplayName().equals(siteName)) {
                return site;
            }
        }
        return null;
    }


    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

    /**
     * {@inheritDoc}
     *
     * @param build
     * @param launcher
     * @param listener
     * @return
     * @throws InterruptedException
     * @throws java.io.IOException  {@inheritDoc}
     * @see hudson.tasks.BuildStep#perform(hudson.model.Build, hudson.Launcher, hudson.model.BuildListener)
     */

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
            throws InterruptedException, IOException {

        UrbanDeploySite udSite = getSite();
        String resolvedAnotherUser = null;
        String resolvedAnotherPassword = null;
        String deployURI = null;
        String processURI = null;

        PublishArtifactsCallable task = null;

        if (isUseAnotherUser())
        {
            resolvedAnotherUser = resolveVariables(getAnotherUser());
            resolvedAnotherPassword = resolveVariables(getAnotherPassword());
            udSite = new UrbanDeploySite(udSite.getProfileName(), udSite.getUrl(), resolvedAnotherUser, resolvedAnotherPassword);
            listener.getLogger().println("[SDA] Using a different user to access Serena DA: " + udSite.getUser());
        }
        if (isSkip()) {
            listener.getLogger().println("[SDA] Skipping artifacts upload to Serena DA.");
        }
        else if (build.getResult() == Result.FAILURE || build.getResult() == Result.ABORTED) {
            listener.getLogger().println("[SDA] Skipping artifacts upload to Serena DA - build failed or aborted.");
            return false;
        }
        else {
            listener.getLogger().println("[SDA] Artifacts upload is enabled.");
            envMap = build.getEnvironment(listener);

            String resolvedStatusName = resolveVariables(getStatusName());
            String resolvedComponent = resolveVariables(getComponent());
            String resolvedBaseDir = resolveVariables(getBaseDir());
            String resolvedVersionName = resolveVariables(getVersionName());
            String resolvedFileIncludePatterns = resolveVariables(fileIncludePatterns);
            String resolvedFileExcludePatterns = resolveVariables(fileExcludePatterns);
            String resolvedDirectoryOffset = resolveVariables(directoryOffset);
            String resolvedVersionProperties = resolveVariables(getVersionProps());
            String resolvedDeployProperties = resolveVariables(getDeployProps());
            String resolvedDeployIf = resolveVariables(getDeployIf());

            // iterate over version properties to construct JSON string
            String jsonVersionProperties = "{";
            if (resolvedVersionProperties == null || resolvedVersionProperties.trim().length() == 0) {
                setAddPropertiesToVersion(false);
                listener.getLogger().println("[SDA] Version Properties: none defined");
            } else {
                setAddPropertiesToVersion(true);
                // iterate over properties
                BufferedReader bufReader = new BufferedReader(new StringReader(resolvedVersionProperties));
                String line = null, propName = null, propVal = null;
                while ((line = bufReader.readLine()) != null) {
                    String[] parts = line.split("=");
                    listener.getLogger().println("[SDA] Version Property: " + parts[0] + " = " + parts[1]);
                    jsonVersionProperties += ("\"" + parts[0] + "\": \"" + parts[1] + "\",");
                }
                // remove last comma if it exists
                if (jsonVersionProperties.endsWith(",")) jsonVersionProperties = jsonVersionProperties.substring(0, jsonVersionProperties.length() - 1);
            }
            jsonVersionProperties += "}";

            // iterate over deployment properties to construct JSON string
            String jsonDeployProperties = "{";
            if (resolvedDeployProperties == null || resolvedDeployProperties.trim().length() == 0) {
                listener.getLogger().println("[SDA] Deployment properties: none defined");
            } else {
                BufferedReader bufReader = new BufferedReader(new StringReader(resolvedDeployProperties));
                String line;
                while ((line = bufReader.readLine()) != null) {
                    String[] parts = line.split("=");
                    String val = (parts.length == 1 ? "" : parts[1]); // do we have a value
                    listener.getLogger().println("[SDA] Deployment property: " + parts[0] + " = " + val);
                    jsonDeployProperties += ("\"" + parts[0] + "\": \"" + val + "\",");
                }
                // remove last comma if it exists
                if (jsonDeployProperties.endsWith(","))
                    jsonDeployProperties = jsonDeployProperties.substring(0, jsonDeployProperties.length() - 1);
            }
            jsonDeployProperties += "}";

            try {
                task = new PublishArtifactsCallable(resolvedBaseDir, resolvedDirectoryOffset,
                        udSite, resolvedFileIncludePatterns, resolvedFileExcludePatterns, resolvedComponent,
                        resolvedVersionName, isAddPropertiesToVersion(), jsonVersionProperties,
                        isAddStatus(), resolvedStatusName, listener);
                try {
                    launcher.getChannel().call(task);
                } catch (InterruptedException e) {
                    throw new Exception("[SDA] Error calling PublishArtifactsCallable!");
                }

                // work out if we should initiate deployment of version
                Boolean deployAfterUpload = false;
                // is deployVersionIf override defined
                if (resolvedDeployIf == null || resolvedDeployIf.length() == 0) {
                    // no, check deployVersion parameter
                    if (isDeploy()) {
                        deployAfterUpload = true;
                    } else {
                        deployAfterUpload = false;
                    }
                } else {
                    // yes, check if it is set to true or yes
                    if (resolvedDeployIf.equals("false") || resolvedDeployIf.equals("no")) {
                        deployAfterUpload = false;
                    }
                    if (resolvedDeployIf.equals("true") || resolvedDeployIf.equals("yes")) {
                        deployAfterUpload = true;
                    }
                }
                listener.getLogger().println("[SDA] Deploy after upload is " + deployAfterUpload);

                if (deployAfterUpload) {
                    if (getDeployApp() == null || getDeployApp().trim().length() == 0) {
                        throw new Exception("[SDA] Deploy Application is a required field if Deploy is selected!");
                    }
                    if (getDeployEnv() == null || getDeployEnv().trim().length() == 0) {
                        throw new Exception("[SDA] Deploy Environment is a required field if Deploy is selected!");
                    }
                    if (getDeployProc() == null || getDeployProc().trim().length() == 0) {
                        throw new Exception("[SDA] Deploy Process is a required field if Deploy is selected!");
                    }

                    listener.getLogger().println("[SDA] Starting deployment of " + getDeployApp() + " to " + getDeployEnv());
                    String deployJson = createApplicationProcessRequest(udSite, resolvedComponent, resolvedVersionName, jsonDeployProperties);
                    JSONObject deployObj = new JSONObject(deployJson);
                    String requestId = deployObj.getString("requestId");
                    deployURI = getSite().getUrl() + "/app#/application-process-request/" + requestId + "/log";
                    listener.getLogger().println("[SDA] Deployment request URI is: " + deployURI);

                    long startTime = new Date().getTime();
                    while (!checkDeploymentProcessStatus(udSite, requestId)) {
                        Thread.sleep(3000L);
                    }
                    if (this.deploymentResult != null)
                    {
                        long duration = (new Date().getTime() - startTime) / 1000L;
                        listener.getLogger().println("[SDA] Finished deployment of " + getDeployApp() + " to " + getDeployEnv() + " in " + duration + " seconds");

                        listener.getLogger().println("[SDA] The deployment " + this.deploymentResult + ". See the Serena DA logs for more details.");
                        if (("faulted".equalsIgnoreCase(this.deploymentResult)) || ("failed to start".equalsIgnoreCase(this.deploymentResult))) {
                            build.setResult(Result.UNSTABLE);
                        }
                    }
                }
            }
            catch (Throwable th) {
                th.printStackTrace(listener.error("[SDA] Failed to upload or deploy files:\n" + th));
                build.setResult(Result.UNSTABLE);
            }
        }

        // run a generic process
        if (isRunProcess()) {

            envMap = build.getEnvironment(listener);

            String resolvedProcessIf = resolveVariables(getProcessIf());
            String resolvedProcessProperties = resolveVariables(getProcessProps());

            // iterate over process properties to construct JSON string
            String jsonProcessProperties = "{";
            if (resolvedProcessProperties == null) {
                listener.getLogger().println("[SDA] Process properties: none defined");
            } else {
                BufferedReader bufReader = new BufferedReader(new StringReader(resolvedProcessProperties));
                String line;
                while ((line = bufReader.readLine()) != null) {
                    String[] parts = line.split("=");
                    String val = (parts.length == 1 ? "" : parts[1]); // do we have a value
                    listener.getLogger().println("[SDA] Process property: " + parts[0] + " = " + val);
                    jsonProcessProperties += ("\"" + parts[0] + "\": \"" + val + "\",");
                }
                // remove last comma if it exists
                if (jsonProcessProperties.endsWith(","))
                    jsonProcessProperties = jsonProcessProperties.substring(0, jsonProcessProperties.length() - 1);
            }
            jsonProcessProperties += "}";

            try {
                // work out if we should initiate a generic process
                Boolean processAfterUpload = false;
                // is runProcessIf override defined
                if (resolvedProcessIf == null || resolvedProcessIf.equals("")) {
                    // no, check deployVersion parameter
                    if (isRunProcess()) {
                        processAfterUpload = true;
                    } else {
                        processAfterUpload = false;
                    }
                } else {
                    // yes, check if it is set to true or yes
                    if (resolvedProcessIf.equals("false") || resolvedProcessIf.equals("no")) {
                        processAfterUpload = false;
                    }
                    if (resolvedProcessIf.equals("true") || resolvedProcessIf.equals("yes")) {
                        processAfterUpload = true;
                    }
                }
                listener.getLogger().println("[SDA] Run process after upload is " + processAfterUpload);

                if (processAfterUpload) {
                    if (getProcessName() == null || getProcessName().trim().length() == 0) {
                        throw new Exception("[SDA] Process Name is a required field if Run Process is selected!");
                    }
                    if (getResourceName() == null || getResourceName().trim().length() == 0) {
                        throw new Exception("[SDA] Resource Name is a required field if Run Process is selected!");
                    }

                    listener.getLogger().println("[SDA] Starting process " + getProcessName() + " on " + getResourceName());
                    String processJson = createGenericProcessRequest(udSite, processName, resourceName, jsonProcessProperties);
                    JSONObject processObj = new JSONObject(processJson);
                    String requestId = processObj.getString("id");
                    processURI = getSite().getUrl() + "/app#/process-request/" + requestId + "/log";
                    listener.getLogger().println("[SDA] Process request URI is: " + processURI);

                    long startTime = new Date().getTime();
                    while (!checkGenericProcessStatus(udSite, requestId)) {
                        Thread.sleep(3000L);
                    }
                    if (this.deploymentResult != null)
                    {
                        long duration = (new Date().getTime() - startTime) / 1000L;
                        listener.getLogger().println("[SDA] Finished process " + getProcessName() + " on " + getResourceName() + " in " + duration + " seconds");

                        listener.getLogger().println("[SDA] The process " + this.deploymentResult + ". See the Serena DA logs for more details.");
                        if (("faulted".equalsIgnoreCase(this.processResult)) || ("failed to start".equalsIgnoreCase(this.processResult))) {
                            build.setResult(Result.UNSTABLE);
                        }
                    }

                }
            } catch (Throwable th) {
                th.printStackTrace(listener.error("[SDA] Failed to run generic process" + th));
                build.setResult(Result.UNSTABLE);
            }
        }

        listener.getLogger().println("[SDA] Generating Deployment report...");
        String vName = null;
        String vURI = null;
        if (!isSkip()) {
            vName = resolveVariables(versionName);
            vURI = getSite().getUrl() + "/app#/version/" + task.getVersionId() + "/artifacts";
        }
        SerenaDAReport report = new SerenaDAReport(vName, vURI, getStatusName(), getDeployApp(), deployURI,
                deploymentResult, getProcessName(), processURI, processResult);
        report.setFileList(task.getFileList());
        SerenaDABuildAction buildAction = new SerenaDABuildAction(build, report);
        build.addAction(buildAction);

        return true;
    }

    private String createApplicationProcessRequest(UrbanDeploySite site, String componentName, String versionName,
                                                   String jsonProperties)
            throws Exception {

        URI uri = UriBuilder.fromPath(site.getUrl()).path("cli").path("applicationProcessRequest")
                .path("request").build();
        String json =
                "{\"application\":\"" + getDeployApp() +
                "\",\"applicationProcess\":\"" + getDeployProc() +
                "\",\"environment\":\"" + getDeployEnv() +
                "\",\"properties\":" + jsonProperties +
                ",\"versions\":[{\"version\":\"" + versionName +
                "\",\"component\":\"" + componentName + "\"}]}";

        return site.executeJSONPut(uri,json);
    }

    private String createGenericProcessRequest(UrbanDeploySite site, String processName, String resourceName,
                                        String jsonProperties)
            throws Exception {

        URI uri = UriBuilder.fromPath(site.getUrl()).path("rest").path("process").path("request").build();
        String json =
                "{\"processId\":\"" + site.getProcessId(processName) +
                "\",\"resource\":\"" + site.getResourceId(resourceName) +
                "\",\"properties\":" + jsonProperties + "}";
        return site.executeJSONPost(uri, json);
    }

    private boolean checkDeploymentProcessStatus(UrbanDeploySite site, String requestId)
            throws Exception
    {
        boolean processFinished = false;

        URI uri = UriBuilder.fromPath(site.getUrl()).path("rest").path("deploy").path("applicationProcessRequest").path(requestId).build();
        String requestResult = site.executeJSONGet(uri);
        if (requestResult != null)
        {
            JSONObject rootTrace = new JSONObject(requestResult).optJSONObject("rootTrace");
            if (rootTrace != null) {
                String executionStatus = (String) rootTrace.get("state");
                String executionResult = (String) rootTrace.get("result");
                if ((executionStatus == null) || ("".equals(executionStatus))) {
                    this.deploymentResult = "FAULTED";
                    processFinished = true;
                } else if (("closed".equalsIgnoreCase(executionStatus)) || ("faulted".equalsIgnoreCase(executionStatus)) || ("faulted".equalsIgnoreCase(executionResult))) {
                    this.deploymentResult = executionResult;
                    processFinished = true;
                }
            }
        }
        return processFinished;
    }

    private boolean checkGenericProcessStatus(UrbanDeploySite site, String requestId)
            throws Exception
    {
        boolean processFinished = false;

        URI uri = UriBuilder.fromPath(site.getUrl()).path("rest").path("process").path("request").path(requestId).build();
        String requestResult = site.executeJSONGet(uri);
        if (requestResult != null)
        {
            JSONObject trace = new JSONObject(requestResult).optJSONObject("trace");
            if (trace != null) {
                String executionStatus = (String) trace.get("state");
                String executionResult = (String) trace.get("result");
                if ((executionStatus == null) || ("".equals(executionStatus))) {
                    this.processResult = "FAULTED";
                    processFinished = true;
                } else if (("closed".equalsIgnoreCase(executionStatus)) || ("faulted".equalsIgnoreCase(executionStatus)) || ("faulted".equalsIgnoreCase(executionResult))) {
                    this.processResult = executionResult;
                    processFinished = true;
                }
            }
        }
        return processFinished;
    }

    private String resolveVariables(String input) {
        String result = input;
        if (input != null && input.trim().length() > 0) {
            Pattern pattern = Pattern.compile("\\$\\{[^}]*}");
            Matcher matcher = pattern.matcher(result);
            while (matcher.find()) {
                String key = result.substring(matcher.start() + 2, matcher.end() - 1);
                if (envMap.containsKey(key)) {
                    result = matcher.replaceFirst(Matcher.quoteReplacement(envMap.get(key)));
                    matcher.reset(result);
                }
            }
        }

        return result;
    }
}


