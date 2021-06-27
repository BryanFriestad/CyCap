package com.cycapservers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cycapservers.game.components.drawing.Image;
import com.cycapservers.game.components.positioning.GridLockedPositionComponent;
import com.cycapservers.game.components.positioning.PositionComponent;

@SpringBootApplication
public class CyCapApplication 
{
    public static void main(String[] args) throws Exception 
    {
        SpringApplication.run(CyCapApplication.class, args);
    }
}
