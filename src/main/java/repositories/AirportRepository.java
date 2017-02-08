package repositories;

import entities.Airport;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AirportRepository extends MongoRepository<Airport, String> {

    public Airport findById(String id);
    public Airport findByIataCode(String iataCode);
    public List<Airport> findByCity(String city);

}