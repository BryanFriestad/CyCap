class GamePosition {

	constructor() {
		this._x = 0;
		this._y = 0;
	}

	constructor(x, y) {
		this._x = x;
		this._y = y;
	}

	get x() {
		return this._x;
	}

	set x(x) {
		this._x = x;
	}

	get y() {
		return this._y;
	}

	set y(y) {
		this._y = y;
	}

	getClosestGridX(){
		return Math.round((this._x - (grid_length / 2.0)) / grid_length);
	}
	
	getClosestGridY(){
		return Math.round((this._y - (grid_length / 2.0)) / grid_length);
	}
}

class GridLockedGamePosition extends GamePosition {

	constructor GridLockedGamePosition() {
		super();
		this.x = 0;
		this.y = 0;
	}

	constructor GridLockedGamePosition(x, y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	set x(new_x){
		super.x = new_x;
		gridX = this.getClosestGridX();
		super.x = (gridX * grid_length) + (grid_length / 2);
	}
	
	set y(new_y){
		super.y = new_y;
		gridY = this.getClosestGridY();
		super.y = (gridY * grid_length) + (grid_length / 2);
	}
}
