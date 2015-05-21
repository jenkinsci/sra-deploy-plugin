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

import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import hudson.tasks.Notifier;
import hudson.util.CopyOnWriteList;
import hudson.util.FormFieldValidator;
import hudson.util.FormValidation;
import hudson.Util;


import net.sf.json.JSONObject;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;

public class UrbanDeployPublisherDescriptor extends BuildStepDescriptor<Publisher> {
    /**
     * <p> This class holds the metadata for the UrbanDeployPublisher. </p>
     */
    private final CopyOnWriteList<UrbanDeploySite> sites = new CopyOnWriteList<UrbanDeploySite>();

    /**
     * The default constructor.
     */
    public UrbanDeployPublisherDescriptor() {
        super(UrbanDeployPublisher.class);
        load();
    }

    @Override
    public void load() {
        super.load();
    }

    /**
     * The name of the plugin to display them on the project configuration web page.
     * <p/>
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @see hudson.model.Descriptor#getDisplayName()
     */
    @Override
    public String getDisplayName() {
        return "Publish artifacts to Serena DA";
    }

    /**
     * Return the location of the help document for this publisher.
     * <p/>
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @see hudson.model.Descriptor#getHelpFile()
     */
    @Override
    public String getHelpFile() {
        return "/plugin/sra-deploy/help.html";
    }
    
    @Override
    public boolean isApplicable(Class<? extends AbstractProject> jobType) {
        return true;
    }


    /**
     * The getter of the sites field.
     *
     * @return the value of the sites field.
     */
    public UrbanDeploySite[] getSites() {
        Iterator<UrbanDeploySite> it = sites.iterator();
        int size = 0;
        while (it.hasNext()) {
            it.next();
            size++;
        }
        return sites.toArray(new UrbanDeploySite[size]);
    }

    /**
     * The getter of the site.
     *
     * @return the deploy site object.
     */
    public UrbanDeploySite getSiteByName(String siteName) {
        UrbanDeploySite urbanDeploySite = null;
        for (UrbanDeploySite site : sites) {
            if (siteName.equals(site.getDisplayName())) {
                urbanDeploySite = site;
                break;
            }
        }
        return urbanDeploySite;
    }

    /**
     * {@inheritDoc}
     *
     * @param req {@inheritDoc}
     * @return {@inheritDoc}
     * @see hudson.model.Descriptor#configure(org.kohsuke.stapler.StaplerRequest)
     */
    @Override
    public boolean configure(StaplerRequest req, JSONObject formData) {
        sites.replaceBy(req.bindParametersToList(UrbanDeploySite.class, "ud."));
        save();
        return true;
    }

    @Override
    public Notifier newInstance(StaplerRequest req, JSONObject formData)
            throws FormException {

        boolean useAnotherUser = "on".equals(req.getParameter("urbandeploypublisher.useAnotherUser"));
        boolean skip = "on".equals(req.getParameter("urbandeploypublisher.skip"));
        boolean deploy = "on".equals(req.getParameter("urbandeploypublisher.deploy"));
        boolean addStatus = "on".equals(req.getParameter("urbandeploypublisher.addStatus"));
        boolean runProcess = "on".equals(req.getParameter("urbandeploypublisher.runProcess"));

        String siteName = req.getParameter("urbandeploypublisher.siteName");
        
        if (siteName != null) {
            siteName = Util.fixNull(req.getParameter("urbandeploypublisher.siteName").trim());
        }

        String anotherUser = req.getParameter("urbandeploypublisher.anotherUser");

        if (anotherUser != null) {
            anotherUser = Util.fixNull(req.getParameter("urbandeploypublisher.anotherUser").trim());
        }

        String anotherPassword = req.getParameter("urbandeploypublisher.anotherPassword");

        if (anotherPassword != null) {
            anotherPassword = Util.fixNull(req.getParameter("urbandeploypublisher.anotherPassword").trim());
        }

        String component = req.getParameter("urbandeploypublisher.component");
        
        if (component != null) {
            component = Util.fixNull(req.getParameter("urbandeploypublisher.component").trim());
        }

        String versionName = req.getParameter("urbandeploypublisher.versionName");
        
        if (versionName != null) {
            versionName = Util.fixNull(req.getParameter("urbandeploypublisher.versionName").trim());
        }

        String directoryOffset = req.getParameter("urbandeploypublisher.directoryOffset");
        
        if (directoryOffset != null) {
            directoryOffset = Util.fixNull(req.getParameter("urbandeploypublisher.directoryOffset").trim());
        }

        String baseDir = req.getParameter("urbandeploypublisher.baseDir");
        
        if (baseDir != null) {
            baseDir = Util.fixNull(req.getParameter("urbandeploypublisher.baseDir").trim());
        }

        String fileIncludePatterns = req.getParameter("urbandeploypublisher.fileIncludePatterns");
        
        if (fileIncludePatterns != null) {
            fileIncludePatterns = Util.fixNull(req.getParameter("urbandeploypublisher.fileIncludePatterns").trim());
        }

        String fileExcludePatterns = req.getParameter("urbandeploypublisher.fileExcludePatterns");
        
        if (fileExcludePatterns != null) {
            fileExcludePatterns = Util.fixNull(req.getParameter("urbandeploypublisher.fileExcludePatterns").trim());
        }

        String versionProps = req.getParameter("urbandeploypublisher.versionProps");

        if (versionProps != null) {
            versionProps = Util.fixNull(req.getParameter("urbandeploypublisher.versionProps").trim());
        }

        String statusName = req.getParameter("urbandeploypublisher.statusName");

        if (statusName != null) {
            statusName = Util.fixNull(req.getParameter("urbandeploypublisher.statusName").trim());
        }

        String deployIf = req.getParameter("urbandeploypublisher.deployIf");

        if (deployIf != null) {
            deployIf = Util.fixNull(req.getParameter("urbandeploypublisher.deployIf").trim());
        }

        String deployApp = req.getParameter("urbandeploypublisher.deployApp");
        
        if (deployApp != null) {
            deployApp = Util.fixNull(req.getParameter("urbandeploypublisher.deployApp").trim());
        }
                
        String deployEnv = req.getParameter("urbandeploypublisher.deployEnv");
        
        if (deployEnv != null) {
            deployEnv = Util.fixNull(req.getParameter("urbandeploypublisher.deployEnv").trim());
        }

        String deployProc = req.getParameter("urbandeploypublisher.deployProc");
        
        if (deployProc != null) {
            deployProc = Util.fixNull(req.getParameter("urbandeploypublisher.deployProc").trim());
        }

        String deployProps = req.getParameter("urbandeploypublisher.deployProps");

        if (deployProps != null) {
            deployProps = Util.fixNull(req.getParameter("urbandeploypublisher.deployProps").trim());
        }

        String processIf = req.getParameter("urbandeploypublisher.processIf");

        if (processIf != null) {
            processIf = Util.fixNull(req.getParameter("urbandeploypublisher.processIf").trim());
        }

        String processName = req.getParameter("urbandeploypublisher.processName");

        if (processName != null) {
            processName = Util.fixNull(req.getParameter("urbandeploypublisher.processName").trim());
        }

        String resourceName = req.getParameter("urbandeploypublisher.resourceName");

        if (resourceName != null) {
            resourceName = Util.fixNull(req.getParameter("urbandeploypublisher.resourceName").trim());
        }

        String processProps = req.getParameter("urbandeploypublisher.processProps");

        if (processProps != null) {
            processProps = Util.fixNull(req.getParameter("urbandeploypublisher.processProps").trim());
        }

        UrbanDeployPublisher notif = new UrbanDeployPublisher(siteName, useAnotherUser, anotherUser, anotherPassword,
                                                              skip, component,
                                                              versionName, directoryOffset,
                                                              baseDir,
                                                              fileIncludePatterns,
                                                              fileExcludePatterns,
                                                              versionProps,
                                                              addStatus, statusName,
                                                              deploy, deployIf,
                                                              deployApp,
                                                              deployEnv,
                                                              deployProc,
                                                              deployProps,
                                                              runProcess, processIf,
                                                              processName,
                                                              resourceName,
                                                              processProps);
        
        return notif;
    }

    //
    // form field validation methods
    //

    public FormValidation doCheckComponentName(@QueryParameter String value,
                                               @QueryParameter("urbandeploypublisher.skip") final Boolean skip)
            throws IOException, ServletException {
        if (skip == false && value.length() == 0) {
            return FormValidation.error("Please enter a value for the Component Name");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckStatusName(@QueryParameter String value,
                                            @QueryParameter("urbandeploypublisher.addStatus") final Boolean addStatus)
            throws IOException, ServletException {
        if (addStatus == true && value.length() == 0) {
            return FormValidation.error("Please enter a value for the Version Status");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckApplicationName(@QueryParameter String value,
                                                 @QueryParameter("urbandeploypublisher.deploy") final Boolean deploy)
            throws IOException, ServletException {
        if (deploy == true && value.length() == 0) {
            return FormValidation.error("Please enter a value for the Application Name");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckEnvironmentName(@QueryParameter String value,
                                                 @QueryParameter("urbandeploypublisher.deploy") final Boolean deploy)
            throws IOException, ServletException {
        if (deploy == true && value.length() == 0) {
            return FormValidation.error("Please enter a value for the Environment Name");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckDeploymentProcessName(@QueryParameter String value,
                                                       @QueryParameter("urbandeploypublisher.deploy") final Boolean deploy)
            throws IOException, ServletException {
        if (deploy == true && value.length() == 0) {
            return FormValidation.error("Please enter a value for the Deployment Process Name");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckProcessName(@QueryParameter String value,
                                             @QueryParameter("urbandeploypublisher.runProcess") final Boolean runProcess)
            throws IOException, ServletException {
        if (runProcess == true && value.length() == 0) {
            return FormValidation.error("Please enter a value for the Process Name");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckResourceName(@QueryParameter String value,
                                              @QueryParameter("urbandeploypublisher.runProcess") final Boolean runProcess)
            throws IOException, ServletException {
        if (runProcess == true && value.length() == 0) {
            return FormValidation.error("Please enter a value for the Resource Name");
        }
        return FormValidation.ok();
    }

    //
    // data validation methods that communicate with SDA to check validity
    //

    public void doTestConnection(StaplerRequest req, StaplerResponse rsp,
                                 @QueryParameter("ud.url") final String url,
                                 @QueryParameter("ud.user") final String user,
                                 @QueryParameter("ud.password") final String password)
            throws IOException, ServletException {
        new FormFieldValidator(req, rsp, true) {
            protected void check()
                    throws IOException, ServletException {
                try {
                    UrbanDeploySite site = new UrbanDeploySite(null, url, user, password);
                    site.verifyConnection();
                    ok("Success");
                }
                catch (Exception e) {
                    error(e.getMessage());
                }
            }
        }.process();
    }

    public void doTestUserConnection(StaplerRequest req, StaplerResponse rsp,
                                 @QueryParameter("urbandeploypublisher.siteName") final String siteName,
                                 @QueryParameter("urbandeploypublisher.anotherUser") final String user,
                                 @QueryParameter("urbandeploypublisher.anotherPassword") final String password)
            throws IOException, ServletException {
        new FormFieldValidator(req, rsp, true) {
            protected void check()
                    throws IOException, ServletException {
                try {
                    UrbanDeploySite site = UrbanDeployPublisherDescriptor.this.getSiteByName(siteName);
                    site = new UrbanDeploySite(null, site.getUrl(), user, password);
                    site.verifyConnection();
                    ok("Success");
                }
                catch (Exception e) {
                    error(e.getMessage());
                }
            }
        }.process();
    }

    public void doTestVersionStatusExists(StaplerRequest req, StaplerResponse rsp,
                                      @QueryParameter("urbandeploypublisher.statusName") final String statusName,
                                      @QueryParameter("urbandeploypublisher.siteName") final String siteName)
            throws IOException, ServletException {
        new FormFieldValidator(req, rsp, true) {
            protected void check()
                    throws IOException, ServletException {
                try {
                    UrbanDeploySite site = UrbanDeployPublisherDescriptor.this.getSiteByName(siteName);
                    site.verifyStatusExists(statusName);
                    ok("Version Status \"" + statusName + "\" was found");
                } catch (Exception e) {
                    error("Version Status \"" + statusName + "\" was not found!");
                }
            }
        }.process();
    }

    public void doTestComponentExists(StaplerRequest req, StaplerResponse rsp,
                                      @QueryParameter("urbandeploypublisher.component") final String component,
                                      @QueryParameter("urbandeploypublisher.siteName") final String siteName)
    throws IOException, ServletException {
        new FormFieldValidator(req, rsp, true) {
            protected void check()
            throws IOException, ServletException {
                try {
                    UrbanDeploySite site = UrbanDeployPublisherDescriptor.this.getSiteByName(siteName);
                    site.verifyComponentExists(component);
                    ok("Component \"" + component + "\" was found");
                } catch (Exception e) {
                    error("Component \"" + component + "\" was not found!");
                }
            }
        }.process();
    }
    
    public void doTestApplicationExists(StaplerRequest req, StaplerResponse rsp,
                                        @QueryParameter("urbandeploypublisher.deployApp") final String application,
                                        @QueryParameter("urbandeploypublisher.siteName") final String siteName)
    throws IOException, ServletException {
        new FormFieldValidator(req, rsp, true) {
            protected void check()
            throws IOException, ServletException {
                try {
                    UrbanDeploySite site = UrbanDeployPublisherDescriptor.this.getSiteByName(siteName);
                    site.verifyApplicationExists(application);
                    ok("Application \"" + application + "\" was found");
                } catch (Exception e) {
                    error("Application \"" + application + "\" was not found!");
                }
            }
        }.process();
    }
    
    public void doTestEnvironmentExists(StaplerRequest req, StaplerResponse rsp,
                                        @QueryParameter("urbandeploypublisher.deployEnv") final String environment,
                                        @QueryParameter("urbandeploypublisher.deployApp") final String application,
                                        @QueryParameter("urbandeploypublisher.siteName") final String siteName)
    throws IOException, ServletException {
        new FormFieldValidator(req, rsp, true) {
            protected void check()
            throws IOException, ServletException {
                try {
                    UrbanDeploySite site = UrbanDeployPublisherDescriptor.this.getSiteByName(siteName);
                    site.verifyEnvironmentExists(environment, application);
                    ok("Environment \"" + environment + "\" for application \"" + application + "\" was found");
                } catch (Exception e) {
                    error("Environment \"" + environment + "\" for application \"" + application + "\" was not found");
                }
            }
        }.process();
    }
    
    public void doTestApplicationProcessExists(StaplerRequest req, StaplerResponse rsp,
                                               @QueryParameter("urbandeploypublisher.deployProc") final String applicationProcess,
                                               @QueryParameter("urbandeploypublisher.deployApp") final String application,
                                               @QueryParameter("urbandeploypublisher.siteName") final String siteName)
    throws IOException, ServletException {
        new FormFieldValidator(req, rsp, true) {
            protected void check()
            throws IOException, ServletException {
                try {
                    UrbanDeploySite site = UrbanDeployPublisherDescriptor.this.getSiteByName(siteName);
                    site.verifyApplicationProcessExists(applicationProcess, application);
                    ok("Process \"" + applicationProcess + "\" for application \"" + application + "\" was found");
                } catch (Exception e) {
                    error("Process \"" + applicationProcess + "\" for application \"" + application + "\" was not found");
                }
            }
        }.process();
    }

    public void doTestProcessExists(StaplerRequest req, StaplerResponse rsp,
                                   @QueryParameter("urbandeploypublisher.processName") final String processName,
                                   @QueryParameter("urbandeploypublisher.siteName") final String siteName)
            throws IOException, ServletException {
        new FormFieldValidator(req, rsp, true) {
            protected void check()
                    throws IOException, ServletException {
                try {
                    UrbanDeploySite site = UrbanDeployPublisherDescriptor.this.getSiteByName(siteName);
                    site.verifyProcessExists(processName);
                    ok("Process \"" + processName + "\" was found");
                } catch (Exception e) {
                    error("Process \"" + processName + "\" was not found");
                }
            }
        }.process();
    }

    public void doTestResourceExists(StaplerRequest req, StaplerResponse rsp,
                                    @QueryParameter("urbandeploypublisher.resourceName") final String resourceName,
                                    @QueryParameter("urbandeploypublisher.siteName") final String siteName)
            throws IOException, ServletException {
        new FormFieldValidator(req, rsp, true) {
            protected void check()
                    throws IOException, ServletException {
                try {
                    UrbanDeploySite site = UrbanDeployPublisherDescriptor.this.getSiteByName(siteName);
                    site.verifyResourceExists(resourceName);
                    ok("Resource \"" + resourceName + "\" was found");
                } catch (Exception e) {
                    error("Resource \"" + resourceName + "\" was not found");
                }
            }
        }.process();
    }
}