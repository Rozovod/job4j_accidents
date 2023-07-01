package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.accidents.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
