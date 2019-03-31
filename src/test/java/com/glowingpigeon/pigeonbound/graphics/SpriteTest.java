package com.glowingpigeon.pigeonbound.graphics;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SpriteTest extends TestCase {
    /**
     * Create a new Sprite test
     * @param testName the name of the test
     */
    public SpriteTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new SpriteTest("testApp"));
        return suite;
    }

    public void testApp() {
        assertNotNull(getClass());
        assertNotNull(getClass().getClassLoader());
        assertNotNull(getClass().getClassLoader().getResource("."));
        System.out.println(getClass().getClassLoader().getResource("."));

        try {

            Sprite sprite = new Sprite("test/test.spr");
    
            for (int i = 0; i < 20; ++i) {
                sprite.tick();
            }
    
            assertTrue(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }
}