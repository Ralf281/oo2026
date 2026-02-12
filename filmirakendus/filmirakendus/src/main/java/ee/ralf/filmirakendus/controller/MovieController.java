package ee.ralf.filmirakendus.controller;

import ee.ralf.filmirakendus.entity.Movie;
import ee.ralf.filmirakendus.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    // GET - kõik filmid
    // localhost:2004/movies
    @GetMapping("movies")
    public List<Movie> getMovies(){
        return movieRepository.findAll();
    }

    // DELETE - kustuta film id järgi
    @DeleteMapping("movies/{id}")
    public List<Movie> deleteMovie(@PathVariable Long id){
        movieRepository.deleteById(id);
        return movieRepository.findAll();
    }

    // POST - lisa film
    @PostMapping("movies")
    public List<Movie> addMovie(@RequestBody Movie movie){
        movieRepository.save(movie);
        return movieRepository.findAll();
    }
}


