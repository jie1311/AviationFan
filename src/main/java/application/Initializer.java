package application;

import entities.Aircraft;
import entities.Airport;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import repositories.AircraftRepository;
import repositories.AirportRepository;
import repositories.UserRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"repositories"})
public class Initializer implements CommandLineRunner {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Initializer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        aircraftInitial();
//        airportInitial();
//        userInitial();

        for (Aircraft aircraft : aircraftRepository.findAll()) {
            System.out.println(aircraft.getType());
        }

        for (Airport airport : airportRepository.findAll()) {
            System.out.println(airport.getCity());
        }

    }

    private void aircraftInitial() {
        aircraftRepository.deleteAll();

        aircraftRepository.save(new Aircraft("Airbus", "A350", "900", 15000, 325));
        aircraftRepository.save(new Aircraft("Airbus", "A350", "900ULR", 16120, 325));
        aircraftRepository.save(new Aircraft("Airbus", "A350", "1000", 14800, 366));
        aircraftRepository.save(new Aircraft("Boeing", "777", "300ER", 13650,365));


    }

    private void airportInitial() {
        airportRepository.deleteAll();

        double[] mel = {-37.673333, 144.843333};
        airportRepository.save(new Airport("MEL", "Melbourne", mel));
        double[] dxb = {25.252778, 55.364444};
        airportRepository.save(new Airport("DXB", "Dubai", dxb));
        double[] lhr = {51.4775, -0.461389};
        airportRepository.save(new Airport("LHR", "London", lhr));

    }

    private void userInitial() {
        userRepository.deleteAll();

        String psd = Calculator.base64Encode("password");
        userRepository.save(new User("user", psd));
        String psd2 = Calculator.base64Encode("password");
        userRepository.save(new User("user2", psd2));
    }

}
