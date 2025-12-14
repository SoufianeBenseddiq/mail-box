package ma.ensa.mailbox.repository.facade;

import ma.ensa.mailbox.model.Inbox;
import ma.ensa.mailbox.model.Outbox;

public interface OutboxDao {
//    public int save(Inbox inbox);
//    public int delete(Inbox inbox);


    int sendMail(Outbox outbox);

    void deleteAllMails();
}
