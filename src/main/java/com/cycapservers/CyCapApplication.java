package com.cycapservers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cycapservers.game.GameImage;
import com.cycapservers.game.GamePosition;
import com.cycapservers.game.GridLockedGamePosition;

@SpringBootApplication
public class CyCapApplication {

    public static void main(String[] args) throws Exception {
    	GameImage im = new GameImage("res/images/flags.png", 4, 128, 128, 2, 4);
    	System.out.println(im.toJSONString());
//        SpringApplication.run(CyCapApplication.class, args);
    }
}
