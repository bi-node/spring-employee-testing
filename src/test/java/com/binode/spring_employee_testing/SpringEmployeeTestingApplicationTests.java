package com.binode.spring_employee_testing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SpringEmployeeTestingApplicationTests {

	@Test
	void contextLoads() {
	}

}
