package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private Map<Integer, User> repository = new HashMap<>();
    private AtomicInteger count = new AtomicInteger(0);

    {
        save(new User("Petya", "petya@yandex.ru", "12345", Role.ADMIN));
        save(new User("Vasya", "vasya@yandex.ru", "12345", Role.ADMIN, Role.USER));
        save(new User("Vanya", "vanya@yandex.ru", "12345", Role.USER));
        save(new User("Kostya", "kostya@yandex.ru", "12345", Role.ADMIN, Role.USER));
        save(new User("Sasha", "sasha@yandex.ru", "12345", Role.ADMIN, Role.USER));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(count.incrementAndGet());
            return repository.put(user.getId(), user);
        } else
            repository.computeIfPresent(user.getId(), (id, old_user) -> user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return (List<User>) repository.values();
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository
                .values()
                .stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }
}
