package database.seeders;

import lombok.RequiredArgsConstructor;
import org.booking.roomtypes.RoomType;
import org.booking.roomtypes.RoomTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomTypeSeeder implements Seeder {

    private final RoomTypeRepository repository;

    @Override
    public void run(){
        List<String> roles = List.of("beds rooms", "Living rooms");
        List<RoomType> roleEntity = roles.stream()
                .map(RoomType::new)
                .toList();

        repository.saveAll(roleEntity);
    }
}
