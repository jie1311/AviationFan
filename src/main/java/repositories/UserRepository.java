package repositories;


import entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    public User findById(String id);
    public User findByUsername(String username);
}
