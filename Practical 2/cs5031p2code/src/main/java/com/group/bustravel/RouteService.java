package com.group.bustravel;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @data 03:36 27/02/2023
 * @name RouteService
 * @introduce to handle the business logic of the routes and manage the routes and stops
 */
@Service
public class RouteService {
    private List<Route> routes = new ArrayList<>();
    private Map<String, Stop> stops = new HashMap<>();

    public List<Route> getAllRoutes() {
        return routes;
    }

    public void addRoute(Route route) {
        routes.add(route);
        for (Stop stop : route.getStopList()) {
            stops.put(stop.getName(), stop);
        }
    }

    public Route getRouteById(String id) {
        return routes.stream()
                .filter(route -> route.getIdentifier().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addStopOnRoute(String routeId, Stop stop) {
        Route route = getRouteById(routeId);
        if (route != null) {
            route.getStopList().add(stop);
        }
    }

    public List<Route> getRoutesServingStop(String stopName) {
        return routes.stream()
                .filter(route -> route.getStopList()
                        .stream()
                        .anyMatch(stop -> stop.getName().equals(stopName)))
                .collect(Collectors.toList());
    }

    public List<Route> getRoutesServingStopAtTime(String stopName, LocalTime time) {
        return routes.stream()
                .filter(route -> route.getStopList()
                        .stream()
                        .anyMatch(stop -> stop.getName().equals(stopName))
                        && route.getTimeTable().containsValue(time))
                .collect(Collectors.toList());
    }

    public List<LocalTime> getStopServiceTimes(String stopName) {
        List<LocalTime> serviceTimes = new ArrayList<>();
        for (Route route : routes) {
            for (Stop stop : route.getStopList()) {
                if (stop.getName().equals(stopName)) {
                    serviceTimes.addAll((Collection<? extends LocalTime>) route.getTimeTable());//...
                }
            }
        }
        return serviceTimes;
    }
}

