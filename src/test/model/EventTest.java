package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("List of all reviews was sorted.");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("List of all reviews was sorted.", e.getDescription());
        assertEquals(d.getDay(), e.getDate().getDay());
        assertEquals(d.getMonth(), e.getDate().getMonth());
        assertEquals(d.getYear(), e.getDate().getYear());
        assertEquals(d.getHours(), e.getDate().getHours());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "List of all reviews was sorted.", e.toString());
    }
}
