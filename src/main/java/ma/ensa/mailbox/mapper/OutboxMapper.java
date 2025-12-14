package ma.ensa.mailbox.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ma.ensa.mailbox.model.Outbox;

public class OutboxMapper {
    static final GsonBuilder builder = new GsonBuilder();
    static final Gson gson = builder.create();

    public static Outbox fromJson(String json){
        return gson.fromJson(json, Outbox.class);
    }

    public static String toJson (Outbox outbox){
        return gson.toJson(outbox);
    }
}
