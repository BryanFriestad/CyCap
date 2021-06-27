let gameState;

function InitializeGameState(initial_game_state)
{
	gameState = initial_game_state;
	
	// setting these because later parts of the function use them.
	gameState.player = {};
	gameState.player.x = 0;
	gameState.player.y = 0;
	
	// call initialize functions for each of the intermittent entities
	if (gameState.intermittent_entities)
	{
		for (let i = 0; i < gameState.intermittent_entities.length; i++)
		{
			InitializeInterpolatingEntity(gameState.intermittent_entities[i]);
		}
	}
	
	// if there are no persistent entities to start, make an empty list
	if (!gameState.persistent_entities)
	{
		gameState.persistent_entities = [];
	}
	
	// if there are no intermittent entities to start, make an empty list
	if (!gameState.intermittent_entities)
	{
		gameState.intermittent_entities = [];
	}
	
	gameState.last_message_time = Date.now(); // in ms
	
	// adds new persistent entities, removes "deleted" ones, begins interpolation of intermittent ones, adds new intermittent entities.
	gameState.ReceiveNewGameState = function(new_game_state_obj)
	{
		let delta_message_time = Date.now() - this.last_message_time;
		
		// add new persistent entities
		if (new_game_state_obj.persistent_entities)
		{
			for (let i = 0; i < new_game_state_obj.persistent_entities.length; i++)
			{
				this.persistent_entities.push(new_game_state_obj.persistent_entities[i]);
			}
		}
		
		if (new_game_state_obj.deleted_entities)
		{
			// remove deleted entities
			let i = 0;
			while (i < this.persistent_entities.length)
			{
				if (new_game_state_obj.deleted_entities.includes(this.persistent_entities[i].entity_id))
				{
					this.persistent_entities.splice(i, 1);
				}
				else
				{
					i++;
				}
			}
		}
		
		let updated_list = [];
		if (new_game_state_obj.intermittent_entities)
		{
			for (let i = 0; i < new_game_state_obj.intermittent_entities.length; i++)
			{
				let entity_found = false;
				
				// if the entity id already exists, call UpdateInterpolation(). Add to updated list.
				for (let j = 0; j < this.intermittent_entities.length; j++)
				{
					if (this.intermittent_entities[j].entity_id === new_game_state_obj.intermittent_entities[i].entity_id)
					{
						entity_found = true;
						this.intermittent_entities[j].UpdateInterpolation(new_game_state_obj.intermittent_entities[i], delta_message_time);
						updated_list.push(this.intermittent_entities[j]);
						break;
					}
				}
				
				// else, add to intermittent entity list of gameState and call InitializeInterpolatingEntity
				if (!entity_found)
				{
					InitializeInterpolatingEntity(new_game_state_obj.intermittent_entities[i]);
					updated_list.push (new_game_state_obj.intermittent_entities[i]);
				}
			}
		}
		// remove any not updated entities
		this.intermittent_entities = updated_list;
		
	}
	
	// updates all of the intermittent entities.
	gameState.Update = function()
	{
		for (let i = 0; i < this.intermittent_entities.length; i++)
		{
			this.intermittent_entities[i].Update();
		}
	}
	
	// draws all entities.
	gameState.Draw = function()
	{
		for (let i = 0; i < this.persistent_entities.length; i++)
		{
			RenderDrawingComponent(this.persistent_entities[i].model, this.persistent_entities[i].position);
		}
		
		for (let i = 0; i < this.intermittent_entities.length; i++)
		{
			RenderDrawingComponent(this.intermittent_entities[i].model, this.intermittent_entities[i].position);
		}
	}
}