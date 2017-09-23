package com.example.walling.elizaapp;

/**
 * Created by Olof Enström on 2017-09-23.
 */

public class MessageData {
    public enum MessageType {
        PORT_CLOSED,
        ALREADY_CONNECTED;
    }

    private MessageType msgType;

    public MessageData(MessageType messageType) {
        this.msgType = messageType;
    }

    public MessageType getMessageType() { return msgType; }
}
