package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;


	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User user1 = new User("mk@gmail.com", "mk825409", "Mohak", "Kumar"); 
		user1.addRole(roleAdmin);

		User savedUser = repo.save(user1);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUserswithTwoRoles() {
		User user2 = new User("sk@gmail.com", "sk825409", "Akash", "Verma"); 
		Role roleSalesperson = new Role(2);
		Role roleShipper = new Role(4);
		user2.addRole(roleSalesperson);
		user2.addRole(roleShipper);

		User savedUser = repo.save(user2);

		assertThat(savedUser.getId()).isGreaterThan(0);
		//repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
	}

	@Test
	public void testListAllUsers() {

		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));

	}

	@Test
	public void testGetUserById() {
		User userNam = repo.findById(1).get();
		System.out.println(userNam);
		assertThat(userNam).isNotNull();
	}

	@Test
	public void testUpdateUserDetails() {
		User userNam = repo.findById(1).get();
		userNam.setEnabled(true);
		userNam.setEmail("mkjavaprogramming@gmail.com");

		repo.save(userNam);
	}

	@Test
	public void testUpdateUserRoles() {
		User userNam = repo.findById(2).get(); 
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		userNam.getRoles().remove(roleEditor);
		userNam.addRole(roleSalesperson);

		repo.save(userNam);

	}

	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);
	}


	@Test 
	public void testGetUserByEmail() { 
		String email =	"mkjavaprogramming@gmail.com"; 
		User user = repo.getUserByEmail(email);

			assertThat(user).isNotNull(); }

}
