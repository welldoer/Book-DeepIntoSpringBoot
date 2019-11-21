package net.blogjava.welldoer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.blogjava.welldoer.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
