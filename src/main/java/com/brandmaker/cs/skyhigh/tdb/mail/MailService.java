package com.brandmaker.cs.skyhigh.tdb.mail;

import java.io.File;
import java.util.List;

/**
 * Interface for sending mails
 */
public interface MailService {
    /**
     *
     * Send mail and attach file from string
     *
     * @param subject mail subject
     * @param bodyMessage mail body
     * @param fileName name for attachment
     * @param attachment text that will be written to attachment file
     *
     * @return status
     */
    boolean sendMailAttachmentFromString(String subject, String bodyMessage, String fileName, String attachment, String to);

    /**
     * Send mail with files
     *
     * @param subject mail subject
     * @param bodyMessage  mail body
     * @param attachments list of files to attach in mail
     *
     * @return status
     */
    boolean sendMailAttachment(String subject, String bodyMessage, List<File> attachments);
}
