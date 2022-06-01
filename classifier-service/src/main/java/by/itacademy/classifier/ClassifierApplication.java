package by.itacademy.classifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "by.itacademy.classifier.dao")
@EnableTransactionManagement
@ConfigurationPropertiesScan("by.itacademy.classifier.config")
public class ClassifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassifierApplication.class, args);
	}
}
