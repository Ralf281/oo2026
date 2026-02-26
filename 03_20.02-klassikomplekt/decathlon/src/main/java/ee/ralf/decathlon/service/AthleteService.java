package ee.ralf.decathlon.service;

import ee.ralf.decathlon.entity.Athlete;
import ee.ralf.decathlon.entity.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AthleteService {

    public void validateAndCalculatePoints(Result result) {

        if (result.getSport() == null || result.getSport().isEmpty()) {
            throw new RuntimeException("Spordiala on kohustuslik");
        }

        List<String> allowedSports = List.of("100m", "kaugushüpe");
        if (!allowedSports.contains(result.getSport())) {
            throw new RuntimeException("Lubatud alad: 100m, kaugushüpe");
        }

        if (result.getResult() <= 0) {
            throw new RuntimeException("Tulemus peab olema positiivne number");
        }

        int points;

        if (result.getSport().equals("100m")) {
            points = (int) Math.max(0, (15 - result.getResult()) * 100);
        } else {
            points = (int) (result.getResult() * 100);
        }

        result.setPoints(points);
    }

    public int calculateTotalPoints(Athlete athlete) {
        return athlete.getResults()
                .stream()
                .mapToInt(Result::getPoints)
                .sum();
    }
}
