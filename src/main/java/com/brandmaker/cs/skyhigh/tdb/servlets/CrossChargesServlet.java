package com.brandmaker.cs.skyhigh.tdb.servlets;

import com.brandmaker.cs.skyhigh.tdb.config.Globals;
import com.brandmaker.cs.skyhigh.tdb.core.ProcessCrossCharges;
import com.brandmaker.cs.skyhigh.tdb.mail.MailService;
import com.brandmaker.cs.skyhigh.tdb.mail.MailServiceImpl;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

@MultipartConfig
public class CrossChargesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Log LOG = LogFactory.getLog(CrossChargesServlet.class);
    private ProcessCrossCharges processCrossCharges;

    public CrossChargesServlet() {super();}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        processCrossCharges = new ProcessCrossCharges();
        Map<String, String> data = new LinkedHashMap<>();

        String userMail = "";
        if (req.getPart("uMail") != null) {
            InputStream uMail = req.getPart("uMail").getInputStream();
            userMail = CharStreams.toString(new InputStreamReader(
                    uMail, Charsets.UTF_8));
        }

        if (req.getPart("multipartFile") != null) {

            Part filePart = req.getPart("multipartFile");
            InputStream fileContent = filePart.getInputStream();

            List processResponse = processCrossCharges.doProcess(fileContent);

            if ((Boolean)processResponse.get(0)) {
                resp.setStatus(200);
                data.put("message", "Cross Charges import finished.");
                if (processResponse.size() > 1) {
                    String rex = processResponse.get(1).toString();
                    data.put("message2", "Rejected rows No. : " + rex.substring(1, rex.length() - 1));
                    sendMail("Cross charges import incomplete", "Import of the name_of_the_file.csv incomplete.", "Skipped import at rows: "+ rex.substring(1, rex.length() - 1), userMail );
                }
                LOG.info("SERVLET - CC IMPORT FINISHED");
            } else {
                resp.setStatus(400);
                data.put("message", "Cross charges import failed.");
                data.put("message2", "Import failed due to the data format or the file layout.");
                sendMail("Cross charges import failed", "Cross charges import failed", "Import of the name_of_the_file.csv failed due to the data format or the file layout.", userMail );
                LOG.error("SERVLET - FILE FORMAT ERROR");
            }
        } else {
            resp.setStatus(404);
        }

        String json = new Gson().toJson(data);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);

    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.setContentType("text/html");
//        PrintWriter out = resp.getWriter();
//        out.append("CrossChargesServlet get method");
//        out.close();
//    }

    private void sendMail(String subject, String title, String paragraphs, String to ) {
        StringBuilder bodyMessage = new StringBuilder();
        bodyMessage.append(title);
        bodyMessage.append("\r\n");
        bodyMessage.append(paragraphs);
        String filename = "";
        String content = "";

        MailService mailService = new MailServiceImpl();
        mailService.sendMailAttachmentFromString(subject, bodyMessage.toString(), filename, content, to);
    }


}
