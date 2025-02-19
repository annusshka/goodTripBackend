package com.example.goodTripBackend;

import com.example.goodTripBackend.features.user.models.entities.UserRole;
import com.example.goodTripBackend.features.user.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.utils.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class GoodTripBackendApplication {

    public static void main(String[] args) {
        ImageKit imageKit = ImageKit.getInstance();
        Configuration config;
        try {
            config = Utils.getSystemConfig(GoodTripBackendApplication.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageKit.setConfig(config);

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
