package com.cycapservers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cycapservers.game.GamePosition;
import com.cycapservers.game.GridLockedGamePosition;

@SpringBootApplication
public class CyCapApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CyCapApplication.class, args);
    }
}
