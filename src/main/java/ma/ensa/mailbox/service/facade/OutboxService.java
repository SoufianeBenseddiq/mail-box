package ma.ensa.mailbox.service.facade;

import ma.ensa.mailbox.model.Outbox;

public interface OutboxService {
    public int sendMail(Outbox outbox);

    public void deleteAllMails();
}
