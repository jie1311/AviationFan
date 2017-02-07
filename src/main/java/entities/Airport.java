package entities;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Airport {

    @Id
    private String id;

    private String iataCode;
    private String city;
    private double[] coordinate;

    public Airport() {
    }

    public Airport(String iataCode, String city, double[] coordinate) {
        this.iataCode = iataCode;
        this.city = city;
        this.coordinate = coordinate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(double[] coordinate) {
        this.coordinate = coordinate;
    }

    public String getAirport() {
        return String.format("%s %s", iataCode, city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iataCode, city, coordinate);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof Airport) {
            return false;
        } else {
            Airport airport = (Airport) obj;
            return coordinate.equals(airport.getCoordinate()) &&
                    Objects.equals(iataCode, airport.getIataCode()) &&
                    Objects.equals(city, airport.getCity());
        }
    }
}
