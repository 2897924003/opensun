package ss;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableRabbit
@EnableMethodSecurity(securedEnabled = true)
@EnableScheduling
@EnableCaching
public class SSApplication {

    void main() {
        SpringApplication.run(SSApplication.class);
    }


}
