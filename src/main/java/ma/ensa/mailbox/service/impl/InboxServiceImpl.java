package ma.ensa.mailbox.service.impl;

import ma.ensa.mailbox.config.EmailConfig;
import ma.ensa.mailbox.model.Inbox;
import ma.ensa.mailbox.repository.facade.InboxDao;
import ma.ensa.mailbox.repository.impl.InboxDaoImpl;
import ma.ensa.mailbox.service.facade.InboxService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.util.Date;
import java.util.Properties;

public class InboxServiceImpl implements InboxService {
    private final InboxDao inboxDao = new InboxDaoImpl();
    private final String username = EmailConfig.getUsername();
    private final String password = EmailConfig.getPassword();

    @Override
    public Inbox fetchLastEmail() {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imap.host", "imap.gmail.com");
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");

        Store store = null;
        Folder inbox = null;

        try {
            // Connect to Gmail
            Session session = Session.getInstance(props);
            store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);

            // Open INBOX
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            if (messages.length == 0) {
                System.out.println("No emails found");
                return null;
            }

            // Get the last message (most recent)
            Message lastMsg = messages[messages.length - 1];

            // Extract details
            String subject = lastMsg.getSubject() != null ? lastMsg.getSubject() : "(No Subject)";
            String sender = getFromAddress(lastMsg);
            String body = getTextFromMessage(lastMsg);
            Date receivedDate = lastMsg.getReceivedDate() != null ? lastMsg.getReceivedDate() : new Date();

            // Create and return Inbox object
            Inbox email = new Inbox();
            email.setSubject(subject);
            email.setSender(sender);
            email.setBody(body);

            System.out.println("Fetched last email: " + subject);
            // save to file
            FileStorageService.saveInboxEmail(email);
            // save in inbox database table
            inboxDao.save(email);

            return email;

        } catch (Exception e) {
            System.err.println("✗ Error fetching email: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            // Close connections
            try {
                if (inbox != null && inbox.isOpen()) inbox.close(false);
                if (store != null) store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    // Extract sender email
    private String getFromAddress(Message message) throws MessagingException {
        Address[] froms = message.getFrom();
        if (froms != null && froms.length > 0) {
            return ((InternetAddress) froms[0]).getAddress();
        }
        return "unknown@example.com";
    }

    // Extract email body
    private String getTextFromMessage(Message message) {
        try {
            Object content = message.getContent();

            if (content instanceof String) {
                return (String) content;
            } else if (content instanceof Multipart) {
                Multipart multipart = (Multipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    if (bodyPart.isMimeType("text/plain")) {
                        return bodyPart.getContent().toString();
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "Error reading email";
        }
    }
}