package ee.ralf.autorakendus.controller;

import ee.ralf.autorakendus.entity.Car;
import ee.ralf.autorakendus.repository.CarRepository;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // GET - kõik autod
    @GetMapping
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    // GET - auto ID järgi
    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto ID-ga " + id + " ei leitud!"));
    }

    // POST - lisa auto
    @PostMapping
    public Car addCar(@RequestBody Car car) {

        // 1. Kohustuslikud väljad
        if (car.getBrand() == null || car.getModel() == null
                || car.getYear() == null || car.getPrice() == null) {
            throw new RuntimeException("Kõik väljad on kohustuslikud!");
        }

        // 2. Aasta liiga väike
        if (car.getYear() < 1886) {
            throw new RuntimeException("Auto aasta ei saa olla väiksem kui 1886!");
        }

        // 3. Tuleviku aasta
        if (car.getYear() > Year.now().getValue()) {
            throw new RuntimeException("Auto aasta ei saa olla tulevikus!");
        }

        // 4. Hind peab olema positiivne
        if (car.getPrice() <= 0) {
            throw new RuntimeException("Auto hind peab olema positiivne!");
        }

        // 5. Duplikaat
        if (carRepository.findByBrandAndModelAndYear(
                car.getBrand(), car.getModel(), car.getYear()).isPresent()) {
            throw new RuntimeException("Selline auto on juba olemas!");
        }

        return carRepository.save(car);
    }

    // DELETE - auto ID järgi
    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable Long id) {
        if (!carRepository.existsById(id)) {
            return "Auto ID-ga " + id + " ei leitud!";
        }
        carRepository.deleteById(id);
        return "Auto ID-ga " + id + " kustutatud!";
    }
}
