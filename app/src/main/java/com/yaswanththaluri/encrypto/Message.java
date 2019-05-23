package com.yaswanththaluri.encrypto;

import io.realm.RealmObject;

public class Message extends RealmObject
{

    private String messageType;
    private String messageEncrypted;
    private String date;
    private String messageDecrypted;


    public Message()
    {

    }

    public Message(String messageType, String messageEncrypted, String messageDecrypted, String date)
    {
        this.messageType = messageType;
        this.messageEncrypted = messageEncrypted;
        this.messageDecrypted = messageDecrypted;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessageEncrypted() {
        return messageEncrypted;
    }

    public void setMessageEncrypted(String messageEncrypted) {
        this.messageEncrypted = messageEncrypted;
    }

    public String getMessageDecrypted() {
        return messageDecrypted;
    }

    public void setMessageDecrypted(String messageDecrypted) {
        this.messageDecrypted = messageDecrypted;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    
}
