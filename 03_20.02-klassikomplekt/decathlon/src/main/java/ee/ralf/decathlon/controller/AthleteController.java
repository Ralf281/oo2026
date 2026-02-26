package ee.ralf.decathlon.controller;

import ee.ralf.decathlon.dto.ResultDto;
import ee.ralf.decathlon.entity.Athlete;
import ee.ralf.decathlon.entity.Result;
import ee.ralf.decathlon.repository.AthleteRepository;
import ee.ralf.decathlon.repository.ResultRepository;
import ee.ralf.decathlon.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AthleteController {

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private AthleteService athleteService;

    @GetMapping("athletes")
    public List<Athlete> getAthletes() {
        return athleteRepository.findAll();
    }

    @GetMapping("athletes/{id}")
    public Athlete getAthlete(@PathVariable Long id) {
        return athleteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sportlast ei leitud"));
    }

    @PostMapping("athletes")
    public Athlete addAthlete(@RequestBody Athlete athlete) {
        if (athlete.getName() == null || athlete.getName().isEmpty()) {
            throw new RuntimeException("Sportlase nimi on kohustuslik");
        }
        return athleteRepository.save(athlete);
    }

    @PostMapping("athletes/{id}/results")
    public Athlete addResult(@PathVariable Long id,
                             @RequestBody ResultDto dto) {

        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sportlast ei leitud"));

        Result result = new Result();
        result.setSport(dto.sport);
        result.setResult(dto.result);

        athleteService.validateAndCalculatePoints(result);

        result.setAthlete(athlete);
        resultRepository.save(result);

        return athleteRepository.findById(id).get();
    }

    @GetMapping("athletes/{id}/total")
    public int getTotalPoints(@PathVariable Long id) {

        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sportlast ei leitud"));

        return athleteService.calculateTotalPoints(athlete);
    }
}