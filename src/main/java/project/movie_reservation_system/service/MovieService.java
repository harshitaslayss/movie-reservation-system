package project.movie_reservation_system.service;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import project.movie_reservation_system.dto.MovieRequest;
import project.movie_reservation_system.dto.MovieResponse;
import project.movie_reservation_system.entity.Movie;
import project.movie_reservation_system.exception.MovieException;
import project.movie_reservation_system.repository.MovieRepository;
import project.movie_reservation_system.specification.MovieSpecification;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieResponse createMovie(MovieRequest movieRequest) {
        Movie movie= new Movie();
        movie.setTitle(movieRequest.getTitle());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        movie.setDescription(movieRequest.getDescription());
        movie.setRating(movieRequest.getRating());
        movie.setDuration(movieRequest.getDuration());
        movie.setGenre(movieRequest.getGenre());
        movie.setLanguage(movieRequest.getLanguage());
        movie.setPosterUrl(movieRequest.getPosterUrl());

        Movie savedMovie= movieRepository.save(movie);
        return new MovieResponse(savedMovie.getId(),savedMovie.getTitle(),savedMovie.getReleaseDate(),savedMovie.getDescription(),savedMovie.getRating(),savedMovie.getDuration(),savedMovie.getGenre(),savedMovie.getLanguage(),savedMovie.getPosterUrl());
    }

    @CachePut(value = "MOVIE_CACHE",key="#result.id()")
    public MovieResponse updateMovie(Long id, MovieRequest movieRequest){
        Movie movie= movieRepository.findById(id).orElseThrow(()-> new MovieException("Movie with id: "+ id+" not found"));

        movie.setTitle(movieRequest.getTitle());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        movie.setDescription(movieRequest.getDescription());
        movie.setRating(movieRequest.getRating());
        movie.setDuration(movieRequest.getDuration());
        movie.setGenre(movieRequest.getGenre());
        movie.setLanguage(movieRequest.getLanguage());
        movie.setPosterUrl(movieRequest.getPosterUrl());

        movieRepository.save(movie);
        return new MovieResponse(movie.getId(),movie.getTitle(),movie.getReleaseDate(),movie.getDescription(),movie.getRating(),movie.getDuration(),movie.getGenre(),movie.getLanguage(),movie.getPosterUrl());
    }

    @CacheEvict(value = "MOVIE_CACHE",key = "#id")
    public String deleteMovie(Long id) {
        Movie movie= movieRepository.findById(id)
                .orElseThrow(()-> new MovieException("Movie with id: "+ id+" not found"));

        movieRepository.delete(movie);
        return "Movie deleted successfully.";
    }


    public Page<MovieResponse> getMovies(int page, int size, String sortBy, String direction, String rating, String genre,String language, String title) {
        Sort sort= direction.equalsIgnoreCase("desc")? Sort.by(sortBy).descending(): Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page,size,sort);
        Specification<Movie> spec= Specification.allOf();

        if(rating!=null && !rating.isBlank()){
           spec= spec.and(MovieSpecification.hasRating(rating));
        }
        if(genre!=null && !genre.isBlank()){
            spec= spec.and(MovieSpecification.hasGenre(genre));
        }
        if(language!=null && !language.isBlank()){
            spec= spec.and(MovieSpecification.hasLanguage(language));
        }
        if(title!=null && !title.isBlank()){
            spec= spec.and(MovieSpecification.hasTitle(title));
        }
        Page<Movie> movies= movieRepository.findAll(spec,pageable);
        return movies.map(
                movie -> new MovieResponse(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getReleaseDate(),
                        movie.getDescription(),
                        movie.getRating(),
                        movie.getDuration(),
                        movie.getGenre(),
                        movie.getLanguage(),
                        movie.getPosterUrl()));

    }

    @Cacheable(value = "MOVIE_CACHE",key = "#id")
    public MovieResponse getMovie(Long id){
        Movie movie= movieRepository.findById(id).orElseThrow(()->new MovieException("Movie with id: "+ id+" not found"));
        return new MovieResponse(movie.getId(),
                movie.getTitle(),
                movie.getReleaseDate(),
                movie.getDescription(),
                movie.getRating(),
                movie.getDuration(),
                movie.getGenre(),
                movie.getLanguage(),
                movie.getPosterUrl());

    }


}
