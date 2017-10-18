package com.example.walling.elizaapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestMessageData {
    @Test
    public void TestMessageData() throws Exception {

        MessageData dataTest = new MessageData(MessageData.MessageType.ALREADY_CONNECTED);

        assertTrue(dataTest.getMessageType().equals(MessageData.MessageType.ALREADY_CONNECTED));

    }
}