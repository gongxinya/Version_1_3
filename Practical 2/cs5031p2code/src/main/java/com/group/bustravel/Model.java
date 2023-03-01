package com.group.bustravel;

import com.group.exceptions.DuplicateNameException;
import com.group.exceptions.NoSuchNameException;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.*;

public class Model {

    private ArrayList<Route> routeList;
    private ArrayList<Stop> stopList;

    public Model() {
        routeList = new ArrayList<>();
        stopList = new ArrayList<>();
    }

    /**
     * Getters
     */

    public ArrayList<Route> getRouteList() {
        return routeList;
    }

    public ArrayList<Stop> getStopList() {
        return stopList;
    }

    /**
     * Adding routes & stops
     */
    public void addRoute(Route route) throws DuplicateNameException {
        // check duplicate name
        for (Route r : routeList) {
            if (r.getIdentifier().equals(route.getIdentifier())) {
                throw new DuplicateNameException();
            }
        }

        routeList.add(route);
    }

    public void addStop(Stop stop) throws DuplicateNameException {
        // check duplicate name
        for (Stop s : stopList) {
            if (s.getName().equals(stop.getName())) {
                throw new DuplicateNameException();
            }
        }

        stopList.add(stop);
    }

    public void removeRoute(String routeIdentifier) {
        for (Route r : routeList) {
            if (r.getIdentifier().equals(routeIdentifier)) {
                routeList.remove(r);
                break;
            }
        }
    }

    public void removeStop(String stopName) {
        for (Stop s : stopList) {
            if (s.getName().equals(stopName)) {
                stopList.remove(s);
                break;
            }
        }
    }

    /**
     * Get route/stop instances given a name
     */
    public Route getRoute(String name) throws NoSuchNameException {
        for (Route r : routeList) {
            if (r.getIdentifier().equals(name)) return r;
        }

        throw new NoSuchNameException();
    }

    public Stop getStop(String name) throws NoSuchNameException {
        for (Stop s : stopList) {
            if (s.getName().equals(name)) return s;
        }

        throw new NoSuchNameException();
    }

    /**
     * return routes given a stop
     */
    public ArrayList<Route> findAllRoutes(Stop stop) {
        ArrayList<Route> result = new ArrayList<>();

        for (Route r : routeList) {
            if (r.isPassing(stop)) {
                result.add(r);
            }
        }

        return result;
    }

    public ArrayList<Route> findAllRoutes(Stop stop, LocalTime stopTime) {
        ArrayList<Route> result = new ArrayList<>();

        for (Route r : routeList) {
            if (r.isPassing(stop, stopTime)) {
                result.add(r);
            }
        }

        return result;
    }

    /**
     * get time for a stop having services
     */
    public ArrayList<LocalTime> getServiceTime(Stop stop) {
        ArrayList<LocalTime> result = new ArrayList<>();

        for (Route r : routeList) {
            LocalTime stopTime = r.getStopTime(stop); // this returns null if the stop is not in the route
            result.add(stopTime);
        }

        // remove duplicated items
        Set<LocalTime> set = new HashSet<>(result);
        result.clear();
        result.addAll(set);

        // sort
        Collections.sort(result);

        return result;
    }
}
