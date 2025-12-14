package ma.ensa.mailbox.service.impl;

import ma.ensa.mailbox.mapper.InboxMapper;
import ma.ensa.mailbox.mapper.OutboxMapper;
import ma.ensa.mailbox.model.Inbox;
import ma.ensa.mailbox.model.Outbox;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileStorageService {
    private static final String INBOX_DIR = "C:\\Users\\soufi\\Documents\\ENSA-GI-1ST\\OOP-JAVA\\ctrl2\\mail-box\\src\\main\\java\\ma\\ensa\\mailbox\\storage\\inbox\\";
    private static final String OUTBOX_DIR = "C:\\Users\\soufi\\Documents\\ENSA-GI-1ST\\OOP-JAVA\\ctrl2\\mail-box\\src\\main\\java\\ma\\ensa\\mailbox\\storage\\outbox\\";

    public static String saveInboxEmail(Inbox email) {
        try {
            Files.createDirectories(Paths.get(INBOX_DIR));
            String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm").format(new Date());

            String safeSubject = email.getSubject()
                    .replaceAll("[\\\\/:*?\"<>|]", "_");

            Path filePath = Paths.get(
                    INBOX_DIR,
                    safeSubject + "_" + timestamp + ".json"
            );

            String json = InboxMapper.toJson(email);

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write(json);
            }
//            System.out.println("json = " + json);

            return filePath.toAbsolutePath().toString();

        } catch (IOException e) {
            throw new RuntimeException("Failed to save inbox email", e);
        }
    }
    public static String saveOutboxEmail(Outbox email) {
        try {
            Files.createDirectories(Paths.get(OUTBOX_DIR));
            String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm").format(new Date());

            String safeSubject = email.getSubject()
                    .replaceAll("[\\\\/:*?\"<>|]", "_");

            Path filePath = Paths.get(
                    OUTBOX_DIR,
                    safeSubject + "_" + timestamp + ".json"
            );

            String json = OutboxMapper.toJson(email);

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write(json);
            }
            System.out.println("json = " + json);

            return filePath.toAbsolutePath().toString();

        } catch (IOException e) {
            throw new RuntimeException("Failed to save inbox email", e);
        }
    }
}
