package com.brandmaker.cs.skyhigh.tdb.servlets;

import com.brandmaker.cs.skyhigh.tdb.core.ProcessCrossCharges;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CrossChargesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Log LOG = LogFactory.getLog(CrossChargesServlet.class);
    private ProcessCrossCharges processCrossCharges;

    public CrossChargesServlet() {super();}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        processCrossCharges = new ProcessCrossCharges();
        String message = processCrossCharges.doProcess();

        req.setAttribute("message", message);
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }


}
