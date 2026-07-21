package project.movie_reservation_system.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import project.movie_reservation_system.dto.ShowRequest;
import project.movie_reservation_system.dto.ShowResponse;
import project.movie_reservation_system.entity.*;
import project.movie_reservation_system.exception.HallException;
import project.movie_reservation_system.exception.MovieException;
import project.movie_reservation_system.exception.ShowException;
import project.movie_reservation_system.repository.HallRepository;
import project.movie_reservation_system.repository.MovieRepository;
import project.movie_reservation_system.repository.ShowRepository;
import project.movie_reservation_system.specification.ShowSpecification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowService {
    private final ShowRepository showRepository;
    private final HallRepository hallRepository;
    private final MovieRepository movieRepository;
    private final ShowSeatService showSeatService;


    @Transactional
    public ShowResponse createShow( ShowRequest showRequest) {
        Show show= new Show();
        show.setStartTime(showRequest.getStartTime());
        Hall hall= hallRepository.findById(showRequest.getHallId()).orElseThrow(()->new HallException("Hall with the given id: "+showRequest.getHallId()+ " not found"));
        show.setHall(hall);

        Movie movie= movieRepository.findById(showRequest.getMovieId()).orElseThrow(()-> new MovieException("Movie with id: "+ showRequest.getMovieId()+" not found"));
        show.setMovie(movie);

        LocalDateTime startTime = showRequest.getStartTime();
        LocalDateTime endTime = startTime.plus(movie.getDuration());

        show.setStartTime(startTime);
        show.setEndTime(endTime);
        List<Show> existingShows = hall.getShows();
        for (Show existingShow : existingShows) {

            if (startTime.isBefore(existingShow.getEndTime())
                    && endTime.isAfter(existingShow.getStartTime())) {

                throw new ShowException(
                        "Another show is already scheduled in this hall during the selected time."
                );
            }
        }
        showRepository.save(show);
        showSeatService.generateShowSeats(show);
        return new ShowResponse(show.getId(),
                show.getStartTime(),
                show.getMovie().getDuration(),
                show.getHall().getName(),
                show.getHall().getTheatre().getName(),
                show.getMovie().getTitle());
    }

    @CachePut(value = "SHOW_CACHE",key = "#result.id()")
    public ShowResponse updateShow(Long id, ShowRequest showRequest) {
        Show show= showRepository.findById(id).orElseThrow(()-> new ShowException("Show with id: "+id+" not found"));
        show.setStartTime(showRequest.getStartTime());
        Hall hall= hallRepository.findById(showRequest.getHallId()).orElseThrow(()->new HallException("Hall with id: "+showRequest.getHallId()+" not found"));
        show.setHall(hall);
        Movie movie= movieRepository.findById(showRequest.getMovieId()).orElseThrow(()-> new MovieException("Movie with id: "+ showRequest.getMovieId()+" not found"));
        show.setMovie(movie);

        LocalDateTime startTime = showRequest.getStartTime();
        LocalDateTime endTime = startTime.plus(movie.getDuration());

        show.setStartTime(startTime);
        show.setEndTime(endTime);
        for (Show existingShow : hall.getShows()) {

            if (existingShow.getId().equals(show.getId())) {
                continue;
            }

            if (startTime.isBefore(existingShow.getEndTime())
                    && endTime.isAfter(existingShow.getStartTime())) {

                throw new ShowException(
                        "Another show is already scheduled in this hall during the selected time."
                );
            }
        }
        showRepository.save(show);
        return new ShowResponse(show.getId(),
                show.getStartTime(),
                show.getMovie().getDuration(),
                show.getHall().getName(),
                show.getHall().getTheatre().getName(),
                show.getMovie().getTitle());
    }


    @CacheEvict(value = "SHOW_CACHE",key = "#id")
    public String deleteShow(Long id) {
        Show show= showRepository.findById(id).orElseThrow(()-> new ShowException("Show with id: "+id+" not found"));
        showRepository.delete(show);
        return "Show successfully deleted.";
    }


    public Page<ShowResponse> getShows(int page, int size, String sortBy, String direction, Long movieId, Long theatreId, LocalDate date) {

        Sort sort= direction.equalsIgnoreCase("desc")? Sort.by(sortBy).descending(): Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page,size,sort);
        Specification<Show> spec= Specification.allOf();

        if(movieId!=null){
            spec= spec.and(ShowSpecification.hasMovie(movieId));
        }

        if(theatreId!=null){
            spec= spec.and(ShowSpecification.hasTheatre(theatreId));
        }

        if(date!=null){
            spec= spec.and(ShowSpecification.hasDate(date));
        }
        Page<Show> shows= showRepository.findAll(spec,pageable);
        return shows.map(
                show->
                    new ShowResponse(
                            show.getId(),
                            show.getStartTime(),
                            show.getMovie().getDuration(),
                            show.getHall().getName(),
                            show.getHall().getTheatre().getName(),
                            show.getMovie().getTitle()
                    )
                );
    }

    @Cacheable(value = "SHOW_CACHE",key = "#id")
    public ShowResponse getShow(Long id) {
        Show show= showRepository.findById(id).orElseThrow(()-> new ShowException("Show with id: "+id+" not found"));
        return new ShowResponse(
                show.getId(),
                show.getStartTime(),
                show.getMovie().getDuration(),
                show.getHall().getName(),
                show.getHall().getTheatre().getName(),
                show.getMovie().getTitle()
        );
    }

    public List<ShowResponse> getMovieShows(Long id) {
        Movie movie= movieRepository.findById(id).orElseThrow(()-> new MovieException("Movie with id: "+ id+" not found"));
        List<Show> shows = movie.getShows();
        return shows.stream().map(show ->
            new ShowResponse(show.getId(),
                    show.getStartTime(),
                    movie.getDuration(),
                    show.getHall().getName(),
                    show.getHall().getTheatre().getName(),
                    movie.getTitle())
        ).toList();
    }

}
