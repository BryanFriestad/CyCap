package com.cycapservers.game.components.drawing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import com.cycapservers.JSON_returnable;

public class Image implements JSON_returnable 
{
	private String src;
	private int num_sprites;
	
	private Image(String source, int num_sprites) 
	{
		this.src = source;
		this.num_sprites = num_sprites;
	}
	
	public int GetNumberOfSprites()
	{
		return num_sprites;
	}

	@Override
	public JSONObject toJSONObject() 
	{
		JSONObject object = new JSONObject();
		object.put("src", this.src);
		return object;
	}
	
	private static List<Image> game_images = new ArrayList<Image>();
	
	public static Image GetImageBySrc(String src)
	{
		for (Image i : game_images)
		{
			if (i.src.equals(src)) return i;
		}
		throw new IllegalArgumentException("no image exists with that source path.");
	}
	
	public static void LoadGameImages() throws IOException
	{
		ClassPathResource resource = new ClassPathResource("static/scripts/images.json");
		InputStream i_stream = resource.getInputStream();
		
		StringBuilder builder = new StringBuilder();
		int CR = 13;
		int LF = 10;
		int data;
		while ((data = i_stream.read()) != -1)
		{
			if (!(data == CR || data == LF))
			{
				builder.append(Character.toString((char) data));
			}
		}
		i_stream.close();
//		System.out.println("Images file text: " + builder.toString());
		JSONObject json = new JSONObject(builder.toString());
		
		JSONArray array = json.getJSONArray("images");
		for (int index = 0; index < array.length(); index++)
		{
			JSONObject image = array.getJSONObject(index);
			JSONObject params = image.getJSONObject("params");
			Image i = new Image(image.getString("src"), params.getInt("columns") * params.getInt("rows"));
//			System.out.println("Adding image: " + i.toJSONObject().toString());
			game_images.add(i);
		}
	}

}
