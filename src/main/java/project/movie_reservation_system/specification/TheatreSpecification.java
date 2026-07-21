package project.movie_reservation_system.specification;

import org.springframework.data.jpa.domain.Specification;
import project.movie_reservation_system.entity.Theatre;

public class TheatreSpecification {

    public static Specification<Theatre> hasName(String name){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%"+name.toLowerCase()+"%"));
    }

    public static Specification<Theatre> hasCity(String city){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("city")),"%"+city.toLowerCase()+"%"));
    }
}
