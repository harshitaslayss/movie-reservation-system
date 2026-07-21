package project.movie_reservation_system.specification;

import org.springframework.data.jpa.domain.Specification;
import project.movie_reservation_system.entity.Show;
import project.movie_reservation_system.entity.Theatre;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class ShowSpecification {

    public static Specification<Show> hasMovie(Long movieId){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("movie").get("id"),movieId));
    }

    public static Specification<Show> hasTheatre(Long theatreId){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hall").get("theatre").get("id"),theatreId));
    }

    public static Specification<Show> hasDate(LocalDate date){
        LocalDateTime start= date.atStartOfDay();
        LocalDateTime end= date.atStartOfDay().plusDays(1);
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"),start),criteriaBuilder.lessThan(root.get("startTime"),end));
    }
}
