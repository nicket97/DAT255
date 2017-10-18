package com.example.walling.elizaapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olof Enström on 2017-09-23.
 */

public enum MessageListener {

    BUS;

    private List<IMessageListener> listeners = new ArrayList<>();

    /**
     * Add an object which listens to updates from the BUS.
     * @param listener A class (which must implement IMessageListener) that is to be added as a listener
     */
    public void addListener(IMessageListener listener) {
        listeners.add(listener);
    }

    /**
     * Send out the bus along with a message as an enum.
     * @param msgData The message that goes along with the bus, providing the information which is to be provided.
     */
    public void updateMessage(MessageData msgData) {
        try {
            for (IMessageListener messageUpdater : listeners) {
                messageUpdater.update(msgData);
            }
        } catch (Exception e) {

        }

    }
}
