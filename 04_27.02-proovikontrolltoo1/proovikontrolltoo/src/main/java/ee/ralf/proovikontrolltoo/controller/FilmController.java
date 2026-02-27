package ee.ralf.proovikontrolltoo.controller;

import ee.ralf.proovikontrolltoo.dto.FilmSaveDto;
import ee.ralf.proovikontrolltoo.entity.Film;
import ee.ralf.proovikontrolltoo.entity.FilmType;
import ee.ralf.proovikontrolltoo.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FilmController {

    private final FilmRepository filmRepository;

    @PostMapping("films")
    public Film saveFilm(@RequestBody FilmSaveDto filmSaveDto){
        Film film = new Film();
        film.setTitle(filmSaveDto.title());
        film.setType(filmSaveDto.type());
        film.setDays(0);
        return filmRepository.save(film);
    }

    @DeleteMapping("films/{id}")
    public void deleteFilm(@PathVariable Long id){
        filmRepository.deleteById(id);
    }

    @PatchMapping("film/type")
    public Film changeFilmType(@RequestParam Long id, @RequestParam FilmType filmType){
        Film film = filmRepository.findById(id).orElseThrow();
        film.setType(filmType);
        return filmRepository.save(film);
    }

    @GetMapping("films")
    public List<Film> findAll(){
        return filmRepository.findAll();
    }

    @GetMapping("films/available")
    public List<Film> findAllAvailable(){
        return filmRepository.findByDays(0);
    }
}
