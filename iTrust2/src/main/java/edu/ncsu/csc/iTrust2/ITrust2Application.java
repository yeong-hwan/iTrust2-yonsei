package edu.ncsu.csc.iTrust2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import springfox.documentation.swagger2.annotations.EnableSwagger2;

// @EnableSwagger2
@SpringBootApplication(scanBasePackages = { "edu.ncsu.csc.iTrust2" })
public class ITrust2Application {
    public static void main(final String[] args) {
        SpringApplication.run(ITrust2Application.class, args);
    }

}
