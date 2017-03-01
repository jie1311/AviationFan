package application;

import entities.Aircraft;
import entities.Airport;
import org.springframework.security.crypto.codec.Base64;

import java.nio.charset.Charset;

public class Calculator {

    public static int destance(Airport org, Airport des) {
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
        if (aircraft.getRange() > destance(org, des)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean reachable(Airport org, Airport des, int range) {
        if (range > destance(org, des)) {
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
}
