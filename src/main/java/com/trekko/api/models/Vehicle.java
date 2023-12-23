package com.trekko.api.models;

public enum Vehicle {
    BYFOOT, BICYCLE, CAR, PUBLIC, SHIP, PLANE, OTHER;

    public static Vehicle from(final String vehicle) {
        switch (vehicle) {
            case "BYFOOT":
                return BYFOOT;
            case "BICYCLE":
                return BICYCLE;
            case "CAR":
                return CAR;
            case "PUBLIC":
                return PUBLIC;
            case "SHIP":
                return SHIP;
            case "PLANE":
                return PLANE;
            default:
                return OTHER;
        }
    }
}
