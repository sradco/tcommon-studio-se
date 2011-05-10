// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================

package org.talend.swtbot;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

/**
 * DOC sgandon class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40Z nrousseau $
 * 
 */
/**
 * DOC Administrator class global comment. Detailled comment
 */
/**
 * DOC Administrator class global comment. Detailled comment
 */
public class Utilities {

    /**
     * Define a enum for database type
     */
    public enum DbConnectionType {
        MYSQL,
        POSTGRESQL,
        MSSQL,
        ORACLE,
        AS400,
        SYBASE,
        JDBC_MYSQL,
        DB2,
        INFORMIX,
        ORACLE_OCI,
        TERADATA,
        VERTICA
    }

    /**
     * Define a enum for talend item type
     */
    public enum TalendItemType {
        BUSINESS_MODEL,
        JOB_DESIGNS,
        JOBLET_DESIGNS,
        CONTEXTS,
        ROUTINES,
        JOBSCRIPTS,
        SQL_TEMPLATES,
        DB_CONNECTIONS,
        SAP_CONNECTIONS,
        FILE_DELIMITED,
        FILE_POSITIONAL,
        FILE_REGEX,
        FILE_XML,
        FILE_EXCEL,
        FILE_LDIF,
        LDAP,
        SALESFORCE,
        GENERIC_SCHEMAS,
        TALEND_MDM,
        BRMS,
        EMBEDDED_RULES,
        COPYBOOK,
        WEB_SERVICE,
        VALIDATION_RULES,
        FTP,
        HL7,
        EDI,
        DOCUMENTATION,
        RECYCLE_BIN
    }

    //    private static final String BUNDLE_NAME = "connectionInfo"; //$NON-NLS-1$
    //
    // private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    private static SWTBotShell shell;

    private static final String SAMPLE_FOLDER_PATH = "/sample/"; //$NON-NLS-1$

    /**
     * return a File instance pointing to the file or folder located below this plugin sample folder
     * 
     * @param subSamplePath, path located below /sample/, so subpath should never start with a /, example : test.xsl
     * @return the File instance pointing to the obsolute path of the subSamplePath
     * @throws IOException
     * @throws URISyntaxException
     */
    public static File getFileFromCurrentPluginSampleFolder(String subSamplePath) throws IOException, URISyntaxException {
        URL url = Utilities.class.getResource(SAMPLE_FOLDER_PATH + subSamplePath);
        URI escapedURI = getFileUrl(url);
        return URIUtil.toFile(escapedURI);
    }

    /**
     * Convert any URL to a file URL
     * 
     * @param url, may be a eclipse URL
     * @return a file:/ url
     * @throws IOException
     * @throws URISyntaxException
     */
    public static URI getFileUrl(URL url) throws IOException, URISyntaxException {
        // bundleresource://875.fwk22949069:4/top400/
        URL unescapedURL = FileLocator.toFileURL(url);// bug 145096
        // file:/E:sv n/org.talend.model.migration.test/samples/top400/
        URI escapedURI = new URI(unescapedURL.getProtocol(), unescapedURL.getPath(), unescapedURL.getQuery());
        // file:/E:sv%20n/org.talend.model.migration.test/samples/top400/
        return escapedURI;
    }

    /**
     * Empty Recycle bin
     * 
     * @author fzhong
     * @param gefBot SWTGefBot
     * @param tree tree from repository
     * @return void
     */
    public static void emptyRecycleBin(SWTGefBot gefBot, SWTBotTree tree) {
        tree.select("Recycle bin").contextMenu("Empty recycle bin").click();
        gefBot.waitUntil(Conditions.shellIsActive("Empty recycle bin"));
        gefBot.button("Yes").click();
    }

    /**
     * Create item in repository
     * 
     * @author fzhong
     * @param itemType item type
     * @param itemName item name
     * @param tree tree in repository
     * @param gefBot SWTGefBot
     * @param shell creating wizard shell
     */
    private static void create(String itemType, String itemName, SWTBotTreeItem treeNode, SWTGefBot gefBot) {
        treeNode.contextMenu("Create " + itemType).click();

        if (!"JobScript".equals(itemType)) {
            gefBot.waitUntil(Conditions.shellIsActive("New " + itemType));
            shell = gefBot.shell("New " + itemType);
            shell.activate();
        }

        gefBot.textWithLabel("Name").setText(itemName);

        boolean finishButtonIsEnabled = gefBot.button("Finish").isEnabled();
        if (finishButtonIsEnabled) {
            gefBot.button("Finish").click();
        } else {
            shell.close();
            Assert.assertTrue("finish button is not enabled, " + itemType + " created fail. Maybe the item name is exist,",
                    finishButtonIsEnabled);
        }

        SWTBotTreeItem newTreeItem = null;
        try {
            if (gefBot.toolbarButtonWithTooltip("Save (Ctrl+S)").isEnabled()) {
                gefBot.toolbarButtonWithTooltip("Save (Ctrl+S)").click();
            }
            newTreeItem = treeNode.expand().select(itemName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull(itemType + " item is not created", newTreeItem);
        }
    }

    public static void createJoblet(String jobletName, SWTBotTreeItem treeNode, SWTGefBot gefBot) {
        create("Joblet", jobletName, treeNode, gefBot);
    }

    public static void createSqlTemplate(String sqlTemplateName, SWTBotTreeItem treeNode, SWTGefBot gefBot) {
        create("SQLTemplate", sqlTemplateName, treeNode, gefBot);
    }

    public static void createBusinessModel(String businessModelName, SWTBotTreeItem treeNode, SWTGefBot gefBot) {
        create("Business Model", businessModelName, treeNode, gefBot);
    }

    public static void createJob(String jobName, SWTBotTreeItem treeNode, SWTGefBot gefBot) {
        create("job", jobName, treeNode, gefBot);
    }

    public static void createRoutine(String routineName, SWTBotTreeItem treeNode, SWTGefBot gefBot) {
        create("routine", routineName, treeNode, gefBot);
    }

    public static void createJobScript(String jobScriptName, SWTBotTreeItem treeNode, SWTGefBot gefBot) {
        create("JobScript", jobScriptName, treeNode, gefBot);
    }

    private static void createFile(TalendItemType itemType, String contextMenu, final String shellTitle, String fileName,
            SWTBotTreeItem treeNode, final SWTGefBot gefBot) throws IOException, URISyntaxException {
        treeNode.contextMenu(contextMenu).click();
        gefBot.waitUntil(Conditions.shellIsActive(shellTitle));
        shell = gefBot.shell(shellTitle).activate();
        gefBot.textWithLabel("Name").setText(fileName);
        boolean nextButtonIsEnabled = gefBot.button("Next >").isEnabled();
        if (nextButtonIsEnabled) {
            gefBot.button("Next >").click();
        } else {
            shell.close();
            Assert.assertTrue("next button is not enabled, maybe the item name is exist,", nextButtonIsEnabled);
        }

        switch (itemType) {
        case FILE_DELIMITED:
            gefBot.textWithLabel("File").setText(
                    getFileFromCurrentPluginSampleFolder(System.getProperty("fileDelimited.filepath")).getAbsolutePath());
            gefBot.button("Next >").click();
            break;
        case FILE_EXCEL:
            gefBot.textWithLabel("File").setText(
                    getFileFromCurrentPluginSampleFolder(System.getProperty("fileExcel.filepath")).getAbsolutePath());
            gefBot.treeWithLabel("Set sheets parameters").getTreeItem("All sheets/DSelect sheet").check();
            gefBot.button("Next >").click();
            break;
        case FILE_LDIF:
            gefBot.textWithLabel("File").setText(
                    getFileFromCurrentPluginSampleFolder(System.getProperty("fileLdif.filepath")).getAbsolutePath());
            gefBot.button("Next >").click();
            for (int i = 0; i < 5; i++) {
                gefBot.tableInGroup("List Attributes of Ldif file").getTableItem(i).check();
            }
            break;
        case FILE_POSITIONAL:
            gefBot.textWithLabel("File").setText(
                    getFileFromCurrentPluginSampleFolder(System.getProperty("filePositional.filepath")).getAbsolutePath());
            gefBot.textWithLabel("Field Separator").setText("5,7,7,*");
            gefBot.textWithLabel("Marker position").setText("5,12,19");
            gefBot.button("Next >").click();
            break;
        case FILE_REGEX:
            gefBot.textWithLabel("File").setText(
                    getFileFromCurrentPluginSampleFolder(System.getProperty("fileRegex.filepath")).getAbsolutePath());
            gefBot.button("Next >").click();
            break;
        case FILE_XML:
            gefBot.button("Next >").click();
            gefBot.textWithLabel("XML").setText(
                    getFileFromCurrentPluginSampleFolder(System.getProperty("fileXml.filepath")).getAbsolutePath());
            gefBot.button("Next >").click();

            gefBot.tableInGroup("Target Schema", 0).click(0, 2);
            gefBot.text().setText(System.getProperty("filexml.loop"));
            for (int i = 0; i < 3; i++) {
                gefBot.buttonWithTooltip("Add").click();
                gefBot.tableInGroup("Target Schema", 1).click(i, 2);
                gefBot.text().setText("@" + System.getProperty("filexml.schema" + i));
                gefBot.tableInGroup("Target Schema", 1).click(i, 3);
                gefBot.text().setText(System.getProperty("filexml.schema" + i));
            }
            break;
        default:
            break;
        }

        gefBot.waitUntil(new DefaultCondition() {

            public boolean test() throws Exception {

                return gefBot.button("Next >").isEnabled();
            }

            public String getFailureMessage() {
                gefBot.shell(shellTitle).close();
                return "next button was never enabled";
            }
        }, 60000);
        gefBot.button("Next >").click();
        gefBot.waitUntil(new DefaultCondition() {

            public boolean test() throws Exception {

                return gefBot.button("Finish").isEnabled();
            }

            public String getFailureMessage() {
                gefBot.shell(shellTitle).close();
                return "finish button was never enabled";
            }
        });
        gefBot.button("Finish").click();

        SWTBotTreeItem newItem = null;
        try {
            newItem = treeNode.expand().select(fileName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("item is not created", newItem);
        }
    }

    public static void createFileDelimited(String fileName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) throws IOException,
            URISyntaxException {
        createFile(TalendItemType.FILE_DELIMITED, "Create file delimited", "New Delimited File", fileName, treeNode, gefBot);
    }

    public static void createFileExcel(String fileName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) throws IOException,
            URISyntaxException {
        createFile(TalendItemType.FILE_EXCEL, "Create file Excel", "New Excel File", fileName, treeNode, gefBot);
    }

    public static void createFileLdif(String fileName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) throws IOException,
            URISyntaxException {
        createFile(TalendItemType.FILE_LDIF, "Create file ldif", "New Ldif File", fileName, treeNode, gefBot);
    }

    public static void createFilePositional(String fileName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) throws IOException,
            URISyntaxException {
        createFile(TalendItemType.FILE_POSITIONAL, "Create file positional", "New Positional File", fileName, treeNode, gefBot);
    }

    public static void createFileRegex(String fileName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) throws IOException,
            URISyntaxException {
        createFile(TalendItemType.FILE_REGEX, "Create file regex", "New RegEx File", fileName, treeNode, gefBot);
    }

    public static void createFileXml(String fileName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) throws IOException,
            URISyntaxException {
        createFile(TalendItemType.FILE_XML, "Create file xml", "New Xml File", fileName, treeNode, gefBot);
    }

    public static void createLdap(String ldapName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) {
        treeNode.contextMenu("Create LDAP schema").click();
        gefBot.waitUntil(Conditions.shellIsActive("Create new LDAP schema"));
        shell = gefBot.shell("Create new LDAP schema").activate();

        /* step 1 of 5 */
        gefBot.textWithLabel("Name").setText(ldapName);
        boolean nextButtonIsEnabled = gefBot.button("Next >").isEnabled();
        if (nextButtonIsEnabled) {
            gefBot.button("Next >").click();
        } else {
            shell.close();
            Assert.assertTrue("next button is not enabled, maybe the item name is exist,", nextButtonIsEnabled);
        }
        /* step 2 of 5 */
        gefBot.comboBoxWithLabel("Hostname:").setText(System.getProperty("ldap.hostname"));
        gefBot.comboBoxWithLabel("Port:").setText(System.getProperty("ldap.port"));
        gefBot.button("Check Network Parameter").click();

        gefBot.waitUntil(Conditions.shellIsActive("Check Network Parameter"), 20000);
        shell = gefBot.shell("Check Network Parameter");
        gefBot.button("OK").click();
        gefBot.waitUntil(Conditions.shellCloses(shell));
        gefBot.waitUntil(new DefaultCondition() {

            public boolean test() throws Exception {
                return gefBot.button("Next >").isEnabled();
            }

            public String getFailureMessage() {
                gefBot.shell("Create new LDAP schema").close();
                return "connection failure";
            }
        });
        gefBot.button("Next >").click();

        /* step 3 of 5 */
        gefBot.comboBoxWithLabel("Bind DN or user:").setText(System.getProperty("ldap.dn_or_user"));
        gefBot.textWithLabel("Bind password:").setText(System.getProperty("ldap.password"));
        gefBot.button("Check Authentication").click();

        gefBot.waitUntil(Conditions.shellIsActive("Check Authentication Parameter"), 20000);
        shell = gefBot.shell("Check Authentication Parameter");
        gefBot.button("OK").click();
        gefBot.waitUntil(Conditions.shellCloses(shell));
        gefBot.button("Fetch Base DNs").click();

        gefBot.waitUntil(Conditions.shellIsActive("Fetch base DNs"));
        shell = gefBot.shell("Fetch base DNs");
        gefBot.button("OK").click();
        gefBot.waitUntil(Conditions.shellCloses(shell));
        gefBot.button("Next >").click();

        /* step 4 of 5 */
        for (int i = 0; i < 5; i++) {
            gefBot.tableInGroup("List attributes of LDAP Schema").getTableItem(i).check();
        }
        gefBot.button("Refresh Preview").click();
        gefBot.waitUntil(new DefaultCondition() {

            public boolean test() throws Exception {
                return gefBot.button("Next >").isEnabled();
            }

            public String getFailureMessage() {
                gefBot.shell("Create new LDAP schema").close();
                return "next button was never enabled";
            }
        }, 60000);
        gefBot.button("Next >").click();

        /* step 5 of 5 */
        gefBot.waitUntil(new DefaultCondition() {

            public boolean test() throws Exception {
                return gefBot.button("Finish").isEnabled();
            }

            public String getFailureMessage() {
                gefBot.shell("Create new LDAP schema").close();
                return "finish button was never enabled";
            }
        });
        gefBot.button("Finish").click();

        SWTBotTreeItem newLdapItem = null;
        try {
            newLdapItem = treeNode.expand().select(ldapName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("ldap item is not created", newLdapItem);
        }
    }

    public static void createSalesforce(String salesforceName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) {
        createSalesforce(null, salesforceName, treeNode, gefBot);
    }

    public static void createSalesforceWithHttpProxy(String salesforceName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) {
        createSalesforce("HTTP", salesforceName, treeNode, gefBot);
    }

    public static void createSalesforceWithSocksProxy(String salesforceName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) {
        createSalesforce("SOCKS", salesforceName, treeNode, gefBot);
    }

    /**
     * DOC fzhong Comment method "createSalesforce".
     * 
     * @param proxyType if use proxy set "HTTP" or "SOCKS", if not set null
     * @param salesforceName
     * @param treeNode
     * @param gefBot
     */
    private static void createSalesforce(String proxyType, String salesforceName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) {
        treeNode.contextMenu("Create Salesforce Connection").click();
        shell = gefBot.shell("New Salesforce ").activate();
        gefBot.waitUntil(Conditions.shellIsActive("New Salesforce "));

        /* step 1 of 4 */
        gefBot.textWithLabel("Name").setText(salesforceName);
        boolean nextButtonIsEnabled = gefBot.button("Next >").isEnabled();
        if (nextButtonIsEnabled) {
            gefBot.button("Next >").click();
        } else {
            shell.close();
            Assert.assertTrue("next button is not enabled, maybe the item name is exist,", nextButtonIsEnabled);
        }

        /* step 2 of 4 */
        gefBot.textWithLabel("User name").setText(System.getProperty("salesforce.username"));
        gefBot.textWithLabel("Password ").setText(System.getProperty("salesforce.password"));

        if ("HTTP".equals(proxyType)) {
            gefBot.checkBox("Enable Http proxy").click();
            gefBot.button("OK").click();
        } else if ("SOCKS".equals(proxyType)) {
            gefBot.checkBox("Enable Socks proxy").click();
        }
        if (proxyType != null) {
            gefBot.textWithLabel("Host").setText(System.getProperty("salesforce.proxy.host"));
            gefBot.textWithLabel("Port").setText(System.getProperty("salesforce.proxy.port"));
            gefBot.textWithLabel("Username").setText(System.getProperty("salesforce.proxy.username"));
            gefBot.textWithLabel("Password").setText(System.getProperty("salesforce.proxy.password"));
        }

        gefBot.button("Check login").click();

        gefBot.waitUntil(new DefaultCondition() {

            public boolean test() throws Exception {
                return gefBot.shell("Check Connection ").isActive();
            }

            public String getFailureMessage() {
                gefBot.shell("New Salesforce ").close();
                return "connection failure";
            }
        }, 30000);
        gefBot.button("OK").click();
        gefBot.button("Finish").click();

        SWTBotTreeItem newSalesforceItem = null;
        try {
            newSalesforceItem = treeNode.expand().getNode(salesforceName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("salesforce item is not created", newSalesforceItem);
        }
    }

    public static void createGenericSchema(String schemaName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) {
        treeNode.contextMenu("Create generic schema").click();
        gefBot.waitUntil(Conditions.shellIsActive("Create new generic schema"));
        shell = gefBot.shell("Create new generic schema").activate();

        /* step 1 of 2 */
        gefBot.textWithLabel("Name").setText(schemaName);
        boolean nextButtonIsEnabled = gefBot.button("Next >").isEnabled();
        if (nextButtonIsEnabled) {
            gefBot.button("Next >").click();
        } else {
            shell.close();
            Assert.assertTrue("next button is not enabled, maybe the item name is exist,", nextButtonIsEnabled);
        }

        /* step 2 of 2 */
        for (int i = 0; i < 3; i++) {
            gefBot.buttonWithTooltip("Add").click();
            gefBot.table().click(i, 2);
            gefBot.text().setText(System.getProperty("genericSchema.column" + i));
            gefBot.table().click(i, 4);
            gefBot.ccomboBox().setSelection(System.getProperty("genericSchema.type" + i));
        }

        gefBot.button("Finish").click();

        SWTBotTreeItem newSchemaItem = null;
        try {
            newSchemaItem = treeNode.expand().select(schemaName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("generic schema item is not created", newSchemaItem);
        }
    }

    public static void createCopybook(String copybookNAME, SWTBotTreeItem treeNode, final SWTGefBot gefBot) throws IOException,
            URISyntaxException {
        treeNode.contextMenu("Create EBCDIC").click();
        gefBot.waitUntil(Conditions.shellIsActive("EBCDIC Connection"));
        shell = gefBot.shell("EBCDIC Connection").activate();

        /* step 1 of 3 */
        gefBot.textWithLabel("Name").setText(copybookNAME);
        boolean nextButtonIsEnabled = gefBot.button("Next >").isEnabled();
        if (nextButtonIsEnabled) {
            gefBot.button("Next >").click();
        } else {
            shell.close();
            Assert.assertTrue("next button is not enabled, maybe the item name is exist,", nextButtonIsEnabled);
        }

        /* step 2 of 3 */
        gefBot.textWithLabel("File").setText(
                getFileFromCurrentPluginSampleFolder(System.getProperty("copybook.filepath")).getAbsolutePath());
        gefBot.textWithLabel("Data file").setText(
                getFileFromCurrentPluginSampleFolder(System.getProperty("copybook.datafile")).getAbsolutePath());
        gefBot.button("Generate").click();
        gefBot.button("Next >").click();

        /* step 3 of 3 */
        gefBot.button("Finish").click();

        SWTBotTreeItem newCopybookItem = null;
        try {
            newCopybookItem = treeNode.select(copybookNAME + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("copybook item is not created", newCopybookItem);
        }
    }

    public static void createSapConnection(String sapName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) {
        treeNode.contextMenu("Create SAP connection").click();
        gefBot.waitUntil(Conditions.shellIsActive("SAP Connection"));
        shell = gefBot.shell("SAP Connection").activate();

        /* step 1 of 2 */
        gefBot.textWithLabel("Name").setText(sapName);
        boolean nextButtonIsEnabled = gefBot.button("Next >").isEnabled();
        if (nextButtonIsEnabled) {
            gefBot.button("Next >").click();
        } else {
            shell.close();
            Assert.assertTrue("next button is not enabled, maybe the item name is exist,", nextButtonIsEnabled);
        }

        /* step 2 of 2 */
        gefBot.textWithLabel("Client").setText(System.getProperty("sap.client"));
        gefBot.textWithLabel("Host").setText(System.getProperty("sap.host"));
        gefBot.textWithLabel("User").setText(System.getProperty("sap.user"));
        gefBot.textWithLabel("Password").setText(System.getProperty("sap.password"));
        gefBot.textWithLabel("System Number").setText(System.getProperty("sap.systemNumber"));
        gefBot.textWithLabel("Language").setText(System.getProperty("sap.language"));
        gefBot.button("Check").click();

        gefBot.waitUntil(new DefaultCondition() {

            public boolean test() throws Exception {
                return gefBot.shell("Check SAP Connection ").isActive();
            }

            public String getFailureMessage() {
                gefBot.shell("SAP Connection").close();
                return "connection failure";
            }
        });
        gefBot.button("OK").click();

        gefBot.button("Finish").click();

        SWTBotTreeItem newSapItem = null;
        try {
            newSapItem = treeNode.select(sapName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("SAP connection is not created", newSapItem);
        }
    }

    /**
     * DOC fzhong Comment method "createContext".
     * 
     * @param contextName
     * @param treeNode
     * @param gefBot
     */
    public static void createContext(String contextName, SWTBotTreeItem treeNode, SWTGefBot gefBot) {
        treeNode.contextMenu("Create context group").click();

        gefBot.waitUntil(Conditions.shellIsActive("Create / Edit a context group"));
        shell = gefBot.shell("Create / Edit a context group");
        shell.activate();

        gefBot.textWithLabel("Name").setText(contextName);
        boolean nextButtonIsEnabled = gefBot.button("Next >").isEnabled();
        if (nextButtonIsEnabled) {
            gefBot.button("Next >").click();
        } else {
            shell.close();
            Assert.assertTrue("next button is not enabled, maybe the item name is exist,", nextButtonIsEnabled);
        }

        SWTBotTreeItem treeItem;
        SWTBotTreeItemExt treeItemExt;
        for (int i = 0; i < 3; i++) {
            gefBot.button(0).click();
            treeItem = gefBot.tree(0).getTreeItem("new1").click();
            treeItemExt = new SWTBotTreeItemExt(treeItem);
            treeItemExt.click(0);
            gefBot.text("new1").setText(System.getProperty("context.variable" + i));
            treeItemExt.click(1);
            gefBot.ccomboBox("String").setSelection(System.getProperty("context.type" + i));
            treeItemExt.click(2);
        }

        gefBot.cTabItem("Values as tree").activate();

        for (int j = 0; j < 3; j++) {
            treeItem = gefBot.tree(0).expandNode(System.getProperty("context.variable" + j)).getNode(0).select().click();
            treeItemExt = new SWTBotTreeItemExt(treeItem);
            treeItemExt.click(4);
            gefBot.text().setText(System.getProperty("context.value" + j));
        }

        gefBot.button("Finish").click();
        SWTBotTreeItem newContextItem = null;
        try {
            newContextItem = treeNode.expand().select(contextName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("context is not created", newContextItem);
        }
    }

    /**
     * DOC fzhong Comment method "createWebService".
     * 
     * @param type 'simple' or 'advanced'
     * @param webServiceName
     * @param treeNode
     * @param gefBot
     */
    public static void createWebService(String type, String webServiceName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) {
        treeNode.contextMenu("Create WSDL schema").click();
        gefBot.waitUntil(Conditions.shellIsActive("Create new WSDL schema"));
        shell = gefBot.shell("Create new WSDL schema").activate();
        try {
            /* step 1 of 4 */
            gefBot.textWithLabel("Name").setText(webServiceName);
            boolean nextButtonIsEnabled = gefBot.button("Next >").isEnabled();
            if (nextButtonIsEnabled) {
                gefBot.button("Next >").click();
            } else {
                shell.close();
                Assert.assertTrue("next button is not enabled, maybe the item name is exist,", nextButtonIsEnabled);
            }

            if ("simple".equals(type)) {
                /* step 2 of 4 */
                gefBot.button("Next >").click();

                /* step 3 of 4 */
                gefBot.textWithLabel("WSDL").setText(System.getProperty("webService.url"));
                gefBot.textWithLabel("Method").setText(System.getProperty("webService.method"));
                gefBot.button("Add ").click();
                gefBot.button("Refresh Preview").click();
                gefBot.waitUntil(new DefaultCondition() {

                    public boolean test() throws Exception {

                        return gefBot.button("Next >").isEnabled();
                    }

                    public String getFailureMessage() {
                        shell.close();
                        return "next button was never enabled";
                    }
                }, 60000);
                gefBot.button("Next >").click();
            } else if ("advanced".equals(type)) {
                /* step 2 of 4 */
                gefBot.radio("Advanced WebService").click();
                gefBot.button("Next >").click();

                /* step 3 of 4 */
                gefBot.button(1).click();// click refresh button
                gefBot.waitUntil(Conditions.shellCloses(gefBot.shell("Progress Information")), 30000);
                gefBot.tableWithLabel("Operation:").click(0, 0);

                // set input mapping
                // left schema
                gefBot.cTabItem(" Input mapping ").activate();
                gefBot.button("Schema Management").click();
                gefBot.shell("Schema").activate();
                gefBot.buttonWithTooltip("Add").click();
                gefBot.button("OK").click();
                // right schema
                gefBot.table(1).click(0, 1);
                gefBot.buttonWithTooltip("Add list element").click();
                gefBot.shell("ParameterTree").activate();
                gefBot.tree().select("City");
                gefBot.button("OK").click();

                gefBot.table(1).click(1, 0);
                gefBot.text().setText("input.newColumn");

                // set output mapping
                // left schema
                gefBot.cTabItem(" Output mapping ").activate();
                gefBot.table(0).click(0, 0);
                gefBot.buttonWithTooltip("Add List element").click();
                gefBot.shell("ParameterTree").activate();
                gefBot.tree().select("GetWeatherResult");
                gefBot.button("OK").click();
                // right schema
                gefBot.button("...").click();
                gefBot.buttonWithTooltip("Add").click();
                gefBot.button("OK").click();

                gefBot.table(1).click(0, 0);
                gefBot.text().setText("parameters.GetWeatherResult");

                gefBot.button("Next >").click();
            }

            /* step 4 of 4 */
            gefBot.waitUntil(new DefaultCondition() {

                public boolean test() throws Exception {

                    return gefBot.button("Finish").isEnabled();
                }

                public String getFailureMessage() {
                    shell.close();
                    return "finish button was never enabled";
                }
            });
            gefBot.button("Finish").click();
        } catch (Exception e) {
            shell.close();
            Assert.fail(e.getCause().getMessage());
        }

        SWTBotTreeItem newWebServiceItem = null;
        try {
            newWebServiceItem = treeNode.expand().select(webServiceName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("web service item is not created", newWebServiceItem);
        }
    }

    /**
     * DOC fzhong Comment method "createHL7".
     * 
     * @param type HL7 type, 'input' or 'output'
     * @param gefBot
     * @param treeNode
     * @param hl7Name
     */
    public static void createHL7(String type, SWTGefBot gefBot, SWTBotTreeItem treeNode, String hl7Name) {
        treeNode.contextMenu("Create HL7").click();
        shell = gefBot.shell("New HL7 File").activate();
        try {
            /* step 1 of 5 */
            gefBot.textWithLabel("Name").setText(hl7Name);
            boolean nextButtonIsEnabled = gefBot.button("Next >").isEnabled();
            if (nextButtonIsEnabled) {
                gefBot.button("Next >").click();
            } else {
                shell.close();
                Assert.assertTrue("next button is not enabled, maybe the item name is exist,", nextButtonIsEnabled);
            }

            if ("input".equals(type)) {
                /* step 2 of 5 */
                gefBot.button("Next >").click();

                /* step 3 of 5 */
                gefBot.textWithLabel("HL7 File path:").setText(
                        getFileFromCurrentPluginSampleFolder(System.getProperty("hl7.filepath")).getAbsolutePath());
                gefBot.button("Next >").click();

                /* step 4 of 5 */
                for (int i = 0; i < 3; i++) {
                    gefBot.buttonWithTooltip("Add").click();
                    gefBot.tableInGroup("Schema View").click(i, 3);
                    gefBot.text().setText(System.getProperty("hl7.column.MSH" + i));
                }
                gefBot.comboBoxWithLabel("Segment(As Schema)").setSelection("EVN");
                for (int j = 0; j < 2; j++) {
                    gefBot.buttonWithTooltip("Add").click();
                    gefBot.tableInGroup("Schema View").click(j, 3);
                    gefBot.text().setText(System.getProperty("hl7.column.SVN" + j));
                }
                gefBot.button("Next >").click();
            } else if ("output".equals(type)) {
                /* step 2 of 5 */
                gefBot.radio("HL7OutPut").click();
                gefBot.button("Next >").click();

                /* step 3 of 5 */
                gefBot.radio("Create from a file").click();
                gefBot.textWithLabel("HL7 File path:").setText(
                        getFileFromCurrentPluginSampleFolder(System.getProperty("hl7.filepath")).getAbsolutePath());
                gefBot.textWithLabel("Output File Path").setText(
                        getFileFromCurrentPluginSampleFolder(System.getProperty("hl7.outputFile")).getAbsolutePath());
                gefBot.button("Next >").click();

                /* step 4 of 5 */
                gefBot.button("Next >").click();
            }

            /* step 5 of 5 */
            gefBot.button("Finish").click();
        } catch (WidgetNotFoundException wnfe) {
            shell.close();
            Assert.fail(wnfe.getCause().getMessage());
        } catch (Exception e) {
            shell.close();
            Assert.fail(e.getMessage());
        }

        SWTBotTreeItem newHl7Item = null;
        try {
            newHl7Item = treeNode.expand().select(hl7Name + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("hl7 item is not created", newHl7Item);
        }
    }

    /**
     * Create db connection
     * 
     * @param gefBot SWTGefBot
     * @param tree SWTBotTree
     * @param dbType
     * @param dbName the name defined by user for db connection
     */
    public static void createDbConnection(final SWTGefBot gefBot, SWTBotTreeItem treeNode, DbConnectionType dbType, String dbName) {
        treeNode.contextMenu("Create connection").click();
        gefBot.waitUntil(Conditions.shellIsActive("Database Connection"));
        shell = gefBot.shell("Database Connection");
        gefBot.textWithLabel("Name").setText(dbName);
        boolean nextButtonIsEnabled = gefBot.button("Next >").isEnabled();
        if (nextButtonIsEnabled) {
            gefBot.button("Next >").click();
        } else {
            shell.close();
            Assert.assertTrue("next button is not enabled, maybe the item name is exist,", nextButtonIsEnabled);
        }
        String dbSelect = null;
        String dbProperty = null;
        switch (dbType) {
        case MYSQL:
            dbSelect = "MySQL";
            dbProperty = "mysql";
            break;
        case POSTGRESQL:
            dbSelect = "PostgreSQL";
            dbProperty = "postgresql";
            break;
        case MSSQL:
            dbSelect = "Microsoft SQL Server 2005/2008";
            dbProperty = "mssql";
            break;
        case ORACLE:
            dbSelect = "Oracle with SID";
            dbProperty = "oracle";
            break;
        case AS400:
            dbSelect = "AS400";
            dbProperty = "as400";
            break;
        case SYBASE:
            dbSelect = "Sybase (ASE and IQ)";
            dbProperty = "sybase";
            break;
        case JDBC_MYSQL:
            dbSelect = "General JDBC";
            dbProperty = "jdbc.mysql";
            break;
        case DB2:
            dbSelect = "IBM DB2";
            dbProperty = "db2";
            break;
        case INFORMIX:
            dbSelect = "Informix";
            dbProperty = "informix";
            break;
        case ORACLE_OCI:
            dbSelect = "Oracle OCI";
            dbProperty = "oracle.oci";
            break;
        case TERADATA:
            dbSelect = "Teradata";
            dbProperty = "teradata";
            break;
        case VERTICA:
            dbSelect = "Vertica";
            dbProperty = "vertica";
            break;
        default:
            break;
        }

        try {
            setConnectionInfo(gefBot, dbSelect, dbProperty);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }

        gefBot.button("Check").click();
        gefBot.waitUntil(new DefaultCondition() {

            public boolean test() throws Exception {
                return gefBot.shell("Check Connection ").isActive();
            }

            public String getFailureMessage() {
                shell.close();
                return "connection failure";
            }
        });

        try {
            if (gefBot.label(1).getText().equals("\"" + dbName + "\" connection successful.")) {
                gefBot.button("OK").click();
                gefBot.button("Finish").click();
            }
            gefBot.waitUntil(Conditions.shellCloses(shell));
        } catch (Exception e) {
            shell.close();
            Assert.assertTrue("connection created failure", false);
        }

        SWTBotTreeItem newDbItem = null;
        try {
            newDbItem = treeNode.expand().select(dbName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull(dbType.toString() + " connection is not created", newDbItem);
        }
    }

    /**
     * Set connection's informations
     * 
     * @param gefBot SWTGefBot
     * @param dbType db type for widget selection
     * @param db the prefix of properties about db connection
     * @throws URISyntaxException
     * @throws IOException
     */
    private static void setConnectionInfo(SWTGefBot gefBot, String dbType, String db) throws IOException, URISyntaxException {
        gefBot.comboBoxWithLabel("DB Type").setSelection(dbType);
        if (System.getProperty(db + ".dbVersion") != null)
            gefBot.comboBoxWithLabel("Db Version").setSelection(System.getProperty(db + ".dbVersion"));
        if (System.getProperty(db + ".login") != null)
            gefBot.textWithLabel("Login").setText(System.getProperty(db + ".login"));
        if (System.getProperty(db + ".password") != null)
            gefBot.textWithLabel("Password").setText(System.getProperty(db + ".password"));
        if (System.getProperty(db + ".server") != null)
            gefBot.textWithLabel("Server").setText(System.getProperty(db + ".server"));
        if (System.getProperty(db + ".port") != null)
            gefBot.textWithLabel("Port").setText(System.getProperty(db + ".port"));
        if (System.getProperty(db + ".dataBase") != null)
            gefBot.textWithLabel("DataBase").setText(System.getProperty(db + ".dataBase"));
        if (System.getProperty(db + ".schema") != null)
            gefBot.textWithLabel("Schema").setText(System.getProperty(db + ".schema"));
        if (System.getProperty(db + ".serviceName") != null)
            gefBot.textWithLabel("Local service name").setText(System.getProperty(db + ".serviceName"));
        if (System.getProperty(db + ".instance") != null)
            gefBot.textWithLabel("Instance").setText(System.getProperty(db + ".instance"));
        if (System.getProperty(db + ".url") != null)
            gefBot.textWithLabel("JDBC URL").setText(System.getProperty(db + ".url"));
        if (System.getProperty(db + ".driver") != null)
            gefBot.textWithLabel("Driver jar").setText(
                    getFileFromCurrentPluginSampleFolder(System.getProperty(db + ".driver")).getAbsolutePath());
        if (System.getProperty(db + ".className") != null)
            gefBot.comboBoxWithLabel("Class name").setText(System.getProperty(db + ".className"));
        if (System.getProperty(db + ".userName") != null)
            gefBot.textWithLabel("User name ").setText(System.getProperty(db + ".userName"));
        if (System.getProperty(db + ".sid") != null)
            gefBot.textWithLabel("Sid").setText(System.getProperty(db + ".sid"));
        if (System.getProperty(db + ".additionalParameters") != null)
            gefBot.textWithLabel("Additional parameters").setText(System.getProperty(db + ".additionalParameters"));
    }

    /**
     * DOC fzhong Comment method "copyAndPaste". Copy and paste the item(itemName + itemVersion) under tree node
     * (treeNode)
     * 
     * @param treeNode tree item node
     * @param itemName item name
     * @param itemVersion item version
     */
    public static void copyAndPaste(SWTBotTreeItem treeNode, String itemName, String itemVersion) {
        treeNode.getNode(itemName + " " + itemVersion).contextMenu("Copy").click();
        treeNode.contextMenu("Paste").click();

        SWTBotTreeItem newItem = null;
        try {
            newItem = treeNode.select("Copy_of_" + itemName + " " + itemVersion);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("item is not copied", newItem);
        }
    }

    /**
     * DOC fzhong Comment method "delete". Delete the item(itemName + itemVersion) under tree node(treeNode) of
     * tree(tree)
     * 
     * @param tree tree in repository
     * @param treeNode tree item node
     * @param itemName item name
     * @param itemVersion item version(if it is a folder, it doen't have version, set it as "null")
     * @param folderPath if no folder set it as "null", otherwise give the folder path(e.g. "a","a/b","a/b/c")
     */
    public static void delete(SWTBotTree tree, SWTBotTreeItem treeNode, String itemName, String itemVersion, String folderPath) {
        String nodeName = itemName;
        if (itemVersion != null)
            nodeName = nodeName + " " + itemVersion;
        treeNode.getNode(nodeName).contextMenu("Delete").click();

        SWTBotTreeItem newItem = null;
        try {
            if (folderPath == null)
                folderPath = "";
            newItem = tree.expandNode("Recycle bin").select(nodeName + " (" + folderPath + ")");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("item is not deleted to recycle bin", newItem);
        }
    }

    /**
     * DOC fzhong Comment method "duplicate". Duplicate the item(itemName + itemVersion) under tree node(treeNode) with
     * new name(newItemName)
     * 
     * @param gefBot SWTGefBot
     * @param treeNode tree item node
     * @param itemName item name
     * @param itemVersion item version
     * @param newItemName new item name
     */
    public static void duplicate(SWTGefBot gefBot, SWTBotTreeItem treeNode, String itemName, String itemVersion,
            String newItemName) {
        treeNode.getNode(itemName + " " + itemVersion).contextMenu("Duplicate").click();
        gefBot.shell("Please input new name ").activate();
        gefBot.textWithLabel("Input new name").setText(newItemName);
        gefBot.button("OK").click();

        SWTBotTreeItem newItem = null;
        try {
            newItem = treeNode.select(newItemName + " " + itemVersion);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("item is not duplicated", newItem);
        }
    }

    /**
     * DOC fzhong Comment method "getTalendItemNode". Get item node of the tree in repository
     * 
     * @param tree
     * @param itemType
     * @return a tree item
     */
    public static SWTBotTreeItem getTalendItemNode(SWTBotTree tree, TalendItemType itemType) {
        switch (itemType) {
        case BUSINESS_MODEL:
            return tree.expandNode("Business Models");
        case JOB_DESIGNS:
            return tree.expandNode("Job Designs");
        case JOBLET_DESIGNS:
            return tree.expandNode("Joblet Designs");
        case CONTEXTS:
            return tree.expandNode("Contexts");
        case ROUTINES:
            return tree.expandNode("Code", "Routines");
        case JOBSCRIPTS:
            return tree.expandNode("Code", "Job Scripts");
        case SQL_TEMPLATES:
            return tree.expandNode("SQL Templates");
        case DB_CONNECTIONS:
            return tree.expandNode("Metadata", "Db Connections");
        case SAP_CONNECTIONS:
            return tree.expandNode("Metadata", "SAP Connections");
        case FILE_DELIMITED:
            return tree.expandNode("Metadata", "File delimited");
        case FILE_POSITIONAL:
            return tree.expandNode("Metadata", "File positional");
        case FILE_REGEX:
            return tree.expandNode("Metadata", "File regex");
        case FILE_XML:
            return tree.expandNode("Metadata", "File xml");
        case FILE_EXCEL:
            return tree.expandNode("Metadata", "File Excel");
        case FILE_LDIF:
            return tree.expandNode("Metadata", "File ldif");
        case LDAP:
            return tree.expandNode("Metadata", "LDAP");
        case SALESFORCE:
            return tree.expandNode("Metadata", "Salesforce");
        case GENERIC_SCHEMAS:
            return tree.expandNode("Metadata", "Generic schemas");
        case TALEND_MDM:
            return tree.expandNode("Metadata", "Talend MDM");
        case BRMS:
            return tree.expandNode("Metadata", "Rules Management", "BRMS");
        case EMBEDDED_RULES:
            return tree.expandNode("Metadata", "Rules Management", "Embedded Rules");
        case COPYBOOK:
            return tree.expandNode("Metadata", "Copybook");
        case WEB_SERVICE:
            return tree.expandNode("Metadata", "Web Service");
        case VALIDATION_RULES:
            return tree.expandNode("Metadata", "Validation Rules");
        case FTP:
            return tree.expandNode("Metadata", "FTP");
        case HL7:
            return tree.expandNode("Metadata", "HL7");
        case EDI:
            return tree.expandNode("Metadata", "UN/EDIFACT");
        case DOCUMENTATION:
            return tree.expandNode("Documentation");
        case RECYCLE_BIN:
            return tree.expandNode("Recycle bin");
        default:
            break;
        }
        return null;
    }

    /**
     * DOC fzhong Comment method "getView".
     * 
     * @param gefBot
     * @param viewTitle
     * @return the widget of view
     */
    private static SWTBotView getView(SWTGefBot gefBot, String viewTitle) {
        return gefBot.viewByTitle(viewTitle);
    }

    /**
     * DOC fzhong Comment method "getRepositoryView". Get view by title "repository"
     * 
     * @param gefBot
     * @return view widget by title
     */
    public static SWTBotView getRepositoryView(SWTGefBot gefBot) {
        return getView(gefBot, "Repository");
    }

    /**
     * DOC fzhong Comment method "createFolder".
     * 
     * @param folderName
     * @param treeNode
     * @param gefBot
     */
    public static void createFolder(String folderName, SWTBotTreeItem treeNode, final SWTGefBot gefBot) {
        treeNode.contextMenu("Create folder").click();
        gefBot.waitUntil(Conditions.shellIsActive("New folder"));
        shell = gefBot.shell("New folder");
        shell.activate();

        gefBot.textWithLabel("Label").setText(folderName);

        boolean finishButtonIsEnabled = gefBot.button("Finish").isEnabled();
        if (finishButtonIsEnabled) {
            gefBot.button("Finish").click();
        } else {
            shell.close();
            Assert.assertTrue("finish button is not enabled, folder created fail, maybe the item is exist", finishButtonIsEnabled);
        }
        gefBot.waitUntil(Conditions.shellCloses(shell));

        SWTBotTreeItem newFolderItem = null;
        try {
            newFolderItem = treeNode.expand().select(folderName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("folder is not created", newFolderItem);
        }
    }

    /**
     * DOC fzhong Comment method "rename".
     * 
     * @param itemType TalendItemType
     * @param gefBot
     * @param treeNode
     * @param itemName
     * @param newItemName
     */
    private static void rename(TalendItemType itemType, SWTGefBot gefBot, SWTBotTreeItem treeNode, String itemName,
            String newItemName) {
        switch (itemType) {
        case CONTEXTS:
            treeNode.getNode(itemName + " 0.1").contextMenu("Edit context group").click();
            shell = gefBot.shell("Create / Edit a context group").activate();
            break;
        case DB_CONNECTIONS:
            treeNode.getNode(itemName + " 0.1").contextMenu("Edit connection").click();
            shell = gefBot.shell("Database Connection").activate();
            break;
        case SAP_CONNECTIONS:
            treeNode.getNode(itemName + " 0.1").contextMenu("Edit SAP connection").click();
            shell = gefBot.shell("SAP Connection").activate();
            break;
        case JOBLET_DESIGNS:
            treeNode.getNode(itemName + " 0.1").contextMenu("Edit Properties").click();
            shell = gefBot.shell("!!!PropertiesWizard.EditPropertiesPageTitle!!!").activate();
            break;
        default:
            treeNode.getNode(itemName + " 0.1").contextMenu("Edit properties").click();
            shell = gefBot.shell("Edit properties").activate();
            break;
        }

        gefBot.text(itemName).setText(newItemName);
        boolean finishButtonIsEnabled = gefBot.button("Finish").isEnabled();
        if (finishButtonIsEnabled) {
            gefBot.button("Finish").click();
        } else {
            shell.close();
            Assert.assertTrue("finish button is not enabled, maybe the item name is exist,", finishButtonIsEnabled);
        }

        SWTBotTreeItem newJobItem = null;
        try {
            newJobItem = treeNode.select(newItemName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("item is not renamed", newJobItem);
        }
    }

    public static void renameJob(SWTGefBot gefBot, SWTBotTreeItem treeNode, String jobName, String newJobName) {
        rename(TalendItemType.JOB_DESIGNS, gefBot, treeNode, jobName, newJobName);
    }

    public static void renameJoblet(SWTGefBot gefBot, SWTBotTreeItem treeNode, String jobName, String newJobName) {
        rename(TalendItemType.JOBLET_DESIGNS, gefBot, treeNode, jobName, newJobName);
    }

    /**
     * DOC fzhong Comment method "cleanUpRepository". Delete all items in repository to recycle bin.
     * 
     * @param tree tree in repository
     * @param buildType "TOS" or "TIS"
     */
    public static void cleanUpRepository(SWTBotTree tree, String buildType) {
        List<TalendItemType> itemList = null;
        for (TalendItemType itemType : TalendItemType.values()) {
            if ("TOS".equals(buildType)) {
                itemList = getTISItemTypes(); // if TOS, get TIS items and pass next step
            }
            if (itemList != null && itemList.contains(itemType))
                continue; // if TOS, pass TIS items
            SWTBotTreeItem treeNode = getTalendItemNode(tree, itemType);
            if (TalendItemType.SQL_TEMPLATES.equals(itemType))
                treeNode = treeNode.expandNode("Generic", "UserDefined"); // focus on specific sql template type
            if (TalendItemType.DOCUMENTATION.equals(itemType) || TalendItemType.RECYCLE_BIN.equals(itemType))
                continue; // undo with documentation and recycle bin
            for (String itemName : treeNode.getNodes()) {
                if (!"system".equals(itemName))
                    treeNode.getNode(itemName).contextMenu("Delete").click();
            }
        }
    }

    public static List<TalendItemType> getTISItemTypes() {
        List<TalendItemType> itemList = new ArrayList<TalendItemType>();
        itemList.add(TalendItemType.JOBLET_DESIGNS);
        itemList.add(TalendItemType.JOBSCRIPTS);
        itemList.add(TalendItemType.SAP_CONNECTIONS);
        itemList.add(TalendItemType.BRMS);
        itemList.add(TalendItemType.EMBEDDED_RULES);
        itemList.add(TalendItemType.COPYBOOK);
        itemList.add(TalendItemType.VALIDATION_RULES);
        itemList.add(TalendItemType.HL7);
        itemList.add(TalendItemType.EDI);
        return itemList;
    }

    /**
     * DOC fzhong Comment method "cleanUpRepository". Delete all items under the tree node to recycle bin.
     * 
     * @param treeNode treeNode of the tree in repository
     */
    public static void cleanUpRepository(SWTBotTreeItem treeNode) {
        for (String itemName : treeNode.getNodes()) {
            if (!"system".equals(itemName))
                treeNode.getNode(itemName).contextMenu("Delete").click();
        }
    }

    public static void createFTP(String ftpName, SWTGefBot gefBot, SWTBotTreeItem treeNode) {
        treeNode.contextMenu("Create FTP").click();

        gefBot.textWithLabel("Name").setText(ftpName);
        boolean isNextButonEnable = gefBot.button("Next >").isEnabled();
        if (!isNextButonEnable) {
            gefBot.button("Cancel").click();
            Assert.assertTrue("next button is not enable, maybe the item name already exist", isNextButonEnable);
        }
        gefBot.button("Next >").click();

        gefBot.textWithLabel("Username").setText(System.getProperty("ftp.username"));
        gefBot.textWithLabel("Password").setText(System.getProperty("ftp.password"));
        gefBot.textWithLabel("Host").setText(System.getProperty("ftp.host"));
        gefBot.textWithLabel("Port").setText(System.getProperty("ftp.port"));
        gefBot.button("Finish").click();

        SWTBotTreeItem newFTPItem = null;
        try {
            newFTPItem = treeNode.expand().select(ftpName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("ftp item is not created", newFTPItem);
        }
    }

    public static void createTalendMDM(SWTGefBot gefBot, SWTBotTreeItem treeNode, String mdmName) {
        treeNode.contextMenu("Create MDM Connection").click();

        gefBot.textWithLabel("Name").setText(mdmName);
        boolean isNextButtonEnable = gefBot.button("Next >").isEnabled();
        if (!isNextButtonEnable) {
            gefBot.button("Cancel").click();
            Assert.assertTrue("mdm item is not created, maybe the item name already exist", isNextButtonEnable);
        }
        gefBot.button("Next >").click();

        try {
            gefBot.textWithLabel("Username").setText(System.getProperty("mdm.username"));
            gefBot.textWithLabel("password").setText(System.getProperty("mdm.password"));
            gefBot.textWithLabel("Server").setText(System.getProperty("mdm.server"));
            gefBot.textWithLabel("Port").setText(System.getProperty("mdm.port"));
            gefBot.button("Check").click();
            // gefBot.button("OK").click();
            if (!gefBot.button("Next >").isEnabled()) {
                gefBot.button("Cancel").click();
                Assert.fail("connection unsuccessful");
            }
            gefBot.button("Next >").click();

            gefBot.comboBoxWithLabel("Version").setSelection(System.getProperty("mdm.version"));
            gefBot.comboBoxWithLabel("Data-model").setSelection(System.getProperty("mdm.dataModel"));
            gefBot.comboBoxWithLabel("Data-Container").setSelection(System.getProperty("mdm.dataContainer"));
            gefBot.button("Finish").click();
        } catch (WidgetNotFoundException wnfe) {
            shell.close();
            Assert.fail(wnfe.getCause().getMessage());
        } catch (Exception e) {
            shell.close();
            Assert.fail(e.getMessage());
        }

        SWTBotTreeItem newMDMItem = null;
        try {
            newMDMItem = treeNode.expand().select(mdmName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("mdm item is not created", newMDMItem);
        }
    }

    /**
     * DOC fzhong Comment method "createEmbeddedRules".
     * 
     * @param resourceType 'XLS' or 'DRL'
     * @param rulesName
     * @param gefBot
     * @param treeNode
     * @param rulesName
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void createEmbeddedRules(String resourceType, String ruleName, SWTGefBot gefBot, SWTBotTreeItem treeNode)
            throws IOException, URISyntaxException {
        treeNode.contextMenu("Create Rules").click();
        shell = gefBot.shell("New Rule ...").activate();
        gefBot.textWithLabel("Name").setText(ruleName);
        boolean isNextButtonEnable = gefBot.button("Next >").isEnabled();
        if (!isNextButtonEnable) {
            shell.close();
            Assert.assertTrue("rule item is not created, maybe the item name already exist", isNextButtonEnable);
        }
        gefBot.button("Next >").click();

        try {
            if ("XLS".equals(resourceType)) {
                gefBot.radio("select").click();
                gefBot.comboBoxWithLabel("Type of rule resource:").setSelection("New XLS (Excel)");
                gefBot.textWithLabel("DRL/XLS").setText(
                        getFileFromCurrentPluginSampleFolder("ExcelRulesTest.xls").getAbsolutePath());
                gefBot.button("Finish").click();
                // can't get download shell at this step
            } else if ("DRL".equals(resourceType)) {
                gefBot.radio("create").click();
                gefBot.comboBoxWithLabel("Type of rule resource:").setSelection("New DRL (rule package)");
                gefBot.button("Finish").click();
            }
        } catch (WidgetNotFoundException wnfe) {
            shell.close();
            Assert.fail(wnfe.getCause().getMessage());
        } catch (Exception e) {
            shell.close();
            Assert.fail(e.getMessage());
        }

        SWTBotShell errorShell = null;
        try {
            errorShell = gefBot.shell("Problem Executing Operation").activate();
            if (errorShell.isActive()) {
                gefBot.button("OK").click();
                gefBot.cTabItem(ruleName + " 0.1").close();
            }
        } catch (WidgetNotFoundException wnfe) {
            // ignor this exception, shell did not open means item create successfully.
        } finally {
            Assert.assertNull("bug for cannot create or restore resource because it already exist.", errorShell);
        }

        SWTBotTreeItem newRuleItem = null;
        try {
            newRuleItem = treeNode.expand().select(ruleName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("embedded rule item is not created", newRuleItem);
        }
    }

    /**
     * DOC fzhong Comment method "createValidationRules".
     * 
     * @param ruleType "Reference Check" or "Basic Value Check" or "Custom Check"
     * @param itemType metadata type
     * @param metadataName
     * @param ruleName
     * @param gefBot
     * @param treeNode
     */
    public static void createValidationRules(String ruleType, TalendItemType metadataType, String metadataName, String ruleName,
            SWTGefBot gefBot, SWTBotTreeItem treeNode) {
        treeNode.contextMenu("Create validation rule").click();
        shell = gefBot.shell("New Validation Rule").activate();
        gefBot.textWithLabel("Name").setText(ruleName);
        boolean isNextButtonEnable = gefBot.button("Next >").isEnabled();
        if (!isNextButtonEnable) {
            shell.close();
            Assert.assertTrue("rule item is not created, maybe the item name already exist", isNextButtonEnable);
        }
        gefBot.button("Next >").click();

        try {
            SWTBotTree tree = gefBot.tree();
            SWTBotTreeItem metadataNode = getTalendItemNode(tree, metadataType);
            if (TalendItemType.DB_CONNECTIONS.equals(metadataType)) {
                metadataNode.expandNode(metadataName + " 0.1", "Table schemas").select("test");
            }
            metadataNode.expandNode(metadataName + " 0.1").select("metadata");
            gefBot.button("Select All").click();
            gefBot.button("Next >").click();

            if ("Reference Check".equals(ruleType)) {
                gefBot.radio(0).click();
                gefBot.button("Next >").click();
                // problem - how to drag columns with SWTBot, wait...
            } else if ("Basic Value Check".equals(ruleType)) {
                gefBot.radio(1).click();
                gefBot.button("Next >").click();
                gefBot.buttonWithTooltip("Add").click();
                gefBot.tableWithLabel("Conditions").click(0, 2);
                gefBot.ccomboBox().setSelection("Column0");
                gefBot.tableWithLabel("Conditions").click(0, 3);
                gefBot.ccomboBox().setSelection("Empty");
                gefBot.tableWithLabel("Conditions").click(0, 4);
                gefBot.ccomboBox().setSelection("Greater");
                gefBot.tableWithLabel("Conditions").click(0, 5);
                gefBot.text().setText("50");
            } else if ("Custom Check".equals(ruleType)) {
                gefBot.radio(2).click();
                gefBot.button("Next >").click();
                gefBot.styledText().setText("true");
            }
            gefBot.button("Next >").click();
            gefBot.button("Finish").click();
        } catch (WidgetNotFoundException wnfe) {
            shell.close();
            Assert.fail(wnfe.getCause().getMessage());
        } catch (Exception e) {
            shell.close();
            Assert.fail(e.getMessage());
        }

        SWTBotTreeItem newRuleItem = null;
        try {
            newRuleItem = treeNode.expand().select(ruleName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("validation rule item is not created", newRuleItem);
        }
    }

    public static void renameFolder(SWTGefBot gefBot, SWTBotTreeItem treeNode, String folderName, String newFolderName) {
        treeNode.getNode(folderName).contextMenu("Rename folder").click();
        shell = gefBot.shell("New folder").activate();
        gefBot.textWithLabel("Label").setText(newFolderName);
        boolean isFinishButtonEnable = gefBot.button("Finish").isEnabled();
        if (!isFinishButtonEnable) {
            shell.close();
            Assert.assertTrue("folder name already exist", isFinishButtonEnable);
        }
        gefBot.button("Finish").click();

        SWTBotTreeItem newFolderItem = null;
        try {
            newFolderItem = treeNode.expand().select(newFolderName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("folder is renamed", newFolderItem);
        }
    }

    /**
     * DOC fzhong Comment method "executeSQL".
     * 
     * @param gefBot
     * @param dbItem the db item under node "DB Connection"
     * @param sql sql you want to execute
     */
    public static void executeSQL(SWTGefBot gefBot, SWTBotTreeItem dbItem, String sql) {
        long defaultTimeout = SWTBotPreferences.TIMEOUT;
        SWTBotPreferences.TIMEOUT = 100;
        if (sql != null) {
            try {
                dbItem.contextMenu("Edit queries").click();
                try {
                    if (gefBot.shell("Choose context").isActive())
                        gefBot.button("OK").click();
                } catch (WidgetNotFoundException wnfe) {
                    // ignor this, means it's not context mode, did not pop up context confirm dialog
                }
                shell = gefBot.shell("SQL Builder [Repository Mode]").activate();
                gefBot.styledText(0).setText(sql);
                gefBot.toolbarButtonWithTooltip("Execute SQL (Ctrl+Enter)").click();

                try {
                    if (gefBot.shell("Error Executing SQL").isActive())
                        gefBot.button("OK").click();
                    Assert.fail("execute sql fail");
                } catch (WidgetNotFoundException wnfe) {
                    // ignor this, means did not pop up error dialog, sql executed successfully.
                }
            } catch (WidgetNotFoundException wnfe) {
                Assert.fail(wnfe.getCause().getMessage());
            } catch (Exception e) {
                Assert.fail(e.getMessage());
            } finally {
                shell.close();
            }
        }
        SWTBotPreferences.TIMEOUT = defaultTimeout;
    }
}
