package com.brandmaker.cs.skyhigh.tdb.mail;

import com.brandmaker.cs.skyhigh.tdb.config.Globals;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class for sending mails
 */
public class MailServiceImpl implements MailService {

    private static final Log LOG = LogFactory.getLog(MailServiceImpl.class);

    /**
     * Method for sending mails
     *
     * @param subject      mail subject
     * @param bodyMessage  mail body
     * @param mimeBodyPart mimeBodyPart to attach in mail
     * @return status
     */
    private boolean sendMail(String subject, String bodyMessage, List<MimeBodyPart> mimeBodyPart) {
        InternetAddress[] recipients = null;
        try {
            recipients = InternetAddress.parse(Globals.MAIL_RECIPIENTS());
        } catch (AddressException e) {
            LOG.error(e.getMessage());
        }

        String from = Globals.MAIL_USERNAME();

        final String username = Globals.MAIL_USERNAME();
        final String password = Globals.MAIL_PASSWORD();


        Properties props = new Properties();
        props.put("mail.smtp.auth", Globals.MAIL_SMTP_AUTH());
        props.put("mail.smtp.starttls.enable", Globals.MAIL_SMTP_STARTTLS_ENABLE());
        props.put("mail.smtp.host", Globals.MAIL_SERVER());
        props.put("mail.smtp.port", Globals.MAIL_SMTP_PORT());

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, recipients);

            message.setSubject(subject);
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(bodyMessage);

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);

            for (MimeBodyPart mbp : mimeBodyPart)
                multipart.addBodyPart(mbp);

            message.setContent(multipart);
            Transport.send(message);

            LOG.info("Sent message successfully....");

            return true;

        } catch (MessagingException e) {
            LOG.error(e.getMessage());
        }

        return false;
    }


    /**
     * Send mail and attach file from string
     *
     * @param subject     mail subject
     * @param bodyMessage mail body
     * @param fileName    name for attachment
     * @param attachment  text that will be written to attachment file
     * @return status
     */
    @Override
    public boolean sendMailAttachmentFromString(String subject, String bodyMessage, String fileName, String attachment) {


        try {
            ArrayList<MimeBodyPart> mbp = new ArrayList<MimeBodyPart>();

            MimeBodyPart attachmentPart = new MimeBodyPart();

            DataSource ds = new ByteArrayDataSource(attachment.getBytes(StandardCharsets.UTF_8), "application/octet-stream");
            attachmentPart = new MimeBodyPart();
            attachmentPart.setDataHandler(new DataHandler(ds));

            attachmentPart.setFileName(fileName);

            mbp.add(attachmentPart);

            return sendMail(subject, bodyMessage, mbp);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return false;
    }

    /**
     * Send mail with files
     *
     * @param subject     mail subject
     * @param bodyMessage mail body
     * @param attachments list of files to attach in mail
     * @return status
     */
    @Override
    public boolean sendMailAttachment(String subject, String bodyMessage, List<File> attachments) {

        try {
            ArrayList<MimeBodyPart> mbp = new ArrayList<MimeBodyPart>();

            MimeBodyPart attachmentPart = new MimeBodyPart();

            for (File file : attachments) {
                DataSource source = new FileDataSource(file);
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(file.getName());
                mbp.add(attachmentPart);
            }

            return sendMail(subject, bodyMessage, mbp);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return false;
    }
}
