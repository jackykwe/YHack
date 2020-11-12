package com.yhack.tutoree.database.model;
// package uk.ac.cam.jk810.yhack20.model;

import java.util.HashMap;
import java.util.Map;

public class Chat {

    private int chatid;
    private Map<Integer, Message> messages = new HashMap<>();

    public Chat(int chatid) {
        this.chatid = chatid;
    }

    public Chat(int chatid, Map<Integer, Message> messages) {
        this.chatid = chatid;
        this.messages = messages;
    }

    public int addMessage(Message m) {
        messages.put(messages.keySet().size() + 1, m);
        return messages.keySet().size();
    }

    public int getChatid() {
        return chatid;
    }

    public void setChatid(int chatid) {
        this.chatid = chatid;
    }

    public Map<Integer, Message> getMessages() {
        return messages;
    }

    public void setMessages(Map<Integer, Message> messages) {
        this.messages = messages;
    }

}
