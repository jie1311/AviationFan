package application;

import entities.Aircraft;
import entities.Airport;

import java.util.ArrayList;

public class JsonBuilder {

    public static String buildAirportJson(ArrayList<Airport> airports) {
        StringBuilder result = new StringBuilder("[");
        for (Airport airport : airports) {
            result.append(String.format("%s, ", airport.toString()));
        }
        result = new StringBuilder(result.substring(0, result.length() - 2));
        result.append("]");
        return result.toString();
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
}
