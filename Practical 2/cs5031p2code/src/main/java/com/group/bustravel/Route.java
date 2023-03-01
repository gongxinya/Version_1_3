package com.group.bustravel;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import com.group.exceptions.DuplicateNameException;

public class Route {
    private String identifier;

    private ArrayList<Stop> stopList;
    private HashMap<String, LocalTime> timeTable;

    public Route(String identifier) {
        this.identifier = identifier;
        stopList = new ArrayList<>();
        timeTable = new HashMap<>();
    }

    public String getIdentifier() {
        return identifier;
    }

    public HashMap<String, LocalTime> getTimeTable() {
        return timeTable;
    }

    public ArrayList<Stop> getStopList() {
        return stopList;
    }

    public void add(Stop stop, LocalTime stopTime) throws DuplicateNameException {

        // check duplicate names
        for (Stop s : stopList) {
            if (s.getName().equals(stop.getName())) {
                throw new DuplicateNameException();
            }
        }

        timeTable.put(stop.getName(), stopTime);

        // sort the stop list
        for (int i = 0; i < stopList.size(); i++) {
            Stop s = stopList.get(i);
            if (timeTable.get(s.getName()).compareTo(stopTime) >= 0) {
                // add stop when all earlier stops are filtered
                stopList.add(i + 1, stop);
                break;
            }
        }
    }

    public void remove(String stopName) {
        for (Stop s : stopList) {
            if (s.getName().equals(stopName)) {
                stopList.remove(s);
                timeTable.remove(stopName);
                break;
            }
        }
    }

    public LocalTime getStopTime(Stop stop) {
        return timeTable.get(stop.getName());
    }

    public boolean isPassing(Stop stop) {
        for (Stop s : stopList) {
            if (s.getName().equals(stop.getName())) {
                return true;
            }
        }

        return false;
    }

    public boolean isPassing(String stopName) {
        for (Stop s : stopList) {
            if (s.getName().equals(stopName)) {
                return true;
            }
        }

        return false;
    }

    public boolean isPassing(Stop stop, LocalTime stopTime) {
        String stopName = stop.getName();
        for (Stop s : stopList) {
            if (s.getName().equals(stopName) && timeTable.get(stopName).equals(stopTime)) {
                return true;
            }
        }

        return false;
    }

    public boolean isPassing(String stopName, LocalTime stopTime) {
        for (Stop s : stopList) {
            if (s.getName().equals(stopName) && timeTable.get(stopName).equals(stopTime)) {
                return true;
            }
        }

        return false;
    }
}
