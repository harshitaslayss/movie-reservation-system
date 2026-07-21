package project.movie_reservation_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.movie_reservation_system.entity.Hall;
import project.movie_reservation_system.entity.Seat;
import project.movie_reservation_system.entity.SeatType;
import project.movie_reservation_system.repository.SeatRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public void generateSeats(Hall hall, Long rows, Long seatsPerRow){
        List<Seat> seats= new ArrayList<>();
        for(int row=0;row<rows;row++){

            char rowLetter= (char)('A'+row);

            for(int i=1;i<=seatsPerRow;i++){
                Seat currSeat= new Seat();
                currSeat.setHall(hall);
                currSeat.setSeatNumber(String.valueOf(rowLetter)+ i);
                if('A'<=rowLetter && rowLetter<='C'){
                    currSeat.setSeatType(SeatType.RECLINER);
                }else if('D'<=rowLetter && rowLetter<='F'){
                    currSeat.setSeatType(SeatType.PREMIUM);
                }else{
                    currSeat.setSeatType(SeatType.REGULAR);
                }
                seats.add(currSeat);
            }

        }
        seatRepository.saveAll(seats);

    }
}
