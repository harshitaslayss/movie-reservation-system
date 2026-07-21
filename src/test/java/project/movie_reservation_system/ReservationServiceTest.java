package project.movie_reservation_system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.movie_reservation_system.dto.ReservationRequest;
import project.movie_reservation_system.entity.Show;
import project.movie_reservation_system.entity.ShowSeat;
import project.movie_reservation_system.repository.ReservationRepository;
import project.movie_reservation_system.repository.ShowRepository;
import project.movie_reservation_system.repository.ShowSeatRepository;
import project.movie_reservation_system.repository.UserRepository;
import project.movie_reservation_system.service.RedisService;
import project.movie_reservation_system.service.ReservationService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

//    @Mock
//    ShowRepository showRepository;
//
//    @Mock
//    ShowSeatRepository showSeatRepository;
//
//    @Mock
//    ReservationRepository reservationRepository;
//
//    @Mock
//    UserRepository userRepository;
//
//    @InjectMocks
//    ReservationService reservationService;
//
//    @Test
//    public void create_reservation_success(){
//
//        ReservationRequest request= new ReservationRequest();
//        request.setShowId(1L);
//        List<Long> showSeats= new ArrayList<>();
//        showSeats.add(101L);
//        showSeats.add(102L);
//        showSeats.add(103L);
//        request.setShowSeatId(showSeats);
//
//        Show show= new Show();
//        show.setId(1L);
//        show.setStartTime(new LocalTime(8,30,00));
//        show.setMovie();
//    }





}
