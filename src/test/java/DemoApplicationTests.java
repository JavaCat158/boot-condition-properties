import netology.taskboot.SpringBootDemoApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SpringBootDemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Container
    private static GenericContainer<?> myapp = new GenericContainer<>("devapp")
            .withExposedPorts(8080)
            .withEnv("SERVER_PORT", "8080");

    @Container
    private static GenericContainer<?> myAppProd = new GenericContainer<>("prodapp")
            .withExposedPorts(8081)
            .withEnv("SERVER_PORT", "8081");

    @Test
    void contextMyApp() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + myapp.getMappedPort(8080) + "/profile", String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Current profile is dev", responseEntity.getBody());

        System.out.println("Dev " + responseEntity.getBody());
    }

    @Test
    void contextProdMyAapp() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + myAppProd.getMappedPort(8081) + "/profile", String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Current profile is production", responseEntity.getBody());

        System.out.println("Production " + responseEntity.getBody());
    }
}
