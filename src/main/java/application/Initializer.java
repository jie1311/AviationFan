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

        aircraftInitial();
        airportInitial();
        userInitial();

        for (Aircraft aircraft : aircraftRepository.findAll()) {
            System.out.println(aircraft.getType());
        }

        for (Airport airport : airportRepository.findAll()) {
            System.out.println(airport.getCity());
        }
    }

    private void aircraftInitial() {
        aircraftRepository.deleteAll();

        aircraftRepository.save(new Aircraft("Airbus", "A319", "100", 6950, 124));
        aircraftRepository.save(new Aircraft("Airbus", "A320", "200", 6100, 150));
        aircraftRepository.save(new Aircraft("Airbus", "A321", "200", 5950, 185));
        aircraftRepository.save(new Aircraft("Airbus", "A330", "200", 13450, 246));
        aircraftRepository.save(new Aircraft("Airbus", "A330", "900neo", 12130, 287));
        aircraftRepository.save(new Aircraft("Airbus", "A330", "300", 11750, 300));
        aircraftRepository.save(new Aircraft("Airbus", "A330", "800neo", 13900, 257));
        aircraftRepository.save(new Aircraft("Airbus", "A350", "900", 15000, 325));
        aircraftRepository.save(new Aircraft("Airbus", "A350", "1000", 14800, 366));
        aircraftRepository.save(new Aircraft("Airbus", "A350", "900ULR", 15000, 325));
        aircraftRepository.save(new Aircraft("Airbus", "A380", "800", 15200, 544));
        aircraftRepository.save(new Aircraft("Boeing", "777", "300ER", 13650, 365));
        aircraftRepository.save(new Aircraft("Boeing", "777", "300", 11165, 368));
        aircraftRepository.save(new Aircraft("Boeing", "777", "200", 9700, 305));
        aircraftRepository.save(new Aircraft("Boeing", "777", "200ER", 13080, 301));
        aircraftRepository.save(new Aircraft("Boeing", "777", "200LR", 15840, 301));
        aircraftRepository.save(new Aircraft("Boeing", "777", "9X", 14100, 412));
        aircraftRepository.save(new Aircraft("Boeing", "777", "8X", 16100, 362));
        aircraftRepository.save(new Aircraft("Boeing", "787", "10", 11908, 330));
        aircraftRepository.save(new Aircraft("Boeing", "787", "8", 13621, 242));
        aircraftRepository.save(new Aircraft("Boeing", "787", "9", 14140, 290));
        aircraftRepository.save(new Aircraft("Bombardier", "CS", "100", 5741, 108));
        aircraftRepository.save(new Aircraft("Bombardier", "CS", "300", 6112, 130));
    }

    private void airportInitial() {
        airportRepository.deleteAll();

        double[] corAKL = {174.791667, -37.008056};
        airportRepository.save(new Airport("AKL", "Auckland", corAKL));
        double[] corBNE = {153.118333, -27.383333};
        airportRepository.save(new Airport("BNE", "Brisbane", corBNE));
        double[] corCTU = {103.946944, 30.578333};
        airportRepository.save(new Airport("CTU", "Chengdu", corCTU));
        double[] corDRW = {130.876667, -12.414722};
        airportRepository.save(new Airport("DRW", "Darwin", corDRW));
        double[] corDXB = {55.364444, 25.252778};
        airportRepository.save(new Airport("DXB", "Dubai", corDXB));
        double[] corHKG = {113.914444, 22.308889};
        airportRepository.save(new Airport("HKG", "Hong Kong", corHKG));
        double[] corHNL = {-157.922500, 21.318611};
        airportRepository.save(new Airport("HNL", "Honolulu", corHNL));
        double[] corJFK = {-73.778889, 40.639722};
        airportRepository.save(new Airport("JFK", "New York", corJFK));
        double[] corJNB = {28.246111, -26.139167};
        airportRepository.save(new Airport("JNB", "Johannesburg", corJNB));
        double[] corLAX = {-118.408056, 33.942500};
        airportRepository.save(new Airport("LAX", "Los Angeles", corLAX));
        double[] corLHR = {-0.461389, 51.477500};
        airportRepository.save(new Airport("LHR", "London", corLHR));
        double[] corMEL = {144.843333, -37.673333};
        airportRepository.save(new Airport("MEL", "Melbourne", corMEL));
        double[] corPEK = {116.597500, 40.072500};
        airportRepository.save(new Airport("PEK", "Beijing", corPEK));
        double[] corPER = {115.966944, -31.940278};
        airportRepository.save(new Airport("PER", "Perth", corPER));
        double[] corPVG = {121.805278, 31.143333};
        airportRepository.save(new Airport("PVG", "Shanghai", corPVG));
        double[] corSCL = {-70.785556, -33.392778};
        airportRepository.save(new Airport("SCL", "Santiago", corSCL));
        double[] corSHA = {121.336389, 31.198056};
        airportRepository.save(new Airport("SHA", "Shanghai", corSHA));
        double[] corSIN = {103.989306, 1.359211};
        airportRepository.save(new Airport("SIN", "Singapore", corSIN));
        double[] corSVO = {37.414722, 55.972778};
        airportRepository.save(new Airport("SVO", "Moscow", corSVO));
        double[] corSYD = {151.177222, -33.946111};
        airportRepository.save(new Airport("SYD", "Sydney", corSYD));
    }

    private void userInitial() {
        userRepository.deleteAll();

        String psd = Calculator.base64Encode("password");
        userRepository.save(new User("user", psd));
    }

}
