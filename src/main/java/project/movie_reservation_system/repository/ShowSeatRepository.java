package project.movie_reservation_system.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.movie_reservation_system.entity.ShowSeat;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {
    List<ShowSeat> findByShowId(Long showId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(""" 
            Select s from ShowSeat s where s.id in :ids
            """)
    List<ShowSeat> findAllByIdWithLock(@Param("ids") List<Long> ids);
}
