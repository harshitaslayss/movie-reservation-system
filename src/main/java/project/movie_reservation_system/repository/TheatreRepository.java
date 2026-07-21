package project.movie_reservation_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import project.movie_reservation_system.entity.Theatre;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Long>, JpaSpecificationExecutor<Theatre> {
    Page<Theatre> findByCityIgnoreCase(String city, Pageable pageable);
}
