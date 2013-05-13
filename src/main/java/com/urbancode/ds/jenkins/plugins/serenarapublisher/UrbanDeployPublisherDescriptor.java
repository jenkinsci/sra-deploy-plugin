/* ===========================================================================
 *  Copyright (c) 2007 Serena Software. All rights reserved.
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
        return "Publish artifacts to Serena RA";
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
 
        Boolean skip = Boolean.valueOf("on".equalsIgnoreCase(req.getParameter("urbandeploypublisher.skip")));
        Boolean deploy = Boolean.valueOf("on".equalsIgnoreCase(req.getParameter("urbandeploypublisher.deploy")));
                
        String siteName = req.getParameter("urbandeploypublisher.siteName");
        
        if (siteName != null) {
            siteName = Util.fixNull(req.getParameter("urbandeploypublisher.siteName").trim());
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

        UrbanDeployPublisher notif = new UrbanDeployPublisher(siteName, component,
                                                              versionName, directoryOffset,
                                                              baseDir,
                                                              fileIncludePatterns,
                                                              fileExcludePatterns,
                                                              skip, deploy,
                                                              deployApp,
                                                              deployEnv,
                                                              deployProc);
        
        return notif;
    }

    public void doTestConnection(StaplerRequest req, StaplerResponse rsp, @QueryParameter("ud.url") final String url,
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
}