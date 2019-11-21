package net.blogjava.welldoer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.blogjava.welldoer.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
