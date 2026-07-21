package project.movie_reservation_system.specification;

import org.springframework.data.jpa.domain.Specification;
import project.movie_reservation_system.entity.Hall;

public class HallSpecification {
    public static Specification<Hall> hasName(String name) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(name)),"%"+name.toLowerCase()+"%"));
    }

    public static Specification<Hall> hasTheatreName(String theatreName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(theatreName)),"%"+theatreName.toLowerCase()+"%"));
    }
}
