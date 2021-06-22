package com.cycapservers.game.components.drawing;

import org.json.JSONObject;

import com.cycapservers.JSON_returnable;
import com.cycapservers.game.Utils;

public class Image implements JSON_returnable {
	
	//params
	private String src;
	private int imageCode;

	//internal
	private ImageSprite[] sprites;
	
	public Image(String source, int img_code) 
	{
		this.src = source;
		this.imageCode = img_code;
		this.sprites = Utils.generateSpriteData(Utils.GRID_LENGTH, Utils.GRID_LENGTH, 1, 1);
	}
	
	public Image(String source, int img_code, int image_width, int image_height) 
	{
		this.src = source;
		this.imageCode = img_code;
		this.sprites = Utils.generateSpriteData(image_width, image_height, 1, 1);
	}
	
	public Image(String source, int img_code,  int sprite_width, int sprite_height, int sprite_rows, int sprite_cols) 
	{
		this.src = source;
		this.imageCode = img_code;
		this.sprites = Utils.generateSpriteData(sprite_width, sprite_height, sprite_rows, sprite_cols);
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
	public JSONObject toJSONObject() {
		JSONObject object = new JSONObject();
		object.put("class", this.getClass().getSimpleName());
		object.put("src", this.src);
		object.put("code", this.imageCode);
		object.put("sprites", GetSpritesAsJsonObjectArray());
		return object;
	}
	
	private JSONObject[] GetSpritesAsJsonObjectArray()
	{
		JSONObject[] arr = new JSONObject[sprites.length];
		for (int i = 0; i < sprites.length; i++)
		{
			arr[i] = sprites[i].toJSONObject();
		}
		return arr;
	}
	
	@Override
	public Image clone() {
		Image i = new Image(String.valueOf(src), imageCode, 0, 0);
		i.cloneSprites(sprites); //clones this object's sprites into the new one
		return i;
	}

}
