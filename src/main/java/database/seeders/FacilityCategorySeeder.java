package database.seeders;

import lombok.RequiredArgsConstructor;
import org.booking.facilityCategories.FacilityCategory;
import org.booking.facilityCategories.FacilityCategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FacilityCategorySeeder implements Seeder {

    private final FacilityCategoryRepository repository;

    @Override
    public void run(){
        List<String> categories = List.of("Bedroom", "Kitchen", "Bathroom", "rooms Amenities", "General", "Media & Technology");
        List<FacilityCategory> category = categories.stream()
                .map(FacilityCategory::new)
                .toList();

        repository.saveAll(category);
    }
}
