package com.group.bustravel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTests {
    private Model model;
    private Route route;
    private Route route2;
    private Stop stop;
    private String stopName;
    private String routeIdentifier;
    private String routeIdentifier2;
    private String location;
    DateTimeFormatter dtf;
    private LocalTime stopTime;
    @BeforeEach
    void setup() {
        model = new Model();
        stopName = "name";
        routeIdentifier = "iden";
        routeIdentifier2 = "iden2";
        location = "loca";
        route = new Route(routeIdentifier);
        route2 = new Route(routeIdentifier2);
        stop = new Stop(stopName, location);
        dtf = DateTimeFormatter.ofPattern("hh:mm");
        stopTime = LocalTime.parse("11:00", dtf);
    }

    @Test
    void testAddingRoute() {
        model.addRoute(route);
        assertEquals(model.getRouteList().size(), 1);
        //TODO: test file storage
    }

    @Test
    void testAddingStop() {
        model.addStop(stop);
        assertEquals(model.getStopList().size(), 1);
        //TODO: test file storage
    }

    //TODO: test loading from file

    @Test
    void testDuplicatedNameRoute() {
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                model.add(route);
                model.add(route);
            }
        };

        assertThrows(DuplicateNameException.class, executable,
                "Expected DuplicateNameException.");
    }

    @Test
    void testDuplicatedNameStop() {
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                model.add(stop);
                model.add(stop);
            }
        };

        assertThrows(DuplicateNameException.class, executable,
                "Expected DuplicateNameException.");
    }

    /**
     * test listing routes that passing a stop
     */
    @Test
    void testListRoutesPassingStop() {
        route.add(stop, stopTime);
        assertTrue(model.findAllRoutes(stop).contains(route));
        assertFalse(model.findAllRoutes(stop).contains(route2));
    }

    /**
     * same test with time arguments
     */
    @Test
    void testListRoutesPassingStopWithTime() {
        route.add(stop, stopTime);
        assertTrue(model.findAllRoutes(stop, stopTime).contains(route));
        assertFalse(model.findAllRoutes(stop, stopTime.plus(30, ChronoUnit.MINUTES)).contains(route));
    }

    /**
     * test all serving times for a stop
     */
    @Test
    void testListAllStopServingTime() {
        route.add(stop, stopTime);
        route2.add(stop, stopTime.plus(30, ChronoUnit.MINUTES));
        // TODO: if duplicated time, only display 1
        assertEquals(model.getServiceTime(stop).size(), 2);
    }
}
