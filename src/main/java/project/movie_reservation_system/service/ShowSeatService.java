package project.movie_reservation_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.movie_reservation_system.dto.ShowSeatResponse;
import project.movie_reservation_system.entity.*;
import project.movie_reservation_system.exception.ShowException;
import project.movie_reservation_system.repository.ShowRepository;
import project.movie_reservation_system.repository.ShowSeatRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowSeatService {
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    public void generateShowSeats(Show show){
        List<ShowSeat> showSeats= new ArrayList<>();
        List<Seat> seats= show.getHall().getSeats();
        for(Seat seat: seats){
            ShowSeat showSeat= new ShowSeat();
            showSeat.setShow(show);
            showSeat.setSeat(seat);

            showSeat.setPrice(getPrice(seat.getSeatType()));

            showSeat.setStatus(SeatStatus.AVAILABLE);
            showSeats.add(showSeat);
        }

        showSeatRepository.saveAll(showSeats);

    }

    public BigDecimal getPrice(SeatType seatType){
        return switch (seatType){

            case REGULAR -> (BigDecimal.valueOf(250));

            case PREMIUM ->
                    (BigDecimal.valueOf(400));

            case RECLINER ->
                    (BigDecimal.valueOf(550));


        };
    }

    public List<ShowSeatResponse> getShowSeats(Long id) {
        showRepository.findById(id).orElseThrow(()-> new ShowException("Show with id: "+id+" not found"));
        List<ShowSeat> showSeats= showSeatRepository.findByShowId(id);
        return showSeats.stream().map(
                showSeat -> new ShowSeatResponse(
                        showSeat.getId(),
                        showSeat.getPrice(),
                        showSeat.getStatus(),
                        showSeat.getSeat().getSeatNumber(),
                        showSeat.getSeat().getSeatType()
                )
        ).toList();
    }
}
