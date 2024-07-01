package com.example.goodTripBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.utils.Utils;

import java.io.IOException;

@SpringBootApplication
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

}
