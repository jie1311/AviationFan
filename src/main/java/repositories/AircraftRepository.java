package repositories;

import entities.Aircraft;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AircraftRepository extends MongoRepository<Aircraft, String> {

    public Aircraft findById(String id);
    public List<Aircraft> findByManufacturer(String manufacturer);
    public List<Aircraft> findByManufacturerAndModel(String manufacturer, String model);
    public List<Aircraft> findByManufacturerAndModelAndSubModel(String manufacturer, String model, String subModel);

}
