//CANVAS INFORMATION
let canvas = document.getElementById("game_canvas");
let context = canvas.getContext("2d");
canvas.height = document.documentElement.clientHeight;
canvas.width = document.documentElement.clientWidth;

let fog_canvas = document.getElementById("fog_canvas");
let fog_context = fog_canvas.getContext("2d");
fog_canvas.height = canvas.height;
fog_canvas.width = canvas.width;

let gui_canvas = document.getElementById("gui_canvas");
let gui_context = gui_canvas.getContext("2d");
gui_canvas.height = canvas.height;
gui_canvas.width = canvas.width;

//INPUT INFORMATION
let input_handler;

//MOVEMENT AND COLLISION CONSTANTS
const UP    = 0b1000;
const DOWN  = 0b0100;
const LEFT  = 0b0010;
const RIGHT = 0b0001;

//GAME STATE OBJECTS
let map;

//NON-SERVER-BASED ITEMS
let canvas_box;
let guis = [];
let respawnCounter;
let gameScoreGUI;

//FRAME TIME & DELTA_T
let lastFrameTime;
let global_delta_t = 0; //the time that this frame took in SECONDS

let current_zoom_lvl = 2;

//////TESTING//////
//stuff for fps testing
let is_fps_running = true;
let fps_frame_times = [];
let rolling_buffer_length = 30;
///////////////////

//all functions
function setup(initial_game_state) 
{
	loadJSON();
	InitializeGameState(initial_game_state);

	//Draw fog of war images and put the normal zoom one on
	// drawFogOfWarImages(gameState.player.visibility);
	//drawFogOfWarImages(50); // 50 sees all
	//fog_context.putImageData(fog_norm, 0, 0);

	//set the global transforms
	gt1 = 1; //x scale
	gt2 = 0; //x skew
	gt3 = 0; //y skew
	gt4 = 1; //y scale
	gt5 = 0; //x trans
	gt6 = 0; //y trans

	gt5 = -1 * ((gameState.player.x * gt1) - (canvas.width / 2));
	gt6 = -1 * ((gameState.player.y * gt4) - (canvas.height / 2));

	//canvas_box = new Entity(background_tiles, 0, gameState.player.x, gameState.player.y, canvas.width, canvas.height, 0, 0); //invisible box to determine whether or not to display an entity
	
	//////GUI ELEMENTS//////
	//guis.push(new HealthGUI(30, gui_canvas.height - 20, 200, 20)); //health bar
	//guis.push(new WeaponSelectGUI());
	//guis.push(new ItemSlotGUI(gui_canvas.width - 45, gui_canvas.height - 45));
	//guis.push(new AmmoGUI(30, gui_canvas.height - 50, 20, 200, 5));
	
	let respawn_time = 10000;
	/*
	if(arr[3] == "CTF"){
		respawn_time = 10000;
	}
	else if(arr[3] == "TDM"){
		respawn_time = 5000;
	}
	*/
	respawnCounter = new  RespawnCounter(gui_canvas.width/2, gui_canvas.height/2, respawn_time);
	
	gameScoreGUI = new GameScoreGUI(gui_canvas.width/2, 0);
	////////////////////////

	SetupInputHandling();

	lastFrameTime = Date.now();
	
	document.getElementById("loading_screen").remove();
}

function run() 
{
	//debugger; //keeps people from messing with code
	
	global_delta_t = (Date.now() - lastFrameTime) / 1000; //set the time of the most recent frame (in seconds)
	lastFrameTime = Date.now();
	
	//For FPS testing purposes
	if (is_fps_running)
	{
		fps_frame_times.push(global_delta_t);
		if (fps_frame_times.length == (rolling_buffer_length + 1))
		{
			fps_frame_times.splice(0, 1); //remove the oldest element
			let temp = Math.round((1 / listAverage(fps_frame_times)));
			document.getElementById("fps").innerHTML = (client_id + ": " + temp);
		}
	}

	//////CLEAR THE CANVASES//////
	context.setTransform(1, 0, 0, 1, 0, 0); //reset the transform so the clearRect function works
	context.clearRect(0, 0, canvas.width, canvas.height); //clear the canvas
	gui_context.setTransform(1, 0, 0, 1, 0, 0); //reset the transform so the clearRect function works
	gui_context.clearRect(0, 0, gui_canvas.width, gui_canvas.height); //clear the canvas
	

	//////UPDATE EVERYTHING///////
	input_handler.update();
	sendInputMessageToServer(input_handler.getSnapshot()); //sends the InputSnapshot to the server
	//canvas_box.x = gameState.player.x; //update the canvas_box position
	//canvas_box.y = gameState.player.y;
	gameState.Update();
	
	//TOGGLE THE ZOOM LEVEL V IMPORTANT
	if(input_handler.keys_pnr.includes(90)){
		//switch zoom level!
		ToggleZoom();
	}
	gt5 = -1 * ((gameState.player.x * gt1) - (canvas.width / 2));
	gt6 = -1 * ((gameState.player.y * gt4) - (canvas.height / 2));
	
	//////DRAW EVERYTHING///////
	gameState.Draw();
	
	input_handler.endOfFrameCleanup();
	requestAnimationFrame(run); //run again please
}

function ToggleZoom()
{
	if (current_zoom_lvl == 1)
	{
		current_zoom_lvl = 2;
		gt1 = NORMAL_ZOOM_LEVEL; //setting scaling to normal levels
		gt4 = NORMAL_ZOOM_LEVEL;
		gt5 = -1 * ((gameState.player.x * gt1) - (canvas.width / 2));
		gt6 = -1 * ((gameState.player.y * gt4) - (canvas.height / 2));
		fog_context.putImageData(fog_norm, 0, 0);
	}
	else if (current_zoom_lvl == 2)
	{
		current_zoom_lvl = 3;
		gt1 = FAR_ZOOM_LEVEL; //setting scaling to wide levels
		gt4 = FAR_ZOOM_LEVEL;
		gt5 = -1 * ((gameState.player.x * gt1) - (canvas.width / 2));
		gt6 = -1 * ((gameState.player.y * gt4) - (canvas.height / 2));
		fog_context.putImageData(fog_far, 0, 0);
	}
	else if (current_zoom_lvl == 3)
	{
		current_zoom_lvl = 1;
		gt1 = CLOSE_ZOOM_LEVEL; //setting scaling to zoomed levels
		gt4 = CLOSE_ZOOM_LEVEL;
		gt5 = -1 * ((gameState.player.x * gt1) - (canvas.width / 2));
		gt6 = -1 * ((gameState.player.y * gt4) - (canvas.height / 2));
		fog_context.putImageData(fog_close, 0, 0);
	}
	//canvas_box.dWidth = canvas.width / gt1; //update the height and width of the canvas box
	//canvas_box.dHeight = canvas.height / gt4;
	//canvas_box.updateCollisionRadius(); //update the collision_radius so that culling still works properly with zoom
}

function InitializeInterpolatingEntity(entity)
{
	//console.log(entity.model.drawH);
	//console.log(entity.model.alpha);
	
	entity.delta_model = {};
	entity.delta_model.sprIdx 	= 0; 
	entity.delta_model.drawW 	= 0;
	entity.delta_model.drawH 	= 0;
	entity.delta_model.rotation = 0;
	entity.delta_model.alpha 	= 0;
	
	entity.delta_position = {};
	entity.delta_position.x = 0
	entity.delta_position.y = 0
	
	entity.UpdateInterpolation = function(next_entity, time_in_ms)
	{
		//console.log(this.model);
		//console.log(next_entity.model);
		//console.log(time_in_ms);
		// TODO if time in ms > 500, just set the model and position to next entity
		let time_in_sec = time_in_ms/1000;
		this.delta_position.x 		= (next_entity.position.x - this.position.x) 			/ (time_in_sec);
		this.delta_position.y 		= (next_entity.position.y - this.position.y) 			/ (time_in_sec);
		
		this.model.img 				= next_entity.model.img;
		this.delta_model.sprIdx 	= (next_entity.model.sprIdx - this.model.sprIdx) 		/ (time_in_sec);
		this.delta_model.drawW 		= (next_entity.model.drawW - this.model.drawW) 			/ (time_in_sec);
		this.delta_model.drawH 		= (next_entity.model.drawH - this.model.drawH) 			/ (time_in_sec);
		this.delta_model.rotation 	= (next_entity.model.rotation - this.model.rotation) 	/ (time_in_sec);
		this.delta_model.alpha 		= (next_entity.model.alpha - this.model.alpha) 			/ (time_in_sec);
		//console.log(this.delta_model);
	}
	
	entity.Update = function()
	{
		//console.log("Global delta t: " + global_delta_t);
		this.position.x 		+= (this.delta_position.x * global_delta_t);
		this.position.y 		+= (this.delta_position.y * global_delta_t);
		
		this.model.sprIdx 		+= (this.delta_model.sprIdx * global_delta_t);
		FindImageBySource(this.model.img.src).sprites.length
		let max_sprite = FindImageBySource(this.model.img.src).sprites.length - 1;
		if (this.model.sprIdx < 0) this.model.sprIdx = 0;
		if (this.model.sprIdx > max_sprite) this.model.sprIdx = max_sprite;
		
		this.model.drawW 		+= (this.delta_model.drawW * global_delta_t);
		this.model.drawH 		+= (this.delta_model.drawH * global_delta_t);
		this.model.rotation 	+= (this.delta_model.rotation * global_delta_t);
		
		this.model.alpha 		+= (this.delta_model.alpha * global_delta_t);
		if (this.model.alpha < 0.0) this.model.alpha = 0.0;
		if (this.model.alpha > 1.0) this.model.alpha = 1.0;
	}
}

/*
function Player(width, height, img, x, y, role, team, client_id)
{
	this.client_id = client_id; //this is the player's specific id. no one else in any match is allowed to have this at the same time

	//this.base = Entity;
	//sprite 0, rotate 0, transparency 1
	//this.base(img, 0, x, y, width, height, 0, 1);
	
	this.role = role;
	this.team = team;

	//WEAPONS AND ITEMS
	this.max_hp = 100;
	this.health = 100;
	this.mov_speed = 150; //pixels per second
	this.weapon1 = "EMPTY";
	this.weapon2 = "EMPTY";
	this.weapon3 = "EMPTY";
	this.weapon4 = "EMPTY";
	this.item_slot = "EMPTY";
	this.visibility = 0;

	//variables for the different power-ups and if they are affecting the player
	this.is_invincible = false;
	this.speed_boost = 1.0;
	this.damage_boost = 1.0;

	this.has_flag = false;
	
	this.setRoleData = function()
	{
		switch(this.role){
			case "recruit":
				this.mov_speed = 140;
				this.max_hp = 100;
				this.health = this.max_hp;
				this.weapon1 = new AutomaticGun("Assault Rifle", 7, 120, 550, 30, 3, 1200, 0.08, ar_icon); //ar
				this.weapon2 = new Shotgun(30, 500, 500, 5, 4, 6000, 0.35); //remington
				this.weapon3 = "EMPTY";
				this.weapon4 = "EMPTY";
				this.currentWeapon = this.weapon1;
				this.visibility = 6;
				break;
				
			case "artillery":
				this.mov_speed = 120;
				this.max_hp = 85;
				this.health = this.max_hp;
				this.weapon1 = new AutomaticGun("SMG", 5, 100, 600, 40, 4, 500, 0.1, smg_icon);
				this.weapon2 = new MortarWeapon(1000, 1, 9, 3000);
				this.weapon3 = "EMPTY";
				this.weapon4 = "EMPTY";
				this.currentWeapon = this.weapon1;
				this.visibility = 6;
				break;
				
			case "infantry":
				this.mov_speed = 140;
				this.max_hp = 105;
				this.health = this.max_hp;
				this.weapon1 = new AutomaticGun("Machine Gun", 8, 134, 450, 100, 2, 1750, 0.15, mg_icon); //mg
				this.weapon2 = new SmokeGrenade(1200, 1, 5, 500);
				this.weapon3 = new Pistol(11, 100, 400, 8, 2, 200, 0.05); //m1911
				this.weapon4 = "EMPTY";
				this.currentWeapon = this.weapon1;
				this.visibility = 5;
				break;
				
			case "scout":
				this.mov_speed = 180;
				this.max_hp = 75;
				this.health = this.max_hp;
				this.weapon1 = new Shotgun(45, 300, 500, 2, 10, 2000, 0.7); //sawed off
				this.weapon2 = new Pistol(11, 100, 400, 8, 2, 200, 0.05); //m1911
				this.weapon3 = "EMPTY";
				this.weapon4 = "EMPTY";
				this.currentWeapon = this.weapon1;
				this.visibility = 7;
				break;
				
			default:
				break;
		}
	}
	this.setRoleData();
	this.currentWeapon = this.weapon1;
	
	this.die = function(){
		//handle the player dying and respawning
	}
	
	this.takeDamage = function(amount){
		if(!this.is_invincible){
			this.health -= amount;
		}
		if(this.health <= 0){
			this.die(); //idk what this is gonna do yet
		}
	}

	//TODO: this needs to take in an inputSnapshot
	this.update = function(snapshot) {
		this.movePlayer(snapshot); //move the player first
		
		this.currentWeapon.update(this, snapshot); //checks to see if the current weapon is to be fired
		
		//WEAPON AND ITEM RELATED KEYPRESSES
		if(snapshot.keys_pnr.includes(49)){
			this.switchWeapon(1);
		}
		else if(snapshot.keys_pnr.includes(50)){
			this.switchWeapon(2);
		}
		else if(snapshot.keys_pnr.includes(51)){
			this.switchWeapon(3);
		}
		else if(snapshot.keys_pnr.includes(52)){
			this.switchWeapon(4);
		}
		if(snapshot.keys_pnr.includes(82)){
			this.currentWeapon.reload();
		}
		if(snapshot.keys_pnr.includes(70)){
			this.useItem();
		}
	}
	
	this.useItem = function(){
		if(this.item_slot == "EMPTY"){
			//play bad sound
			return;
		}
		else{
			this.item_slot = "EMPTY";
		}
	}
	
	this.updateCurrentWeapon = function(weapon_name){
		
	}
	
	this.switchWeapon = function(weapon_num){
		switch(weapon_num){
			case 1:
				if(this.weapon1 != "EMPTY"){
					this.currentWeapon = this.weapon1;
				}
				else{
					//TODO: play cannot switch to that weapon sound
				}
				break;
				
			case 2:
				if(this.weapon2 != "EMPTY"){
					this.currentWeapon = this.weapon2;
				}
				else{
					//TODO: play cannot switch to that weapon sound
				}
				break;
				
			case 3:
				if(this.weapon3 != "EMPTY"){
					this.currentWeapon = this.weapon3;
				}
				else{
					//TODO: play cannot switch to that weapon sound
				}
				break;
				
			case 4:
				if(this.weapon4 != "EMPTY"){
					this.currentWeapon = this.weapon4;
				}
				else{
					//TODO: play cannot switch to that weapon sound
				}
				break;
				
			default:
				console.log("Error: cannot switch to that weapon");
		}
	}
	
	this.movePlayer = function(snapshot){
		let movement_code  = 0b0000; //the binary code for which directions the player moving

		//this section will probably end up on the server
		if (snapshot.keys_down.includes(87)) {
			movement_code |= UP; //trying to move up
		}
		if (snapshot.keys_down.includes(65)) {
			movement_code |= LEFT; //trying to move left
		}
		if (snapshot.keys_down.includes(68)) {
			movement_code |= RIGHT; //trying to move right
		}
		if (snapshot.keys_down.includes(83)) {
			movement_code |= DOWN; //trying to move down
		}

		if((movement_code & (UP | DOWN)) == 0b1100){ //if both up and down are pressed
			movement_code &= ~(UP | DOWN); //clear the up and down bits
		}
		if((movement_code & (LEFT | RIGHT)) == 0b0011){ //if both left and right are pressed
			movement_code &= ~(LEFT | RIGHT); //clear the left and right bits
		}

		let delta_x = 0;
		let delta_y = 0;
		if(movement_code == 0b1010){
			delta_y = -1 * this.mov_speed * this.speed_boost * SIN_45 * snapshot.frameTime;
			delta_x = -1 * this.mov_speed * this.speed_boost * SIN_45 * snapshot.frameTime;
		}
		else if(movement_code == 0b1001){
			delta_y = -1 * this.mov_speed * this.speed_boost * SIN_45 * snapshot.frameTime;
			delta_x = this.mov_speed * this.speed_boost * SIN_45 * snapshot.frameTime;
		}
		else if(movement_code == 0b0110){
			delta_y = this.mov_speed * this.speed_boost * SIN_45 * snapshot.frameTime;
			delta_x = -1 * this.mov_speed * this.speed_boost * SIN_45 * snapshot.frameTime;
		}
		else if(movement_code == 0b0101){
			delta_y = this.mov_speed * this.speed_boost * SIN_45 * snapshot.frameTime;
			delta_x = this.mov_speed * this.speed_boost * SIN_45 * snapshot.frameTime;
		}
		else if(movement_code == 0b1000){
			delta_y = -1 * this.mov_speed * this.speed_boost * snapshot.frameTime;
		}
		else if(movement_code == 0b0100){
			delta_y = this.mov_speed * this.speed_boost * snapshot.frameTime;
		}
		else if(movement_code == 0b0010){
			delta_x = -1 * this.mov_speed * this.speed_boost * snapshot.frameTime;
		}
		else if(movement_code == 0b0001){
			delta_x = this.mov_speed * this.speed_boost * snapshot.frameTime;
		}

		if(delta_x != 0){
			this.x += delta_x;
			gt5 -= (delta_x * gt1);
			for(let i = 0; i < gameState.walls.length; i++){
				if(isColliding(this, gameState.walls[i]))
				{
					//if the player hit a wall, reset the player positions and global transforms
					this.x -= delta_x;
					gt5 += (delta_x * gt1);
					break; //does not check the other walls if at least one was hit
				}
			}
		}
		if(delta_y != 0){
			this.y += delta_y;
			gt6 -= (delta_y * gt4);
			for(let i = 0; i < gameState.walls.length; i++){
				if(isColliding(this, gameState.walls[i]))
				{
					//if the player hit a wall, reset the player positions and global transforms
					this.y -= delta_y;
					gt6 += (delta_y * gt4);
					break; //does not check the other walls if at least one was hit
				}
			}
		}
	}
}
*/

//this code executes right when the page is loaded
//but it needs to be at the end of the file because it references
//certain functions in other files that require classes that exist in this file
//to have already been defined
if(document.getElementById("loading_screen").complete)
{
	msg = new ServerMessage("join");
	msg.addData("client_id", client_id);
	connectToServer(msg);
}
else
{
	document.getElementById("loading_screen").onload = function()
	{
		msg = new ServerMessage("join");
		msg.addData("client_id", client_id);
		connectToServer(msg);
	}
}