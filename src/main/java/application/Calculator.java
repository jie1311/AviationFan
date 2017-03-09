package application;

import entities.Aircraft;
import entities.Airport;
import org.springframework.security.crypto.codec.Base64;
import repositories.AirportRepository;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private static ArrayList<Airport> reachableAirports(Airport org, int range, AirportRepository airportRepository) {
        ArrayList<Airport> airports = new ArrayList<>();
        for (Airport des : airportRepository.findAll()) {
            if (Calculator.reachable(org, des, range) && !org.getId().equals(des.getId())) {
                airports.add(des);
            }
        }
        return airports;
    }

    private static boolean checkNode(Airport des, boolean found, Node current, Node result) {
        if (current.getVia().getId().equals(des.getId())) {
            found = true;
            result.setOrg(current.getOrg());
            result.setDes(current.getDes());
            result.setVia(current.getVia());
            result.setLastNode(current.getLastNode());
            result.setRoute(current.getRoute());
            result.setG(current.getG());
            result.setH(current.getH());
            result.setF(current.getF());
        }
        return found;
    }

    private static void openNode(Airport org, Airport des, int range,
                                 Node start,
                                 ArrayList<Node> avlb,
                                 ArrayList<Node> schd,
                                 AirportRepository airportRepository) {
        for (Airport airport : reachableAirports(org, range, airportRepository)) {
            Node next = new Node(org, des, airport, start);
            avlb.add(next);
        }

        for (Iterator<Node> it = avlb.iterator(); it.hasNext(); ) {
            Node via = it.next();
            l2:
            for (Node srd : schd) {
                if (srd.getVia().getId().equals(via.getVia().getId())) {
                    it.remove();
                    break l2;
                }
            }
        }
    }

    public static ArrayList<Airport> lessReroute(Airport org, Airport des, int range, AirportRepository airportRepository) {
        ArrayList<Node> avlb = new ArrayList<>();
        ArrayList<Node> schd = new ArrayList<>();
        Node start = new Node(org, des, org);
        Node result = new Node();
        lessReroute(org, des, range, avlb, schd, start, result, airportRepository);

        if (!(result.getRoute() == null)) {
            return result.getRoute();
        } else {
            return new ArrayList<>();
        }
    }

    private static void lessReroute(Airport org, Airport des, int range,
                                    ArrayList<Node> avlb,
                                    ArrayList<Node> schd,
                                    Node start,
                                    Node result,
                                    AirportRepository airportRepository) {
        schd.add(start);
        boolean found = false;

        found = checkNode(des, found, start, result);

        if (!found) {
            openNode(org, des, range, start, avlb, schd, airportRepository);
            sortLessReroute(avlb);
            l3:
            for (Node node : avlb) {
                lessReroute(node.getVia(), des, range, avlb, schd, node, result, airportRepository);
                try {
                    result.getRoute();
                    break l3;
                } catch (Exception e) {
                    if (avlb.isEmpty()) {
                        break l3;
                    }
                }
            }

        }
    }

    private static void sortLessReroute(ArrayList<Node> avlb) {
        Collections.sort(avlb, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                Integer f1 = o1.getF();
                Integer f2 = o2.getF();
                return f1.compareTo(f2);
            }
        });

        Collections.sort(avlb, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                Integer f1 = o1.getRoute().size();
                Integer f2 = o2.getRoute().size();
                return f1.compareTo(f2);
            }
        });
    }

    public static ArrayList<Airport> lessTravel(Airport org, Airport des, int range, AirportRepository airportRepository) {
        ArrayList<Node> avlb = new ArrayList<>();
        ArrayList<Node> schd = new ArrayList<>();
        Node start = new Node(org, des, org);
        Node result = new Node();
        lessTravel(org, des, range, avlb, schd, start, result, airportRepository);

        if (!(result.getRoute() == null)) {
            return result.getRoute();
        } else {
            return new ArrayList<>();
        }
    }

    private static void lessTravel(Airport org, Airport des, int range,
                                   ArrayList<Node> avlb,
                                   ArrayList<Node> schd,
                                   Node start,
                                   Node result,
                                   AirportRepository airportRepository) {
        schd.add(start);
        boolean found = false;

        found = checkNode(des, found, start, result);

        if (!found) {
            openNode(org, des, range, start, avlb, schd, airportRepository);
            sortLessTravel(avlb);
            l3:
            for (Node node : avlb) {
                lessTravel(node.getVia(), des, range, avlb, schd, node, result, airportRepository);
                try {
                    result.getRoute();
                    break l3;
                } catch (Exception e) {
                    if (avlb.isEmpty()) {
                        break l3;
                    }
                }
            }
        }
    }

    private static void sortLessTravel(ArrayList<Node> avlb) {
        Collections.sort(avlb, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                Integer f1 = o1.getF();
                Integer f2 = o2.getF();
                return f1.compareTo(f2);
            }
        });
    }
}
