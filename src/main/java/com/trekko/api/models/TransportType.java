package com.trekko.api.models;

public enum TransportType {
    BY_FOOT, BICYCLE, CAR, PUBLIC_TRANSPORT, SHIP, PLANE, OTHER;

    public static TransportType from(final String transportType) {
        switch (transportType) {
            case "BY_FOOT":
                return BY_FOOT;
            case "BICYCLE":
                return BICYCLE;
            case "CAR":
                return CAR;
            case "PUBLIC_TRANSPORT":
                return PUBLIC_TRANSPORT;
            case "SHIP":
                return SHIP;
            case "PLANE":
                return PLANE;
            default:
                return OTHER;
        }
    }
}
