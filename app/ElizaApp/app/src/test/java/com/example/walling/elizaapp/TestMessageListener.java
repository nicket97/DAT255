package com.example.walling.elizaapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by walling on 2017-10-21.
 */

public class TestMessageListener {
    boolean success = false;
    IMessageListener listener = new IMessageListener() {
        @Override
        public void update(MessageData msgData) {
            success = true;
        }
    };

    @Test
    public void addListenerTest(){
        MessageListener.BUS.addListener(listener);
        listener.update(new MessageData(MessageData.MessageType.ALREADY_CONNECTED));
        assertTrue(success);
    }

}
