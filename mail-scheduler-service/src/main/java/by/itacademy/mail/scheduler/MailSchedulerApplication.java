package by.itacademy.mail.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "by.itacademy.mail.scheduler.dao")
@EnableTransactionManagement
@ConfigurationPropertiesScan("by.itacademy.mail.scheduler.config")
public class MailSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailSchedulerApplication.class, args);
    }

}
