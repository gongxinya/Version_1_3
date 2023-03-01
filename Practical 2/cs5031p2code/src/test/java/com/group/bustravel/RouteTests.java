package com.group.bustravel;

import com.group.exceptions.DuplicateNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class RouteTests {
    private Route route;
    private Stop stop;
    private Stop stop2;
    private String identifier;
    private String stopName1;
    private String stopName2;
    private String location;
    private LocalTime stopTime;
    DateTimeFormatter dtf;
    @BeforeEach
    void setup() {
        identifier = "testRoute";
        stopName1 = "name1";
        stopName2 = "name2";
        location = "mysteriousLocation";
        route = new Route(identifier);
        stop = new Stop(stopName1, location);
        stop2 = new Stop(stopName2, location);
        dtf = DateTimeFormatter.ofPattern("hh:mm");
        stopTime = LocalTime.parse("11:00", dtf);
    }

    @Test
    void testIdentifier() {
        assertEquals(route.getIdentifier(), identifier);
    }

    @Test
    void testAddStop() throws DuplicateNameException {
        route.add(stop, stopTime);
        assertEquals(route.getStopList().size(), 1);
        assertEquals(route.getStopTime(stop), stopTime);
    }

    @Test
    void testRemoveStop() throws DuplicateNameException {
        route.add(stop, stopTime);
        route.remove(stopName1);
        assertEquals(route.getStopList().size(), 1);
    }

    @Test
    void testSameStopNames() {
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                route.add(stop, stopTime);
                route.add(stop, stopTime);
            }
        };

        assertThrows(DuplicateNameException.class, executable,
                "Expected DuplicateNameException.");
    }

//    /**
//     * test whether exceptions are thrown when timetable of a route is invalid. i.e. later stop's arriving time is
//     * earlier than former stops.
//     */
//    @Test
//    void testInvalidStopTime() {
//        Executable executable = new Executable() {
//            // Not sure, maybe sort the list to avoid this exception
//            @Override
//            public void execute() throws Throwable {
//                route.add(stop, stopTime);
//                route.add(new Stop(stopName2, location), stopTime.minus(5, ChronoUnit.MINUTES));
//            }
//        };
//
//        assertThrows(InvalidTimeException.class, executable,
//                "Expected InvalidTimeException.");
//    }

    /**
     * test when inserting a stop with earlier arriving time than the current latest stop
     */
    @Test
    void testInsertEarlierStop() throws DuplicateNameException {
        route.add(stop, stopTime);
        route.add(stop2, stopTime.minus(5, ChronoUnit.MINUTES));
        assertEquals(route.getStopList().size(), 2);
        assertEquals(route.getStopList().get(0).getName(), stopName2);
    }

    /**
     * test when a route passing through the stop
     */
    @Test
    void testPassingStopTrue() throws DuplicateNameException {
        route.add(stop, stopTime);
        assertTrue(route.isPassing(stop));
    }

    @Test
    void testPassingStopFalse() {
        assertFalse(route.isPassing(stop));
    }

    @Test
    void testPassingStopAndTimeTrue() throws DuplicateNameException {
        route.add(stop, stopTime);
        assertTrue(route.isPassing(stop, stopTime));
    }

    @Test
    void testPassingStopAndTimeFalse() throws DuplicateNameException {
        route.add(stop, stopTime);
        assertTrue(route.isPassing(stop, stopTime.plus(5, ChronoUnit.MINUTES)));
    }
}
