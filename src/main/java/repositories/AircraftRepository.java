package repositories;

import entities.Aircraft;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AircraftRepository extends MongoRepository<Aircraft, String> {

    public List<Aircraft> findByManufacturer(String manufacturer);
    public Aircraft findById(String id);
    public List<Aircraft> findByManufacturerAndModelAndSubModel(String manufacturer, String model, String subModel);

}
