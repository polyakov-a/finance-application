package by.itacademy.account.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "by.itacademy.account.scheduler.dao")
@EnableTransactionManagement
@ConfigurationPropertiesScan("by.itacademy.account.scheduler.config")
public class AccountSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountSchedulerApplication.class, args);
    }

}
