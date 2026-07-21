package project.movie_reservation_system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import project.movie_reservation_system.dto.HallRequest;
import project.movie_reservation_system.dto.HallResponse;
import project.movie_reservation_system.entity.Hall;
import project.movie_reservation_system.entity.Theatre;
import project.movie_reservation_system.exception.HallException;
import project.movie_reservation_system.exception.TheatreException;
import project.movie_reservation_system.repository.HallRepository;
import project.movie_reservation_system.repository.TheatreRepository;
import project.movie_reservation_system.specification.HallSpecification;

@Service
@RequiredArgsConstructor
public class HallService {
    private final HallRepository hallRepository;
    private final TheatreRepository theatreRepository;
    private final SeatService seatService;

    @Transactional
    public HallResponse createHall(HallRequest hallRequest) {
        Hall hall= new Hall();
        hall.setName(hallRequest.getName());
        Theatre theatre= theatreRepository.findById(hallRequest.getTheatreId()).orElseThrow(()-> new TheatreException("Theatre with the given id "+ hallRequest.getTheatreId()+" not found."));
        hall.setTheatre(theatre);
        hallRepository.save(hall);
        seatService.generateSeats(hall, hallRequest.getRows(), hallRequest.getSeatsPerRow());
        Long totalSeats= hallRequest.getSeatsPerRow()*hallRequest.getRows();

        return new HallResponse(hall.getId(), hall.getName(), hall.getTheatre().getName(), totalSeats);
    }

    @CachePut(value = "HALL_CACHE",key = "#result.id()")
    public HallResponse updateHall(Long id,  HallRequest hallRequest) {
        Hall hall= hallRepository.findById(id).orElseThrow(()->new RuntimeException("Hall with id"+ id + " not found."));
        hall.setName(hallRequest.getName());
        Theatre theatre= theatreRepository.findById(hallRequest.getTheatreId()).orElseThrow(()-> new TheatreException("Theatre with the given id "+ hallRequest.getTheatreId()+" not found."));
        hall.setTheatre(theatre);
        hallRepository.save(hall);
        return new HallResponse(hall.getId(), hall.getName(), hall.getTheatre().getName(), (long) hall.getSeats().size());
    }

    @CacheEvict(value = "HALL_CACHE",key = "#id")
    public String deleteHall(Long id){

        Hall hall= hallRepository.findById(id).orElseThrow(()->new RuntimeException("Hall not found."));
        if (!hall.getShows().isEmpty()) {
            throw new HallException(
                    "Cannot delete hall because shows exist."
            );
        }
        hallRepository.delete(hall);
        return("Hall successfully deleted.");
    }

    @Cacheable(value = "HALL_CACHE",key = "#id")
    public HallResponse getHall(Long id){
        Hall hall= hallRepository.findById(id).orElseThrow(()-> new HallException("Hall not found."));
        return new HallResponse(hall.getId(),hall.getName(),hall.getTheatre().getName(),(long) hall.getSeats().size());
    }


    public Page<HallResponse> getHalls(int page, int size, String sortBy, String direction, String name, String theatreName) {
        Sort sort= direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page,size,sort);

        Specification<Hall> spec= Specification.allOf();
        if(name!=null && !name.isBlank()){
            spec= spec.and(HallSpecification.hasName(name));
        }
        if(theatreName!=null && !theatreName.isBlank()){
            spec= spec.and(HallSpecification.hasTheatreName(theatreName));
        }

        Page<Hall> halls= hallRepository.findAll(spec,pageable);
        return halls.map(hall -> new HallResponse(hall.getId(),hall.getName(),hall.getTheatre().getName(), (long) hall.getSeats().size()));
     }
}
