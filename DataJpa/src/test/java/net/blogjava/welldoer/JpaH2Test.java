package net.blogjava.welldoer;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import net.blogjava.welldoer.entity.Department;
import net.blogjava.welldoer.entity.Role;
import net.blogjava.welldoer.entity.User;
import net.blogjava.welldoer.repository.DepartmentRepository;
import net.blogjava.welldoer.repository.RoleRepository;
import net.blogjava.welldoer.repository.UserRepository;

@DataJpaTest
public class JpaH2Test {
	private static Logger logger = LoggerFactory.getLogger(JpaH2Test.class);

	@Autowired
	UserRepository userRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@BeforeEach
	public void setUp() throws Exception {
		userRepository.deleteAll();
		roleRepository.deleteAll();
		departmentRepository.deleteAll();
		
		Department department = new Department();
		department.setName("开发部");
		departmentRepository.save(department);
		assertNotNull(department.getId());
		
		Role role = new Role();
		role.setName("admin");
		roleRepository.save(role);
		assertNotNull(role.getId());
		
		User user = new User();
		user.setName("user");
		user.setCreatedate(new Date(System.currentTimeMillis()));
		user.setDepartment(department);
		List<Role> roles = roleRepository.findAll();
		assertNotNull(roles);
		user.setRoles(roles);
		
		userRepository.save(user);
		assertNotNull(user.getId());
	}

	@Test
	public void findPage() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
		Page<User> page = userRepository.findAll(pageable);
		assertNotNull(page);
		for(User user : page.getContent()) {
			logger.info("=====user===== user name: {}, department name: {}, role name: {}",
					user.getName(), user.getDepartment().getName(), user.getRoles().get(0).getName());
		}
	}

}
