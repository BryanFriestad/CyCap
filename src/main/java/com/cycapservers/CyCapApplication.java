package com.cycapservers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cycapservers.game.components.drawing.Image;
import com.cycapservers.game.components.positioning.GridLockedPositionComponent;
import com.cycapservers.game.components.positioning.PositionComponent;

@SpringBootApplication
public class CyCapApplication {

    public static void main(String[] args) throws Exception {
//    	Image im = new Image("res/images/flags.png", 4, 128, 128, 2, 4);
//    	System.out.println(im.toJSONString());
        SpringApplication.run(CyCapApplication.class, args);
    }
}
