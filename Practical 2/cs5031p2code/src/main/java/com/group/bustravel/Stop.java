package com.group.bustravel;

public class Stop {
    private String name;
    private String location;

    public Stop(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
