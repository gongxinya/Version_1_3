package com.group.bustravel;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @data 19:03 26/02/2023
 * @name Route
 * @introduce
 */
public class Route {
    String identifier;
    static private List<Stop> stopList;
    Route(String identifier){
        this.identifier = identifier;
    }

    public String getIdentifier(){
        return this.identifier;
    }

    public void add(Stop stop, LocalTime stopTime){

    }

    public List<Stop> getStopList(){
        return stopList;
    }

    public LocalTime getStopTime(Stop stop){
        LocalTime stopTime = null;
        return stopTime;
    }

    public boolean isPassing(Stop stop, LocalTime stopTime){ //??
        return true;
    }
}
