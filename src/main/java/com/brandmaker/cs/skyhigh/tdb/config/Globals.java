package com.brandmaker.cs.skyhigh.tdb.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class store settings from config.properties to variables
 *
 */
public class Globals {

    private static final Log LOG = LogFactory.getLog(Globals.class);
    private static final Properties properties = new Properties();
    private static final String ConfigPath = "/data/skyhigh/tdb-cross-charges/config.properties";
    private static final String version = "1.0.0";

    static {
        try {
            InitConfig();
        }
        catch ( IOException e) {
            e.printStackTrace();
        }
    }


    public static String GetConfigPath()
    {
        return ConfigPath;
    }

    public static String GetVersion()
    {
        return version;
    }

    /**
     * Reinitalization of config file
     *
     * @throws IOException IO Exception
     */
    public static void ReinitConfigFile() throws IOException
    {
        InitConfig();
    }

    /**
     * Load values from config.properties
     *
     * @throws IOException IO Exception
     */
    private static void InitConfig() throws  IOException {
        try {
            String propPath = ConfigPath;
            final FileInputStream propFileStream = new FileInputStream(propPath);
            properties.load(propFileStream);
        }
        catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static Properties getProperties(){
        return properties;
    }

    public static String MAIL_SERVER() { return properties.getProperty("mail.server"); }
    public static String MAIL_USERNAME() { return properties.getProperty("mail.username"); }
    public static String MAIL_PASSWORD() { return properties.getProperty("mail.password"); }
    public static Boolean MAIL_SMTP_AUTH() { return Boolean.parseBoolean(properties.getProperty("mail.smtp.auth")); }
    public static Boolean MAIL_SMTP_STARTTLS_ENABLE() { return Boolean.parseBoolean(properties.getProperty("mail.smtp.starttls.enable")); }
    public static Integer MAIL_SMTP_PORT() {
        try	{
            return Integer.parseInt(properties.getProperty("mail.smtp.port"));
        }
        catch (Exception exception){
            LOG.error("mail.smtp.port not defined in config file!");
            return null;
        }
    }
    public static String MAIL_RECIPIENTS() { return properties.getProperty("mail.recipients"); }

    public static String DB_HOST() { return properties.getProperty("db.host"); }
    public static Integer DB_PORT() {
        try	{
            return Integer.parseInt(properties.getProperty("db.port"));
        }
        catch (Exception exception){
            LOG.error("db.port not defined in config file!");
            return null;
        }
    }
    public static String DB_NAME() { return properties.getProperty("db.name"); }
    public static String DB_USER() { return properties.getProperty("db.user"); }
    public static String DB_PASSWORD() { return properties.getProperty("db.password"); }

    public static String WEB_API_ROOT() { return properties.getProperty("web.api.root"); }
    public static String WEB_API_USERNAME() { return properties.getProperty("web.api.username"); }
    public static String WEB_API_PASSWORD() { return properties.getProperty("web.api.password"); }

    public static final String DSE_API_PATH = "/dse/rest";
    public static final String DSE_API_VERSION = "v1.0";

    public static final String MAPS_API_PATH = "/maps/rest/api";
    public static final String MAPS_API_VERSION = "v3.4";
}

