package database.seeders;

import database.factory.UserFactory;
import lombok.RequiredArgsConstructor;
import org.booking.user.User;
import org.booking.user.UserRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(value = 2)
@RequiredArgsConstructor
public class UserSeeder implements Seeder {

    private final UserRepository userRepository;
    private final UserFactory userFactory;

    @Override
    public void run() {
        if (userRepository.count() == 0){
            List<User> users = userFactory.create(100);
            userRepository.saveAll(users);
        }
    }
}
