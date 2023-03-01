package com.group.bustravel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

/**
 * @data 03:52 27/02/2023
 * @name REST
 * @introduce To handle the incoming requests
 */
@RestController
public class BusSystemController {

    // Injecting the BusSystemService as a dependency
    @Autowired
    private RouteService routeService;

    @GetMapping("/routes")
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/routes/{id}")
    public Route getRouteById(@PathVariable String id) {
        return routeService.getRouteById(id);
    }

    // Endpoint for adding a new stop to a route
    @PostMapping("/routes/{id}/stops")
    public void addStopOnRoute(@PathVariable String id, @RequestBody Stop stop) {
        routeService.addStopOnRoute(id, stop);
    }

    @GetMapping("/stops/{name}/routes")
    public List<Route> getRoutesServingStop(@PathVariable String name) {
        return routeService.getRoutesServingStop(name);
    }

    @GetMapping("/stops/{name}/routes/{time}")
    public List<Route> getRoutesServingStopAtTime(@PathVariable String name, @PathVariable LocalTime time) {
        return routeService.getRoutesServingStopAtTime(name, time);
    }

    @GetMapping("/stops/{name}/service-times")
    public List<LocalTime> getStopServiceTimes(@PathVariable String name) {
        return routeService.getStopServiceTimes(name);
    }
}
