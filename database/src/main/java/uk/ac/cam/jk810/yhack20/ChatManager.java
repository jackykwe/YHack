package uk.ac.cam.jk810.yhack20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import uk.ac.cam.jk810.yhack20.model.Chat;
import uk.ac.cam.jk810.yhack20.model.Message;

/**
 * Manager for opening and reading from a chatfile.
 */
public class ChatManager implements AutoCloseable {

    private Map<Integer, Chat> chats = new HashMap<>();
    private String chatfile;
    private static SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Open and load a chatfile json.
     * @param chatfile
     * @throws Exception
     */
    public ChatManager(String chatfile) throws Exception {
        this.chatfile = chatfile;

        JSONParser parser = new JSONParser();

        try (BufferedReader reader = new BufferedReader(new FileReader(chatfile))) {
            JSONObject chatsJSONObject = (JSONObject) parser.parse(reader);

            for (Object chatidObj : chatsJSONObject.keySet()) {
                int chatid = Integer.parseInt((String) chatidObj);
                JSONObject chat = (JSONObject) chatsJSONObject.get(Integer.toString(chatid));

                Map<Integer, Message> messages = new HashMap<>();

                for (Object msgidObject : chat.keySet()) {
                    int msgid = Integer.parseInt((String) msgidObject);
                    JSONObject messageJSON = (JSONObject) chat.get(Integer.toString(msgid));
                    messages.put(msgid,
                            new Message((String) messageJSON.get("senderID"), (String) messageJSON.get("message"),
                                    timeformat.parse((String) messageJSON.get("timestamp"))));
                }
                this.chats.put(chatid, new Chat(chatid, messages));
            }
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
            throw new Exception("There was an error reading the chatfile");
        }

    }

    /**
     * Closes the chat manager and writes to the disk.
     */
    @Override
    public void close() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(this.chatfile));
        try {
            JSONObject fileObject = new JSONObject();

            for (int chatid : this.chats.keySet()) {
                JSONObject chatObject = new JSONObject();
                fileObject.put(Integer.toString(chatid), chatObject);
                Chat chat = this.chats.get(chatid);
                for (Entry<Integer, Message> message : chat.getMessages().entrySet()) {
                    JSONObject messageObject = new JSONObject();
                    chatObject.put(Integer.toString(message.getKey()), messageObject);

                    messageObject.put("senderID", message.getValue().getSender());
                    messageObject.put("message", message.getValue().getMessage());
                    messageObject.put("timestamp", timeformat.format(message.getValue().getTimestamp()));
                }
            }
            fileObject.writeJSONString(bw);
        } finally {
            bw.flush();
            bw.close();
        }
    }

    public Map<Integer, Chat> getChats() {
        return chats;
    }

    public String getChatfile() {
        return chatfile;
    }

    public void setChatfile(String chatfile) {
        this.chatfile = chatfile;
    }

    public static SimpleDateFormat getTimeformat() {
        return timeformat;
    }

    public static void setTimeformat(SimpleDateFormat timeformat) {
        ChatManager.timeformat = timeformat;
    }
}
