package project.movie_reservation_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import project.movie_reservation_system.entity.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show,Long>,JpaSpecificationExecutor<Show> {

}
