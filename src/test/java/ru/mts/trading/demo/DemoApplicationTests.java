package ru.mts.trading.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.mts.trading.demo.model.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = {DemoApplicationTests.Initializer.class})
@ActiveProfiles(value = "test")
@Testcontainers
class DemoApplicationTests {

	@Container
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
			.withDatabaseName("mydb")
			.withUsername("myuser")
			.withPassword("mypass");

	@Autowired
	private DbUtil dbUtil;

	@Test
	void testGetUsersList() {
		User newUser = new User();
		newUser.setId(UUID.randomUUID());
		newUser.setName("test-name");
		newUser.setRole("test-role");
		dbUtil.createRecord(newUser);

		String url = "http://localhost:8080/api/v1/users";
		RestTemplate restTemplate = new RestTemplate();
		String responseBody = restTemplate.getForObject(url, String.class);
		assertEquals("[{\"name\":\"test-name\",\"role\":\"test-role\"}]", responseBody);
	}

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
					"spring.datasource.username=" + postgreSQLContainer.getUsername(),
					"spring.datasource.password=" + postgreSQLContainer.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

}
