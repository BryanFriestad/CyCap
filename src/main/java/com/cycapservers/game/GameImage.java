package com.cycapservers.game;

import java.util.Arrays;
import java.util.List;

import com.cycapservers.JSONObject;

public class GameImage implements JSON_Stringable {
	
	private String src;
	private GameImageSprite[] sprites;
	private int imageCode;

	public GameImage(String source, int img_code, int image_width, int image_height) {
		this.src = source;
		this.imageCode = img_code;
		this.sprites = Utils.generateSpriteData(image_width, image_height, 1, 1);
	}
	
	public GameImage(String source, int img_code,  int image_width, int image_height, int sprite_rows, int sprite_cols) {
		this.src = source;
		this.imageCode = img_code;
		this.sprites = Utils.generateSpriteData(image_width, image_height, sprite_rows, sprite_cols);
	}

	@Override
	public String toJSONString() {
		JSONObject object = new JSONObject();
		object.put("class", this.getClass().getSimpleName());
		object.put("src", this.src);
		object.put("code", this.imageCode);
		object.put("sprites", this.sprites);
		return object.toString();
	}
	
	

}
