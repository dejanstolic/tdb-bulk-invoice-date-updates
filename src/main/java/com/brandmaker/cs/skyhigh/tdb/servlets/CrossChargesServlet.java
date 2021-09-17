package com.brandmaker.cs.skyhigh.tdb.servlets;

import com.brandmaker.cs.skyhigh.tdb.core.ProcessCrossCharges;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@MultipartConfig
public class CrossChargesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Log LOG = LogFactory.getLog(CrossChargesServlet.class);
    private ProcessCrossCharges processCrossCharges;

    public CrossChargesServlet() {super();}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        processCrossCharges = new ProcessCrossCharges();

        if (req.getPart("multipartFile") != null) {

            Part filePart = req.getPart("multipartFile");
            InputStream fileContent = filePart.getInputStream();

            if (processCrossCharges.doProcess(fileContent)) {
                resp.setStatus(200);
            } else {
                resp.setStatus(500);
            }
        } else {
            resp.setStatus(404);
        }



//        String message = processCrossCharges.doProcess();
//
//        req.setAttribute("message", message);
//        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
//        dispatcher.forward(req, resp);
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.setContentType("text/html");
//        PrintWriter out = resp.getWriter();
//        out.append("CrossChargesServlet get method");
//        out.close();
//    }


}
