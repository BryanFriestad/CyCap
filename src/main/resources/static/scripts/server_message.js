let serverSocket = {}; //the web socket to connect to the server with
let got_first_message = false;

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

function sendInputMessageToServer(input_snapshot)
{
	let msg = new ServerMessage("input");
	msg.addData("snapshot", input_snapshot);
	msg.addData("client_id", client_id);
	sendMessageToServer(msg);
}

function sendMessageToServer(msg)
{
	serverSocket.send(JSON.stringify(msg));
}

//event listener for when the socket receives a message from the server
function message_handler(msg)
{
	console.log("msg length: " + msg.data.length);
	let obj = JSON.parse(msg.data);
	//console.log(obj);
	
	if (!got_first_message)
	{
		console.log(obj);
		got_first_message = true;
		setup(obj);
		requestAnimationFrame(run);
	}
	else
	{
		gameState.ReceiveNewGameState(obj);
	}
}