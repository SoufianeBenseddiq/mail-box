package ma.ensa.mailbox.repository.facade;

import ma.ensa.mailbox.model.Inbox;

import java.util.List;

public interface InboxDao {
    public int save(Inbox inbox);
    public List<Inbox> findAll();
    public int delete(Inbox inbox);


    void deleteAllMails();
}
