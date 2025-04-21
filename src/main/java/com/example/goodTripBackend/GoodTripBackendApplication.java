package com.example.goodTripBackend;

import com.example.goodTripBackend.features.user.models.entities.UserRole;
import com.example.goodTripBackend.features.user.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.utils.Utils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EnableConfigurationProperties
public class GoodTripBackendApplication {

    public static void main(String[] args) {
        System.setProperty("okhttp3.connectTimeout", "60000");
        System.setProperty("okhttp3.readTimeout", "60000");
        System.setProperty("okhttp3.writeTimeout", "90000");

        ImageKit imageKit = ImageKit.getInstance();
        Configuration config;
        try {
            config = Utils.getSystemConfig(GoodTripBackendApplication.class);
            imageKit.setConfig(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SpringApplication.run(GoodTripBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByRole("USER").isEmpty()) {
                roleRepository.save(
                        UserRole.builder().role("USER").build()
                );
            }
        };
    }
}
