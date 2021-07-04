package com.cycapservers.game.components.input;

import com.cycapservers.game.components.Component;

/**
 * A component which allow an entity to move based on some form of input.
 * @author Bryan Friestad
 *
 */
public abstract class InputComponent extends Component
{
	public InputComponent()
	{
		super("input");
	}
}
