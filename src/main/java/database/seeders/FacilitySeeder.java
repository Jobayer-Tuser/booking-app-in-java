package database.seeders;

import lombok.RequiredArgsConstructor;
import org.booking.facility.Facility;
import org.booking.facility.FacilityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FacilitySeeder implements Seeder {

    private final FacilityRepository repository;

    @Override
    public void run(){
        List<String> facilities = List.of("Linen", "Wardrobe or closet", "Electric kettle", "Microwave", "Washing mashine", "Private bathroom", "Shower", "Towels", "Drying rack for clothing", "No smoking", "Fan", "WiFi", "TV");
        List<Facility> category = facilities.stream()
                .map(Facility::new)
                .toList();

        repository.saveAll(category);
    }
}
