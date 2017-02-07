package repositories;

import entities.Airport;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AirportRepository extends MongoRepository<Airport, String> {

    public Airport findByIataCode(String iataCode);
    public Airport findById(String id);
}