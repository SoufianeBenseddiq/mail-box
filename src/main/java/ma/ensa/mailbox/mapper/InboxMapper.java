package ma.ensa.mailbox.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ma.ensa.mailbox.model.Inbox;

public class InboxMapper {
    static final GsonBuilder builder = new GsonBuilder();
    static final Gson gson = builder.create();

    public static Inbox fromJson(String json){
        return gson.fromJson(json, Inbox.class);
    }

    public static String toJson (Inbox inbox){
        return gson.toJson(inbox);
    }
}
