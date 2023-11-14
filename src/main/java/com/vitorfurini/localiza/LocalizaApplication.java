package com.vitorfurini.localiza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication()
@EnableSwagger2
@EnableMongoAuditing
public class LocalizaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalizaApplication.class, args);
    }

}
