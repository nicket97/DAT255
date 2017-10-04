package com.example.walling.elizaapp.generalobserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olof Enström on 2017-09-23.
 */

public enum MessageListener {

    BUS;

    private List<IMessageListener> listeners = new ArrayList<>();

    public void addListener(IMessageListener listener) {
        listeners.add(listener);
    }

    public void updateMessage(MessageData msgData) {
        for (IMessageListener messageUpdater : listeners) {
            messageUpdater.update(msgData);
        }
    }
}
