package org.acme.rest.repository;

import org.acme.rest.model.User;

import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Singleton
public class UserRepository {

    final Random random = new Random();

    private Map<Integer, User> users = new HashMap<>();

    public User create() {
        final User user = new User(random.nextInt(9999));
        users.put(user.getId(), user);
        return user;
    }

    public Collection<User> getAll() {
        return users.values();
    }

    public User getById(Integer userId) {
        if(!users.containsKey(userId)) {
            throw new NotFoundException("UserId: " + userId);
        }
        return users.get(userId);
    }

    public void save(User user) {
        users.put(user.getId(), user);
    }

    public void delete(Integer userId) {
        users.remove(userId);
    }

    public Collection<? extends User> findAllByName(String name) {
        return users.values().stream().filter(u -> u.getName().equals(name)).collect(Collectors.toList());
    }

    public Collection<? extends User> findAllByEmail(String email) {
        return users.values().stream().filter(u -> u.getEmail().equals(email)).collect(Collectors.toList());
    }

    public Collection<? extends User> findAllBySurname(String surname) {
        return users.values().stream().filter(u -> u.getSurname().equals(surname)).collect(Collectors.toList());
    }
}
