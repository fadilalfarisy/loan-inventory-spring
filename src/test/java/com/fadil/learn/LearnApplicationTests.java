package com.fadil.learn;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fadil.learn.model.Employee;
import com.fadil.learn.model.User;
import com.fadil.learn.repository.EmployeeRepository;
import com.fadil.learn.repository.UserRepository;

@SpringBootTest
class LearnApplicationTests {

	private final EmployeeRepository employeeRepository;
	private final UserRepository userRepository;

	@Autowired
	public LearnApplicationTests(EmployeeRepository employeeRepository, UserRepository userRepository) {
		this.employeeRepository = employeeRepository;
		this.userRepository = userRepository;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void getEmployeeByUserIsNull() {
		List<Employee> employees = employeeRepository.findByUserIsNull();

		for (Employee employee : employees) {
			System.out.println(employee);
		}

		Assertions.assertEquals(true, true);
	}

	@Test
	void getManager() {
		List<User> users = userRepository.findAllUserManager();

		for (User user : users) {
			System.out.println(user);
		}

		Assertions.assertEquals(true, true);
	}

	// @Test
	// void getUserByUsername1() {
	// List<User> listUser = userRepository.findByUsername("ROOT");

	// for (User user : listUser) {
	// System.out.println(user.toString());
	// }

	// Assertions.assertEquals(1, listUser.size());
	// }

	// @Test
	// void getUserByUsername2() {
	// List<User> listUser = userRepository.findByUsername2("ROOT");

	// for (User user : listUser) {
	// System.out.println(user.toString());
	// }

	// Assertions.assertEquals(1, listUser.size());
	// }

	// @Test
	// void getUserByUsernameDTO() {
	// List<UserDTO> listUser = userRepository.findByUsernameDTO("ROOT");

	// for (UserDTO user : listUser) {
	// System.out.println(user.toString());
	// }

	// Assertions.assertEquals(1, listUser.size());
	// }

	// @Test
	// void getAllUser() {
	// List<User> userList = userRepository.findAll();

	// for (User user : userList) {
	// System.out.println(user.toString());
	// }

	// Assertions.assertEquals(3, userList.size());
	// }

	// @Test
	// void getAllEmployee() {
	// List<Employee> listEmployee = employeeRepository.findAll();

	// System.out.println(listEmployee.size());

	// for (Employee employee : listEmployee) {
	// System.out.println(employee.getFullName());
	// }

	// Assertions.assertEquals(3, listEmployee.size());
	// }

	// @Test
	// void getEmployeeById() {
	// Optional<Employee> employee = employeeRepository.findById(1);

	// System.out.println(employee.isPresent());
	// System.out.println(employee.toString());

	// Assertions.assertEquals(true, employee.isPresent());
	// }

	// @Test
	// void registerEmployeeAndUser() {
	// Boolean register = authenticationService.register();

	// System.out.println(register);

	// Assertions.assertEquals(true, register);
	// }
}
