package ma.ensa.mailbox.model;

import java.util.Date;

public class Inbox {
    private Long id;
    private String subject;
    private String sender;
    private String body;

    public Inbox() {
    }

    public Inbox(Long id, String subject, String sender, String body) {
        this.id = id;
        this.subject = subject;
        this.sender = sender;
        this.body = body;
    }

    public Inbox(String subject, String sender, String body) {
        this.subject = subject;
        this.sender = sender;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "Inbox{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}