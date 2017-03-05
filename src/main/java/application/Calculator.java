package application;

import entities.Aircraft;
import entities.Airport;
import org.springframework.security.crypto.codec.Base64;
import repositories.AirportRepository;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;


public class Calculator {

    public static int distance(Airport org, Airport des) {
        double lat1 = org.getCoordinate()[1];
        double lat2 = des.getCoordinate()[1];
        double lon1 = org.getCoordinate()[0];
        double lon2 = des.getCoordinate()[0];

        double theta = lon1 - lon2;
        double ditanceRaw = Math.sin(
                deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        double distance = rad2deg(Math.acos(ditanceRaw)) * 111.19;

        return (int) distance;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public static boolean reachable(Airport org, Airport des, Aircraft aircraft) {
        if (aircraft.getRange() > distance(org, des)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean reachable(Airport org, Airport des, int range) {
        if (range > distance(org, des)) {
            return true;
        } else {
            return false;
        }
    }

    public static String base64Encode(String token) {
        byte[] encodedBytes = Base64.encode(token.getBytes());
        return new String(encodedBytes, Charset.forName("UTF-8"));
    }

    public static String base64Decode(String token) {
        byte[] decodedBytes = Base64.decode(token.getBytes());
        return new String(decodedBytes, Charset.forName("UTF-8"));
    }

    public static ArrayList<Airport> lessReroute(Airport org, Airport des, int range, AirportRepository airportRepository) {
        ArrayList<Airport> route = Calculator.lessReroute2(org, des, range, airportRepository);
        while (!(route.isEmpty() || Calculator.reachable(route.get(route.size() - 1), org, range))) {
            Airport via = route.get(route.size() - 1);
            route.addAll(Calculator.lessReroute2(org, via, range, airportRepository));
        }
        return route;
    }

    private static ArrayList<Airport> lessReroute2(Airport org, Airport des, int range, AirportRepository airportRepository) {
        ArrayList<Airport> avlb = new ArrayList<>();
        ArrayList<Airport> schd = new ArrayList<>();
        ArrayList<Airport> rslt = new ArrayList<>();
        return lessReroute3(org, des, range, avlb, schd, rslt, airportRepository);
    }

    private static ArrayList<Airport> lessReroute3(Airport org, Airport des, int range,
                                          ArrayList<Airport> avlb,
                                          ArrayList<Airport> schd,
                                          ArrayList<Airport> rslt,
                                          AirportRepository airportRepository) {
        schd.add(org);

        boolean found = false;

        if (reachable(org, des, range)) {
            found = true;
            rslt.add(org);
        } else {
            for (Airport airport : reachableAirports(org, range, airportRepository)) {
                avlb.add(airport);
            }
        }

        if (!found) {
            for(Iterator<Airport> it = avlb.iterator(); it.hasNext();){
                Airport via = it.next();
                in:
                for (Airport srd : schd) {
                    if (srd.getId().equals(via.getId())) {
                        it.remove();
                        break in;
                    }
                }
            }

            for (Airport via : avlb) {
                lessReroute3(via, des, range, avlb, schd, rslt, airportRepository);
                if (!rslt.isEmpty()) {
                    break;
                } else if (avlb.isEmpty()) {
                    break;
                }
            }
        }
        return rslt;
    }

    private static ArrayList<Airport> reachableAirports(Airport org, int range, AirportRepository airportRepository) {
        ArrayList<Airport> airports = new ArrayList<>();
        for (Airport des : airportRepository.findAll()) {
            if (Calculator.reachable(org, des, range) && !org.getId().equals(des.getId())) {
                airports.add(des);
            }
        }
        return airports;
    }

}
