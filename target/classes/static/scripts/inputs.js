function InputHandler()
{	
	this.canvasX = 0;
	this.canvasY = 0;
	this.mapX = 0;
	this.mapY = 0;
	this.mouse_clicked = false;
	this.lmb_down = false;
	
	this.keys_down = []; //keys being pressed
	this.keys_pnr = []; //keys that have been pushed and released
	this.clientPredictiveState = []; //this is a list of all of the past input snapshots that have not been handled by the Server
	this.snapshotNum = 1;
	
	this.update = function()
	{
		this.mapX = (this.canvasX - gt5) / gt1;
		this.mapY = (this.canvasY - gt6) / gt4;
	}
	
	this.getSnapshot = function()
	{
		//compile input data
		let snap = new InputSnapshot(this.mapX, this.mapY, this.canvasX, this.canvasY, this.mouse_clicked, this.lmb_down, this.keys_down, this.keys_pnr, this.snapshotNum);
		
		//push the snapshot to the list
		this.clientPredictiveState.push(snap);
		
		//increment shapshotNum++
		this.snapshotNum++;
		
		//return object
		return snap;
	}
	
	this.getMostRecentInput = function()
	{
		let pos = this.clientPredictiveState.length - 1;
		return this.clientPredictiveState[pos];
	}
	
	//this takes in the highest snapshot number handled by the server and removes everything before that from the prediction
	this.removeHandledSnapshots = function(highestSnap)
	{
		for (let i = 0; i < this.clientPredictiveState.length; i++)
		{
			if (this.clientPredictiveState[i].snapshotNum == highestSnap)
			{
				this.clientPredictiveState.splice(0, i+1);
				break;
			}
		}
	}
	
	this.endOfFrameCleanup = function()
	{
		//reset the 1 frame inputs
		this.keys_pnr.splice(0, input_handler.keys_pnr.length);
		this.mouse_clicked = false;
	}
}

function InputSnapshot(mapX, mapY, canvasX, canvasY, mouse_clicked, lmb_down, keys_down, keys_pnr, num)
{
	this.game_id = gameState.game_id;
	this.input_code = gameState.passcode; 
	this.mapX = mapX;
	this.mapY = mapY;
	this.canvasX = canvasX;
	this.canvasY = canvasY;
	this.mouse_clicked = mouse_clicked;
	this.lmb_down = lmb_down;
	
	this.keys_down = keys_down; //keys being pressed
	this.keys_pnr = keys_pnr; //keys that have been pushed and released
	
	this.snapshotNum = num;
	this.frameTime = global_delta_t; //the amount of time associated with this frame in seconds
}

function SetupInputHandling()
{
	input_handler = new InputHandler();
	
	//when a key goes down it is added to a list and when it goes up its taken out
	document.addEventListener("keydown", function(event) 
	{
		if (!input_handler.keys_down.includes(event.keyCode)) 
		{
			input_handler.keys_down.push(event.keyCode);
		}
	});
	
	document.addEventListener("keyup", function(event) 
	{
		input_handler.keys_down.splice(input_handler.keys_down.indexOf(event.keyCode), 1);
		input_handler.keys_pnr.push(event.keyCode);
	});
	
	//mouse click listener
	document.addEventListener("click", function(event) 
	{
		input_handler.mouse_clicked = true;
	});
	
	//left mouse button listener
	document.addEventListener("mousedown", function(event) 
	{
		if(event.button == 0)
		{
			input_handler.lmb_down = true;
		}
	});
	
	document.addEventListener("mouseup", function(event) 
	{
		if(event.button == 0)
		{
			input_handler.lmb_down = false;
		}
	});
	
	window.addEventListener('mousemove', function(event)
	{
		this.rect = canvas.getBoundingClientRect();
		input_handler.canvasX = (event.clientX - rect.left);
		input_handler.canvasY = (event.clientY - rect.top);
	}, false);
}