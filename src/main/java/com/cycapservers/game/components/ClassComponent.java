package com.cycapservers.game.components;

import com.cycapservers.game.CharacterClass;

public class ClassComponent extends Component 
{
	private CharacterClass c;
	
	public ClassComponent(CharacterClass c)
	{
		super("class");
		this.c = c;
	}
	
	@Override
	public void Receive(ComponentMessage message) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean Update(long delta_t) 
	{
		return true;
	}

	@Override
	public Object GetJSONValue() 
	{
		return c;
	}

	public CharacterClass GetCharacterClass()
	{
		return c;
	}
}
