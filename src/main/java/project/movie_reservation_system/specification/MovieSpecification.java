package project.movie_reservation_system.specification;

import org.springframework.data.jpa.domain.Specification;
import project.movie_reservation_system.entity.Movie;

public class MovieSpecification {

    public static Specification<Movie> hasRating(String rating){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.lower(root.get("rating")),rating.toLowerCase()));
    }

    public static Specification<Movie> hasGenre(String genre){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.lower(root.get("genre")),genre.toLowerCase()));
    }

    public static Specification<Movie> hasLanguage(String language){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.lower(root.get("language")),language.toLowerCase()));
    }

    public static Specification<Movie> hasTitle(String title){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),"%"+title.toLowerCase()+"%"));
    }
}
