package com.group.bustravel;

import com.group.exceptions.DuplicateNameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RouteServiceTest {

    private RouteService routeService;
    private DateTimeFormatter dtf;
    private LocalTime stopTime;

    @BeforeEach
    public void setup() throws DuplicateNameException {
        dtf = DateTimeFormatter.ofPattern("hh:mm");
        routeService = new RouteService();
        Route route1 = new Route("R001");
        route1.add(new Stop("Stop 1", "Location 1"), LocalTime.parse("11:00", dtf));
        route1.add(new Stop("Stop 2", "Location 2"), LocalTime.parse("12:00", dtf));
        route1.add(new Stop("Stop 3", "Location 3"), LocalTime.parse("13:00", dtf));
        route1.add(new Stop("Stop 4", "Location 4"), LocalTime.parse("14:00", dtf));
        route1.addTimeTable("Stop 1", LocalTime.of(8, 0));
        route1.addTimeTable("Stop 2", LocalTime.of(8, 10));
        route1.addTimeTable("Stop 3", LocalTime.of(8, 20));
        route1.addTimeTable("Stop 4", LocalTime.of(8, 30));
        routeService.addRoute(route1);

        Route route2 = new Route("R002", "Route 2", "Southbound");
        route2.addStop(new Stop("Stop 4", "Location 4"));
        route2.addStop(new Stop("Stop 3", "Location 3"));
        route2.addStop(new Stop("Stop 2", "Location 2"));
        route2.addStop(new Stop("Stop 1", "Location 1"));
        route2.addTimeTable("Stop 4", LocalTime.of(9, 0));
        route2.addTimeTable("Stop 3", LocalTime.of(9, 10));
        route2.addTimeTable("Stop 2", LocalTime.of(9, 20));
        route2.addTimeTable("Stop 1", LocalTime.of(9, 30));
        routeService.addRoute(route2);

        Stop stop1 = new Stop("Stop 1", "Location 1");
        Stop stop4 = new Stop("Stop 4", "Location 4");
        routeService.addStopOnRoute("R002", stop1);
        routeService.addStopOnRoute("R001", stop4);
    }

    @Test
    public void testGetAllRoutes() {
        List<Route> allRoutes = routeService.getAllRoutes();
        Assertions.assertEquals(2, allRoutes.size());
        Assertions.assertEquals("R001", allRoutes.get(0).getIdentifier());
        Assertions.assertEquals("Route 1", allRoutes.get(0).getName());
        Assertions.assertEquals("Northbound", allRoutes.get(0).getDirection());
        Assertions.assertEquals("R002", allRoutes.get(1).getIdentifier());
        Assertions.assertEquals("Route 2", allRoutes.get(1).getName());
        Assertions.assertEquals("Southbound", allRoutes.get(1).getDirection());
    }

    @Test
    public void testAddRoute() {
        Route route3 = new Route("R003", "Route 3", "Eastbound");
        route3.addStop(new Stop("Stop 5", "Location 5"));
        route3.addStop(new Stop("Stop 6", "Location 6"));
        route3.addTimeTable("Stop 5", LocalTime.of(10, 0));
        route3.addTimeTable("Stop 6", LocalTime.of(10, 10));
        routeService.addRoute(route3);

        List<Route> allRoutes = routeService.getAllRoutes();
        Assertions.assertEquals(3, allRoutes.size
