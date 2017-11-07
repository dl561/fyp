package com.dl561.simulation.physics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class Vector2DTest {


    @InjectMocks
    private Vector2D underTest;

    @Test
    public void testXandYConversions() {
        int x = 10;
        int y = 10;
        underTest.setXAndY(x, y);
        assertEquals("X should equal the inputted x", x, underTest.getX(), 0.0001d);
        assertEquals("Y should equal the inputted y", y, underTest.getY(), 0.0001d);
    }

    @Test
    public void testMagnitudeAndDirection() {
        double magnitude = 3d;
        double direction = Math.PI;
        underTest.setDirection(direction);
        underTest.setMagnitude(magnitude);
        assertEquals("X should be -3", -3d, underTest.getX(), 0.0001d);
        assertEquals("Y should be 0ish", 0d, underTest.getY(), 0.0001d);
    }
}