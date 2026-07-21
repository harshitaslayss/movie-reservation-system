package project.movie_reservation_system.service;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import project.movie_reservation_system.dto.TheatreRequest;
import project.movie_reservation_system.dto.TheatreResponse;
import project.movie_reservation_system.entity.Theatre;
import project.movie_reservation_system.exception.TheatreException;
import project.movie_reservation_system.repository.TheatreRepository;
import project.movie_reservation_system.specification.TheatreSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreService {
    private final TheatreRepository theatreRepository;
    public TheatreResponse createTheatre(TheatreRequest theatreRequest) {
        Theatre theatre= new Theatre();
        theatre.setName(theatreRequest.getName());
        theatre.setAddress(theatreRequest.getAddress());
        theatreRepository.save(theatre);
        return new TheatreResponse(theatre.getId(),theatre.getName(),theatre.getAddress(),theatre.getCity());
    }


    @CachePut(value = "THEATRE_CACHE",key = "#result.id()")
    public TheatreResponse updateTheatre(Long id,  TheatreRequest theatreRequest) {
        Theatre theatre= theatreRepository.findById(id).orElseThrow(()-> new TheatreException("Theatre with id: "+id+" not found."));
        theatre.setName(theatreRequest.getName());
        theatre.setAddress(theatreRequest.getAddress());
        theatreRepository.save(theatre);
        return new TheatreResponse(theatre.getId(),theatre.getName(),theatre.getAddress(),theatre.getCity());
    }

    @CacheEvict(value = "THEATRE_CACHE",key = "#id")
    public  String deleteTheatre(Long id) {
        Theatre theatre= theatreRepository.findById(id).orElseThrow(()-> new TheatreException("Theatre with id: "+id+" not found."));
        theatreRepository.delete(theatre);
        return ("Theatre successfully deleted");
    }

    public Page<TheatreResponse> getTheatres(int page, int size, String sortBy, String direction,String name, String city) {
        Sort sort= direction.equalsIgnoreCase("desc")? Sort.by(sortBy).descending(): Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page,size,sort);

        Specification<Theatre> spec= Specification.allOf();

        if(name!=null && !name.isBlank()){
           spec= spec.and(TheatreSpecification.hasName(name));
        }

        if(city!=null && !city.isBlank()){
            spec= spec.and(TheatreSpecification.hasCity(city));
        }

        Page<Theatre> theatres= theatreRepository.findAll(spec,pageable);


        return theatres.map(theatre -> new TheatreResponse(theatre.getId(),theatre.getName(),theatre.getAddress(),theatre.getCity()));
    }

    @Cacheable(value = "THEATRE_CACHE",key = "#id")
    public TheatreResponse getTheatre(Long id){
        Theatre theatre= theatreRepository.findById(id).orElseThrow(()-> new TheatreException("Theatre not found."));
        return new TheatreResponse(theatre.getId(),theatre.getName(),theatre.getAddress(),theatre.getCity());
    }
}
