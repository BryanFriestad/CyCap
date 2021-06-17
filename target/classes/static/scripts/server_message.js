function ServerMessage(type)
{
	this.msg_type = type;
	
	this.addData = function(name, value)
	{
		this[name] = value;
	}
}

function connectToServer(initial_msg)
{
	serverSocket = new WebSocket('ws://' + window.location.host + '/my-websocket-endpoint');
	serverSocket.onopen = function() 
	{
		sendMessageToServer(initial_msg);
	};
	serverSocket.onmessage = message_handler;
}

function sendMessageToServer(msg)
{
	serverSocket.send(JSON.stringify(msg));
}

//event listener for when the socket receives a message from the server
function message_handler(msg)
{
	let temp = msg.data.split(":");
	
	if(temp[0] == "join")
	{
		setup(temp);
		requestAnimationFrame(run); //more synchronized method similar to setInterval
	}
	else if(temp[0] == "endgame")
	{
		//temp[1] is w/l
		//temp[2] is gained xp
		//temp[3] is current class
		//temp[4] is kills
		//temp[5] is deaths
		//temp[6] is new level
		//"Your team [won/lost]! During the game, you killed [kills] enemies and died [deaths] times. You gained [xp] xp with the [class] class. Your new level is [level]."
		if (temp[1] == "w")
		{
			alert("Your team won! During the game, you killed " + temp[4] + " enemies and died " + temp[5] + " times. You gained " + temp[2] + " xp with the " + temp[3] + " class. Your new level is " + temp[6] + ".");
		}
		else
		{
			alert("Your team lost! During the game, you killed " + temp[4] + " enemies and died " + temp[5] + " times. You gained " + temp[2] + " xp with the " + temp[3] + " class. Your new level is " + temp[6] + ".");
		}
		window.location.href = "/game_list";
	}
	else
	{
		gameState.receiveGameState(msg.data);
	}
}


//event listener for when the socket receives a message from the server
//TODO: fix this based on the new model
function message_handler(msg)
{
	//var i = 1;
	let temp = msg.data.split(":");
	if(temp[0] == "player")
	{
		document.getElementById("play").innerHTML = "";
		players = 0;
		for(let i = 1; i < temp.length; i+=3)
		{
			updatePlayerList(temp[i], temp[i + 1], temp[i + 2]);
			players++;
		}
		document.getElementById("players").innerHTML = "Number of Players: " + players;
	}
	else if(temp[0] == "joined")
	{
		gameId = temp[1];
		console.log(gameId);
	}
	else if(temp[0] == "play")
	{
		window.location.href = "play";
	}
	else if(temp[0] == "role")
	{
		if(temp[1] != "no")
		{
			document.getElementById("role").innerHTML = "Role: " + temp[1];
		}
	}
	else if(temp[0] == "time")
	{
		changeTimer(parseInt(temp[1]));
	}
}