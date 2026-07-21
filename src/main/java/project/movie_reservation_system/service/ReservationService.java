package project.movie_reservation_system.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.movie_reservation_system.dto.ReservationRequest;
import project.movie_reservation_system.dto.ReservationResponse;
import project.movie_reservation_system.entity.*;
import project.movie_reservation_system.exception.ReservationAlreadyCancelledException;
import project.movie_reservation_system.exception.ReservationException;
import project.movie_reservation_system.exception.SeatException;
import project.movie_reservation_system.exception.ShowException;
import project.movie_reservation_system.repository.ReservationRepository;
import project.movie_reservation_system.repository.ShowRepository;
import project.movie_reservation_system.repository.ShowSeatRepository;
import project.movie_reservation_system.repository.UserRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation reservation= new Reservation();
        User user= getCurrentUser();

        //to check if show exists or not?
        Show show= showRepository.findById(request.getShowId()).orElseThrow(()-> new ShowException(("Show with id: "+request.getShowId()+" not found.")));
        reservation.setShow(show);
        //get the selected seats' id
        List<Long> showSeatsId= request.getShowSeatId();
        //map to showSeats
        List<ShowSeat> showSeats= showSeatRepository.findAllByIdWithLock(showSeatsId);
        if (showSeats.size() != showSeatsId.size()) {
            throw new SeatException("One or more selected seats do not exist.");
        }
        for(ShowSeat showSeat: showSeats){
          //check for reserved/ clashing seats
            if(showSeat.getStatus()!= SeatStatus.AVAILABLE){
               throw new SeatException("The seat is already booked.");
           } if (!(showSeat.getShow().getId().equals(show.getId()))){
                throw new SeatException("The selected seat does not belong to the selected show.");
            }
        }
        reservation.setShowSeats(showSeats);
        for(ShowSeat seat: showSeats){
            seat.setStatus(SeatStatus.BOOKED);
            seat.setReservation(reservation);
        }
        reservation.setTotalAmount(calculateAmount(showSeats));

        reservation.setStatus(BookingStatus.CONFIRMED);
        reservation.setUser(user);
        reservationRepository.save(reservation);
        showSeatRepository.saveAll(showSeats);
        return new ReservationResponse(
                reservation.getId(),
                reservation.getShow().getMovie().getTitle(),
                reservation.getShow().getHall().getName(),
                reservation.getShow().getStartTime(),
                reservation.getShowSeats().stream().map(showSeat -> (showSeat.getSeat().getSeatNumber())).toList(),
                reservation.getTotalAmount(),
                reservation.getStatus(),
                reservation.getUser().getId()
        );
    }

    private BigDecimal calculateAmount(List<ShowSeat> showSeats) {
        BigDecimal totalAmount= BigDecimal.valueOf(0);
        for(ShowSeat seat: showSeats){
            totalAmount= totalAmount.add(seat.getPrice());
        }
        return totalAmount;
    }


    public Page<ReservationResponse> getReservations( int page,
                                                      int size,
                                                      String sortBy,
                                                      String direction) {

        Sort sort= direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page,size,sort);
        User user= getCurrentUser();

        Page<Reservation> reservations= reservationRepository.findByUser(pageable,user);
        return reservations.map(reservation ->
                new ReservationResponse(reservation.getId(),
                        reservation.getShow().getMovie().getTitle(),
                        reservation.getShow().getHall().getName(),
                        reservation.getShow().getStartTime(),
                        reservation.getShowSeats().stream().map(showSeat -> (showSeat.getSeat().getSeatNumber())).toList(),
                        reservation.getTotalAmount(),
                        reservation.getStatus(),reservation.getUser().getId()));

    }

    public ReservationResponse getReservation(Long id) {
        Reservation reservation= reservationRepository.findById(id).orElseThrow(()-> new ReservationException("Reservation not found."));
        User user= getCurrentUser();
        if(!user.getId().equals(reservation.getUser().getId())) throw new ReservationException("you are not authorized to view this request.");
        return new ReservationResponse(reservation.getId(),
                reservation.getShow().getMovie().getTitle(),
                reservation.getShow().getHall().getName(),
                reservation.getShow().getStartTime(),
                reservation.getShowSeats().stream().map(showSeat -> (showSeat.getSeat().getSeatNumber())).toList(),
                reservation.getTotalAmount(),
                reservation.getStatus(),reservation.getUser().getId());

    }

    @Transactional

    public String cancelReservation(Long id) {
        User user = getCurrentUser();
        Reservation reservation= reservationRepository.findById(id).orElseThrow(()-> new ReservationException("Reservation not found."));

        if (!user.getId().equals(reservation.getUser().getId())) {
            throw new ReservationException(
                    "You are not authorized to cancel this reservation."
            );
        }

        //free the created seats
        if(reservation.getStatus()==BookingStatus.CANCELLED){
            throw new ReservationAlreadyCancelledException("Reservation already has been cancelled.");
        }
        reservation.setStatus(BookingStatus.CANCELLED);
        for(ShowSeat seat: reservation.getShowSeats()){
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setReservation(null);
        }
        reservationRepository.save(reservation);
        showSeatRepository.saveAll(reservation.getShowSeats());
        return "Reservation cancelled successfully.";
    }

    public User getCurrentUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String email= authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User with email: "+ email+ " not found."));
    }

    //FOR ADMIN TO VIEW ALL RESERVATIONS CREATED:
    public Page<ReservationResponse> getAllReservations(
            int page,int size,String sortBy,String direction
    ){
        Sort sort= direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page,size,sort);

        return reservationRepository.findAll(pageable).map(
                reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getShow().getMovie().getTitle(),
                        reservation.getShow().getHall().getName(),
                        reservation.getShow().getStartTime(),
                        reservation.getShowSeats().stream().map(showSeat -> (showSeat.getSeat().getSeatNumber())).toList(),
                        reservation.getTotalAmount(),
                        reservation.getStatus(),reservation.getUser().getId()
                )
        );

    }

}







