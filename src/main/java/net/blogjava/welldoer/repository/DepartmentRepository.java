package net.blogjava.welldoer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.blogjava.welldoer.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
