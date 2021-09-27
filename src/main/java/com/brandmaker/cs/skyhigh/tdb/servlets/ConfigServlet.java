package com.brandmaker.cs.skyhigh.tdb.servlets;


import com.brandmaker.cs.skyhigh.tdb.config.Globals;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Servlet implementation class ConfigServlet
 */
public class ConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(ConfigServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConfigServlet() {
		super();
	}

	/**
	 * @see ServletConfig (ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		LOG.info("ConfigServlet init");
	}

	/**
	 * Save properties data to config file
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSaved = true;
		Properties oldProperties = Globals.getProperties();
		try
		{

			Properties properties = new Properties();

			properties.setProperty("mail.server", request.getParameter("mail.server"));
			properties.setProperty("mail.username", request.getParameter("mail.username"));
			properties.setProperty("mail.password", request.getParameter("mail.password"));
			properties.setProperty("mail.smtp.auth", request.getParameter("mail.smtp.auth"));
			properties.setProperty("mail.smtp.starttls.enable", request.getParameter("mail.smtp.starttls.enable"));
			properties.setProperty("mail.smtp.port", request.getParameter("mail.smtp.port"));
			properties.setProperty("mail.recipients", request.getParameter("mail.recipients"));

			properties.setProperty("web.api.root", request.getParameter("web.api.root"));
			properties.setProperty("web.api.username", request.getParameter("web.api.username"));
			properties.setProperty("web.api.password", request.getParameter("web.api.password"));


			FileOutputStream out = new FileOutputStream(Globals.GetConfigPath());
			properties.store(out, "Cross Charges Importer config file");
			out.flush();

		}
		catch (Exception e) {
			isSaved = false;
			LOG.error(e.getMessage());
		}

		Globals.ReinitConfigFile();
		request.setAttribute("pageSaved", isSaved);

		RequestDispatcher dispatcher = request.getRequestDispatcher("ccConfig.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Get config page
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("ccConfig.jsp");
		dispatcher.forward(request, response);
	}

}