package database.seeders;

import database.factory.UserFactory;
import lombok.RequiredArgsConstructor;
import org.booking.User.User;
import org.booking.User.UserRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserSeeder implements Seeder {

    private final UserRepository userRepository;
    private final UserFactory userFactory;

    @Override
    @Transactional
    public void run() {
        if (userRepository.count() == 0) {
            List<User> users = userFactory.create(100);
            userRepository.saveAll(users);
        }
    }
}
