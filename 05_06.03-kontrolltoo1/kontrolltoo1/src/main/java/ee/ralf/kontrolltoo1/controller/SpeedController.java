package ee.ralf.kontrolltoo1.controller;

import ee.ralf.kontrolltoo1.entity.Speed;
import ee.ralf.kontrolltoo1.entity.SpeedConversion;
import ee.ralf.kontrolltoo1.repository.SpeedConversionRepository;
import ee.ralf.kontrolltoo1.repository.SpeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/speed")
public class SpeedController {

    @Autowired
    private SpeedRepository speedRepository;

    @Autowired
    private SpeedConversionRepository speedConversionRepository;

    @PostMapping("/add")
    public String addSpeed(@RequestParam int value) {
        if (value <= 0) return "Speed must be greater than 0";
        if (value > 300) return "Speed too large";

        Speed s = new Speed();
        s.setValue(value);
        speedRepository.save(s);

        return "Speed added";
    }

    @GetMapping("/all")
    public List<Speed> getAll() {
        return speedRepository.findAll();
    }

    @GetMapping("/average")
    public double average() {
        List<Speed> speeds = speedRepository.findAll();
        if (speeds.isEmpty()) return 0;

        int sum = 0;
        for (Speed s : speeds) sum += s.getValue();
        return (double) sum / speeds.size();
    }

    @GetMapping("/convert")
    public List<Double> convert() {
        List<Speed> speeds = speedRepository.findAll();
        List<Double> result = new ArrayList<>();

        for (Speed s : speeds) {
            double mph = s.getValue() * 0.621371;
            result.add(mph);

            speedConversionRepository.save(new SpeedConversion(null, mph));
        }

        return result;
    }

    @PostMapping("/increase")
    public String increase() {
        List<Speed> speeds = speedRepository.findAll();
        for (Speed s : speeds) {
            s.setValue(s.getValue() + 1);
            speedRepository.save(s);
        }
        return "All speeds increased";
    }
}