package ma.ensa.mailbox.service.impl;

import ma.ensa.mailbox.config.EmailConfig;
import ma.ensa.mailbox.model.Outbox;
import ma.ensa.mailbox.repository.facade.OutboxDao;
import ma.ensa.mailbox.repository.impl.OutboxDaoImpl;
import ma.ensa.mailbox.service.facade.OutboxService;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class OutboxServiceImpl implements OutboxService {
    private final OutboxDao outboxDAO = new OutboxDaoImpl();
    private final String username = EmailConfig.getUsername();
    private final String password = EmailConfig.getPassword();
    private final String from = EmailConfig.getUsername();
    @Override
    public int sendMail(Outbox outbox) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(outbox.getReceiver()));
            message.setSubject(outbox.getSubject());
            message.setText(outbox.getBody());

            Transport.send(message);
            outboxDAO.sendMail(outbox);
            FileStorageService.saveOutboxEmail(outbox);
            System.out.println("Email sent successfully to: " + outbox.getReceiver());
        }catch (MessagingException e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    public void deleteAllMails() {
        outboxDao.deleteAllMails();
    }

    private final OutboxDao outboxDao = new OutboxDaoImpl();
}
