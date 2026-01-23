package database.seeders;

import lombok.RequiredArgsConstructor;
import org.booking.bedTypes.BedType;
import org.booking.bedTypes.BedTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BedTypeSeeder implements Seeder {

    private final BedTypeRepository repository;

    @Override
    public void run(){
        List<String> roles = List.of("Single bed", "Large double bed", "Extra large double bed", "Sofa bed");
        List<BedType> roleEntity = roles.stream()
                .map(BedType::new)
                .toList();

        repository.saveAll(roleEntity);
    }
}
