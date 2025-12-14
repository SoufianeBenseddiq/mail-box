package ma.ensa.mailbox.service.facade;

import ma.ensa.mailbox.model.Inbox;

public interface InboxService {
    Inbox fetchLastEmail();
}