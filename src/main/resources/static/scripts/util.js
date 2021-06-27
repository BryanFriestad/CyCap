//CONSTANTS
const GRAVITY = 9.81;
const SIN_45 = Math.sin(Math.PI/4);
const SIN_30 = 0.5;
const SIN_60 = Math.sin(Math.PI/3);
const ARTILLERY_TIME = 3000; //milliseconds

function getWeightedIndex(list){
	let temp = Math.random();
	let sum = 0;
	for(let i = 0; i < list.length; i++){
		sum += list[i];
		if(temp < sum){
			return i;
		}
	}
	return -1; //this should error
}

function listAverage(list){
	let sum = 0;
	for(let i = 0; i < list.length; i++){
		sum += list[i];
	}
	return (sum/list.length);
}

//returns a random int between 0 and max, not including max
function getRandomInt(max){
	return Math.floor(Math.random() * Math.floor(max));
}

//returns a random int between lower and upper, inclusive
function getRandomInRange(lower, upper){
	return getRandomInt(upper - lower + 1) + lower;
}

function distanceBetween(x1, y1, x2, y2){
	return Math.sqrt(Math.pow(x2 -x1, 2) + Math.pow(y2 - y1, 2));
}

function distanceBetweenEntities(ent1, ent2){
	return Math.sqrt(Math.pow(ent1.x - ent2.x, 2) + Math.pow(ent1.y - ent2.y, 2));
}

//returns true if ent_1 is colliding with ent_2
function isColliding(ent_1, ent_2){
	
	//QUICK COLLISION DETECTION
	if(distanceBetweenEntities(ent_1, ent_2) >= (ent_1.collision_radius + ent_2.collision_radius)){
		return false;
	}
	
	//ADVANCED COLLISION DETECTION
	var y_collision = isBetween(ent_1.y - (ent_1.dHeight/2), ent_2.y - (ent_2.dHeight/2), ent_2.y + (ent_2.dHeight/2)) || isBetween(ent_1.y + (ent_1.dHeight/2), ent_2.y - (ent_2.dHeight/2), ent_2.y + (ent_2.dHeight/2)) || isBetween(ent_1.y, ent_2.y - (ent_2.dHeight/2), ent_2.y + (ent_2.dHeight/2));

	if(isBetween(ent_1.x - (ent_1.dWidth/2), ent_2.x - (ent_2.dWidth/2), ent_2.x + (ent_2.dWidth/2)) && y_collision){
		return true;
	}
	else if(isBetween(ent_1.x + (ent_1.dWidth/2), ent_2.x - (ent_2.dWidth/2), ent_2.x + (ent_2.dWidth/2)) && y_collision){
		return true;
	}
	else if(isBetween(ent_1.x, ent_2.x - (ent_2.dWidth/2), ent_2.x + (ent_2.dWidth/2)) && y_collision){
		return true;
	}
	else{
		return false;
	}
	/* This was the older method of collision detection. it is simpler and could still be used for more basic detection
	if (isBetween(ent_1.x, (ent_2.x -  (ent_2.dWidth/2)), (ent_2.x +  (ent_2.dWidth/2)))
	 && isBetween(ent_1.y, (ent_2.y -  (ent_2.dHeight/2)), (ent_2.y +  (ent_2.dHeight/2)))){
		return true;
	}
	else{
		return false;
	}
	*/
}

//returns true if num is between lower and upper, inclusive
function isBetween(num, lower, upper){
	if(num >= lower && num <= upper){
		return true;
	}
	else{
		return false;
	}
}

function toRadians(angle) {
  return (angle * (Math.PI / 180.0));
}

function toDegrees(angle) {
  return (angle * (180.0 / Math.PI));
}