package com.example.walling.elizaapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testDataSaver() throws Exception {
        DataSaver myDataSaver = new DataSaver(100);

        for (int i = 0; i <= 120; i++) {
            myDataSaver.addValue(i);
            System.out.println("Last val: " + myDataSaver.getLatestVal());
        }

        assertTrue(myDataSaver.getLatestVal() == 120);
    }
}