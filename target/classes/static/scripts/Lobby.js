var serverSocket;

document.getElementById("name").innerHTML = sessionStorage.getItem("type");
var gameType = sessionStorage.getItem("type");

msg = new ServerMessage("lobby");
msg.addData("game_type", gameType);
msg.addData("client_id", client_id);
connectToServer(msg);

var gameId = "";
var players = 1; 
var x;

function refresh()
{
	players = 0;
	sendMessageToServer("lobby:playerList:" + gameId);
}


function recruit()
{
	sendMessageToServer("lobby:role:" + gameId + ":" + client_id + ":recruit");
}

function infantry()
{
	sendMessageToServer("lobby:role:" + gameId + ":" + client_id + ":infantry");
}

function scout()
{
	sendMessageToServer("lobby:role:" + gameId + ":" + client_id + ":scout");
}

function artillery()
{
	sendMessageToServer("lobby:role:" + gameId + ":" + client_id + ":artillery");
}

function changeTimer(ms)
{
	clearInterval(x);
	x = setInterval(function() 
	{
		ms = ms - 1000;

		var minutes = Math.floor(ms / 60000);
		var seconds = Math.floor((ms % 60000) / 1000);
		if (seconds < 10)
		{
			document.getElementById("time").innerHTML = "Time Until Start - " + minutes + ":0" + seconds;  
		}
		else
		{
			// Display the result in the element with id="demo"
			document.getElementById("time").innerHTML = "Time Until Start - " + minutes + ":" + seconds;
		}
	}, 1000);
}

function updatePlayerList(name, role, team) 
{
	//console.log(name);
    var p = document.createElement("P");
	if (team == 1)
	{
		p.style.backgroundColor = "red";
	}
	else if (team == 2)
	{
		p.style.backgroundColor = "blue";
	}
	else if (team == 3)
	{
		p.style.backgroundColor = "green";
	}
	else if (team == 4)
	{
		p.style.backgroundColor = "yellow";
	}
	else if (team == 5)
	{
		p.style.backgroundColor = "purple";
	}
	else if (team == 6)
	{
		p.style.backgroundColor = "pink";
	}
	else if (team == 7)
	{
		p.style.backgroundColor = "orange";
	}
	else if (team == 8)
	{
		p.style.backgroundColor = "LightSkyBlue";
	}
    var t = document.createTextNode(name + " - " + role);
    p.appendChild(t);
    document.getElementById("play").appendChild(p);
}
