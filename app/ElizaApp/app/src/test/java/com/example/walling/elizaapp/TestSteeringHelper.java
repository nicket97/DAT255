package com.example.walling.elizaapp;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by walling on 2017-10-21.
 */

public class TestSteeringHelper {

    @Test
    public void getVelocityTest(){
        assertTrue(SteeringHelper.getInstance().getVelocity()==0);
        SteeringHelper.getInstance().setVelocity(20);
        assertTrue(SteeringHelper.getInstance().getVelocity()==20);
    }
    @Test
    public void commandStringTest(){
        assertTrue(SteeringHelper.getInstance().getCommandString().equals("V0000H0000"));
        SteeringHelper.getInstance().setVelocity(20);
        assertTrue(SteeringHelper.getInstance().getCommandString().equals("V0020H0000"));
    }
    @Test
    public void changeSteeringTest(){
        assertTrue(SteeringHelper.getInstance().getCommandString().equals("V0000H0000"));
        SteeringHelper.getInstance().changeDirection(-50);
        assertTrue(SteeringHelper.getInstance().getCommandString().equals("V0000H-050"));
    }
}
