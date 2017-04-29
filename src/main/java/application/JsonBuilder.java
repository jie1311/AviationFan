package application;

import entities.Aircraft;
import entities.Airport;

import java.util.ArrayList;

public class JsonBuilder {

    public static String buildAirportsJsonArray(ArrayList<Airport> airports) {
        try {
            StringBuilder result = new StringBuilder("[");
            for (Airport airport : airports) {
                result.append(String.format("%s, ", airport.toString()));
            }
            result = new StringBuilder(result.substring(0, result.length() - 2));
            result.append("]");
            return result.toString();
        } catch (Exception e) {
            return "[]";
        }

    }

    public static String buildAircraftsJsonArray(ArrayList<Aircraft> aircrafts){
        try {
            StringBuilder result = new StringBuilder("[");
            for (Aircraft aircraft : aircrafts) {
                result.append(String.format("%s, ", aircraft.toString()));
            }
            result = new StringBuilder(result.substring(0, result.length() - 2));
            result.append("]");
            return result.toString();
        } catch (Exception e) {
            return "[]";
        }
    }

    public static String buildRouteJsonObject(Airport org, Airport des, boolean reachable, ArrayList<Airport> route) {
        try {
            StringBuilder result = new StringBuilder("{");
            result.append(String.format("\"origin\":%s, ", org.toString()));
            result.append(String.format("\"destination:\":%s, ", des.toString()));
            result.append(String.format("\"distance\":%d, ", Calculator.distance(org, des)));
            if (reachable) {
                result.append(String.format("\"reachable\":true, \"route\":["));
                for (int i = 0; i <= route.size() - 2; i++) {
                    Airport via = route.get(i);
                    result.append(String.format("%s, ", via.toString()));
                }
                result.append(String.format("%s]", des.toString()));
                result.append("}");
            } else {
                result.append("\"reachable\":false, \"route\":[]}");
            }

            return result.toString();
        } catch (Exception e) {
            return "{}";
        }
    }
}
