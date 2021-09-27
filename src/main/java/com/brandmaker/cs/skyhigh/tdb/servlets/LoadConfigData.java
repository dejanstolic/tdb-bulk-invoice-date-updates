package com.brandmaker.cs.skyhigh.tdb.servlets;

import com.brandmaker.cs.skyhigh.tdb.config.Globals;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Class for loading config data 
 */
public class LoadConfigData {

	private static final Log LOG = LogFactory.getLog(LoadConfigData.class);

	/**
	 * Get data from config file
	 * @return properties 
	 */
	public Properties getData()
	{
		try {
			String propPath = Globals.GetConfigPath();
			final FileInputStream propFileStream = new FileInputStream(propPath);
			Properties properties = new Properties();
			properties.load(propFileStream);
			return properties;
		}
		catch (FileNotFoundException e) {
			String message = "config.properties not found, message: " + e.getMessage();
			LOG.error(message);
		}
		catch (IOException e) {
			LOG.error(e.getMessage());
		}
		
		
		return null;
		
	}
}
