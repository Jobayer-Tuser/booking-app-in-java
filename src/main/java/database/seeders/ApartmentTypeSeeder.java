package database.seeders;

import lombok.RequiredArgsConstructor;
import org.booking.apartmentTypes.ApartmentType;
import org.booking.apartmentTypes.ApartmentTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApartmentTypeSeeder implements Seeder {

    private final ApartmentTypeRepository repository;

    @Override
    public void run(){
        List<String> roles = List.of("Entire apartment", "Entire studio", "Private suite");
        List<ApartmentType> roleEntity = roles.stream()
                .map(ApartmentType::new)
                .toList();

        repository.saveAll(roleEntity);
    }
}
