package mate.academy.rickandmorty;

import java.util.Random;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    Random random() {
        return new Random();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
