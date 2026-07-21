package project.movie_reservation_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.movie_reservation_system.entity.Reservation;
import project.movie_reservation_system.entity.User;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long>{
    List<Reservation> findByUser(User user);
    Page<Reservation> findByUser(Pageable pageable, User user);
}
