package basic_demo;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("appshould have agreeting", classUnderTest.getGreeting());
    }
}