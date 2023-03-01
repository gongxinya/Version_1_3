package com.group.bustravel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StopTests {
    private Stop stop;
    private String name;
    private String location;
    @BeforeEach
    void setup() {
        stop = new Stop(name, location);
        name = "testStop";
        location = "mysteriousPlace";
    }

    @Test
    void testName() {
        assertEquals(stop.getName(), name);
    }

    @Test
    void testLocation() {
        assertEquals(stop.getLocation(), location);
    }
}
