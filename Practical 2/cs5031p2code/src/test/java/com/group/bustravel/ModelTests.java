package com.group.bustravel;

import com.group.exceptions.DuplicateNameException;
import com.group.exceptions.NoSuchNameException;
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
    void testAddingRoute() throws DuplicateNameException {
        model.addRoute(route);
        assertEquals(model.getRouteList().size(), 1);
        //TODO: test file storage
    }

    @Test
    void testAddingStop() throws DuplicateNameException {
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
                model.addRoute(route);
                model.addRoute(route);
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
                model.addStop(stop);
                model.addStop(stop);
            }
        };

        assertThrows(DuplicateNameException.class, executable,
                "Expected DuplicateNameException.");
    }

    @Test
    void testRemoveRoute() throws DuplicateNameException {
        model.addRoute(route);
        model.removeRoute(routeIdentifier);
        assertEquals(model.getRouteList().size(), 0);
    }

    @Test
    void testRemoveStop() throws DuplicateNameException {
        model.addStop(stop);
        model.removeStop(stopName);
        assertEquals(model.getStopList().size(), 0);
    }

    @Test
    void testGetRouteValid() throws NoSuchNameException, DuplicateNameException {
        model.addRoute(route);
        assertEquals(model.getRoute(routeIdentifier), route);
    }

    @Test
    void testRouteInvalid() {
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                model.getRoute(routeIdentifier);
            }
        };

        assertThrows(NoSuchNameException.class, executable,
                "Expected NoSuchNameException.");
    }

    @Test
    void testGetStopValid() throws NoSuchNameException, DuplicateNameException {
        model.addStop(stop);
        assertEquals(model.getStop(stopName), stop);
    }

    @Test
    void testStopInvalid() {
        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                model.getStop(stopName);
            }
        };

        assertThrows(NoSuchNameException.class, executable,
                "Expected NoSuchNameException.");
    }

    /**
     * test listing routes that passing a stop
     */
    @Test
    void testListRoutesPassingStop() throws DuplicateNameException {
        model.addRoute(route);
        model.addRoute(route2);
        model.addStop(stop);
        route.add(stop, stopTime);
        assertTrue(model.findAllRoutes(stop).contains(route));
        assertFalse(model.findAllRoutes(stop).contains(route2));
    }

    /**
     * same test with time arguments
     */
    @Test
    void testListRoutesPassingStopWithTime() throws DuplicateNameException {
        model.addRoute(route);
        model.addStop(stop);
        route.add(stop, stopTime);
        assertTrue(model.findAllRoutes(stop, stopTime).contains(route));
        assertFalse(model.findAllRoutes(stop, stopTime.plus(30, ChronoUnit.MINUTES)).contains(route));
    }

    /**
     * test all serving times for a stop
     */
    @Test
    void testListAllStopServingTime() throws DuplicateNameException {
        model.addRoute(route);
        model.addRoute(route2);
        model.addStop(stop);
        route.add(stop, stopTime);
        route2.add(stop, stopTime.plus(30, ChronoUnit.MINUTES));
        // TODO: if duplicated time, only display 1 of them
        assertEquals(model.getServiceTime(stop).size(), 2);
    }
}
