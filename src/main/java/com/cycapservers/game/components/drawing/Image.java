package com.cycapservers.game.components.drawing;

import com.cycapservers.JsonToStringObject;
import com.cycapservers.JSON_Stringable;
import com.cycapservers.game.Utils;

public class Image implements JSON_Stringable {
	
	//params
	private String src;
	private int imageCode;

	//internal
	private ImageSprite[] sprites;
	
	public Image(String source, int img_code) {
		this.src = source;
		this.imageCode = img_code;
		this.sprites = Utils.generateSpriteData(Utils.GRID_LENGTH, Utils.GRID_LENGTH, 1, 1);
	}
	
	public Image(String source, int img_code, int image_width, int image_height) {
		this.src = source;
		this.imageCode = img_code;
		this.sprites = Utils.generateSpriteData(image_width, image_height, 1, 1);
	}
	
	public Image(String source, int img_code,  int image_width, int image_height, int sprite_rows, int sprite_cols) {
		this.src = source;
		this.imageCode = img_code;
		this.sprites = Utils.generateSpriteData(image_width, image_height, sprite_rows, sprite_cols);
	}
	
	/**
	 * 
	 * @return the length of the sprites array in this image.
	 */
	public int getSpritesLength(){
		return sprites.length;
	}
	
	private void cloneSprites(ImageSprite[] arr) {
		sprites = new ImageSprite[arr.length];
		for(int i = 0; i < sprites.length; i++) {
			sprites[i] = arr[i].clone();
		}
	}

	@Override
	public String toJSONString() {
		JsonToStringObject object = new JsonToStringObject();
		object.put("class", this.getClass().getSimpleName());
		object.put("src", this.src);
		object.put("code", this.imageCode);
		object.put("sprites", this.sprites);
		return object.toString();
	}
	
	@Override
	public Image clone() {
		Image i = new Image(String.valueOf(src), imageCode, 0, 0);
		i.cloneSprites(sprites); //clones this object's sprites into the new one
		return i;
	}

}
