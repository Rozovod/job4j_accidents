package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.accidents.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority findByAuthority(String authority);
}
