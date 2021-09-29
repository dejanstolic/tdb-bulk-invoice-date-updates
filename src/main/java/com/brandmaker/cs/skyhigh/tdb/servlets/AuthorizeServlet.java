package com.brandmaker.cs.skyhigh.tdb.servlets;

import com.brandmaker.cs.skyhigh.tdb.webapi.helpers.UserHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class RawPoDataServlet
 */
public class AuthorizeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Log LOG = LogFactory.getLog(AuthorizeServlet.class);


    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorizeServlet() {
        super();
    }

    public static Cookie getCookie(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        boolean isUserLoggedIn = false;
        UserHelper userHelper = new UserHelper();
        Cookie jSessionCookie = getCookie(request, "JSESSIONIDSSO");
        if (jSessionCookie != null) {
            String username = userHelper.getUsername(jSessionCookie);
            if (username != null) isUserLoggedIn = true;
        }

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(isUserLoggedIn);
        out.flush();
    }

}
