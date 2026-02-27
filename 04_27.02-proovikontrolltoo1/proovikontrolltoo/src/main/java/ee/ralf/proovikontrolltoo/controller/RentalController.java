package ee.ralf.proovikontrolltoo.controller;

import ee.ralf.proovikontrolltoo.dto.FilmRentalDto;
import ee.ralf.proovikontrolltoo.entity.Film;
import ee.ralf.proovikontrolltoo.entity.Rental;
import ee.ralf.proovikontrolltoo.repository.FilmRepository;
import ee.ralf.proovikontrolltoo.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RentalController {

    private final RentalRepository rentalRepository;
    private final FilmRepository filmRepository;
    private double premiumPrice = 4;
    private double basicPrice = 3;

    @GetMapping("rentals")
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @PostMapping("start-rental")
    public Rental startRental(@RequestBody List<FilmRentalDto> filmRentalDtos) {

        Rental rental = new Rental();
        Rental dbRental = rentalRepository.save(rental);

        double sum = 0;

        for (FilmRentalDto filmRentalDto : filmRentalDtos) {
            Film dbFilm = filmRepository.findById(filmRentalDto.filmId()).orElseThrow();
            dbFilm.setRental(dbRental);
            dbFilm.setDays(filmRentalDto.days());
            switch (dbFilm.getType()) {
                case NEW -> sum += premiumPrice * filmRentalDto.days();
                case REGULAR -> {
                    if (filmRentalDto.days() <= 3) {
                        sum += basicPrice;
                    } else {
                        sum += basicPrice + basicPrice * filmRentalDto.days() - 3;
                    }
                }
                case OLD -> {
                    if (filmRentalDto.days() <= 5) {
                        sum += basicPrice;
                    } else {
                        sum += basicPrice + basicPrice * filmRentalDto.days() - 5;
                    }
                }
            }
            filmRepository.save(dbFilm);
        }

        dbRental.setInitialFee(sum); // {id: 2, initialFee: 123.0, lateFee: null}
        return rentalRepository.save(dbRental);
    }

    // DTO ---> mis film (ID), mitu päeva tegelikult rendis oli
    @PostMapping("end-rental")
    public Rental endRental(@RequestBody List<FilmRentalDto> filmRentalDtos) {

        double totalLateFee = 0;

        for (FilmRentalDto filmRentalDto : filmRentalDtos) {

            Film dbFilm = filmRepository.findById(filmRentalDto.filmId()).orElseThrow();
            Rental rental = dbFilm.getRental();

            if (rental == null) {
                throw new IllegalStateException("Film is not currently rented");
            }

            int extraDays = filmRentalDto.days() - dbFilm.getDays(); // tegelikult renditud päevad - algne päevade arv
            double filmLateFee = 0;

            if (extraDays > 0) {
                switch (dbFilm.getType()) {
                    case NEW -> filmLateFee = extraDays * premiumPrice;
                    case REGULAR -> {
                        if (extraDays <= 3) {
                            filmLateFee = basicPrice;
                        } else {
                            filmLateFee = basicPrice + (extraDays - 3) * basicPrice;
                        }
                    }
                    case OLD -> {
                        if (extraDays <= 5) {
                            filmLateFee = basicPrice;
                        } else {
                            filmLateFee = basicPrice + (extraDays - 5) * basicPrice;
                        }
                    }
                }
            }

            rental.setLateFee(rental.getLateFee() + filmLateFee);
            totalLateFee += filmLateFee;

            dbFilm.setRental(null);
            dbFilm.setDays(0);

            filmRepository.save(dbFilm);
            rentalRepository.save(rental);
        }

        System.out.println("Total late fee: " + totalLateFee + " EUR");

        // Tagastame lihtsalt esimese rental'i
        return filmRentalDtos.isEmpty() ? null :
                filmRepository.findById(filmRentalDtos.get(0).filmId())
                        .orElseThrow()
                        .getRental();
    }
}

