let image_list = [];

const CLOSE_ZOOM_LEVEL = 2.0;
const NORMAL_ZOOM_LEVEL = 1.0;
const FAR_ZOOM_LEVEL = 0.5;
const FOG_DARKNESS = 140;
const FADE_RING_WIDTH = 40;

let gt1, gt2, gt3, gt4, gt5, gt6; //GLOBAL TRANSFORMS
let fog_norm, fog_close, fog_far; //Fog of War image data

function RenderDrawingComponent(model, position)
{
	context.setTransform(gt1, gt2, gt3, gt4, Math.round(gt5 + (position.x * gt1)), Math.round(gt6 + (position.y * gt4))); //we must round the X & Y positions so that it doesn't break the textures
	context.rotate(model.rotation); //this is in radians
	context.globalAlpha = model.alpha;
		
	let img = FindImageBySource(model.img.src);
	let sprite = img.sprites[model.sprIdx];
		
	context.drawImage(img, sprite.x, sprite.y, sprite.w, sprite.h, -model.drawW/2, -model.drawH/2, model.drawW, model.drawH);
}

//Draws three F.O.W. images and stores them in 3 variables for later drawing
function drawFogOfWarImages(visibility){
	//create the image data for the three settings
	fog_norm = fog_context.createImageData(fog_canvas.width, fog_canvas.height);
	fog_close = fog_context.createImageData(fog_canvas.width, fog_canvas.height);
	fog_far = fog_context.createImageData(fog_canvas.width, fog_canvas.height);
	let x, y, dist, r_in, r_out, grad_level; //variables used in generation

	//draw the normal zoom image first
	for(let i = 0; i < fog_norm.data.length; i+=4){
		x = (i/4) %  fog_canvas.width;
		y = Math.floor((i/4) / fog_canvas.width);
		dist = Math.sqrt(Math.pow(x - (canvas.width/2), 2) + Math.pow(y - (canvas.height/2), 2)); //distance from the middle of the screen to this pixel
		r_in = (((grid_length * visibility) - (FADE_RING_WIDTH/2)) * NORMAL_ZOOM_LEVEL);//inner radius of fade ring
		r_out = (((grid_length * visibility) + (FADE_RING_WIDTH/2)) * NORMAL_ZOOM_LEVEL);//outer radius of fade ring

		if(dist > r_out){
			fog_norm.data[i+3] = FOG_DARKNESS;
		}
		else if((dist > r_in) && (dist <= r_out)){
			grad_level = ((dist - r_in) / (r_out - r_in)) * FOG_DARKNESS;
			fog_norm.data[i+3] = grad_level;
		}else{
			fog_norm.data[i+3] = 0;
		}
	}

	//draw the far zoom
	for(let i = 0; i < fog_far.data.length; i+=4){
		x = (i/4) %  fog_canvas.width;
		y = Math.floor((i/4) / fog_canvas.width);
		dist = Math.sqrt(Math.pow(x - (canvas.width/2), 2) + Math.pow(y - (canvas.height/2), 2)); //distance from the middle of the screen to this pixel
		r_in = (((grid_length * visibility) - (FADE_RING_WIDTH/2)) * FAR_ZOOM_LEVEL); //inner radius of fade ring
		r_out = (((grid_length * visibility) + (FADE_RING_WIDTH/2)) * FAR_ZOOM_LEVEL);//outer radius of fade ring

		if(dist > r_out){
			fog_far.data[i+3] = FOG_DARKNESS;
		}
		else if((dist > r_in) && (dist <= r_out)){
			grad_level = ((dist - r_in) / (r_out - r_in)) * FOG_DARKNESS;
			fog_far.data[i+3] = grad_level;
		}
		else{
			fog_far.data[i+3] = 0;
		}
	}

	//draw the close zoom
	for(let i = 0; i < fog_close.data.length; i+=4){
		x = (i/4) %  fog_canvas.width;
		y = Math.floor((i/4) / fog_canvas.width);
		dist = Math.sqrt(Math.pow(x - (canvas.width/2), 2) + Math.pow(y - (canvas.height/2), 2)); //distance from the middle of the screen to this pixel
		r_in = (((grid_length * visibility) - (FADE_RING_WIDTH/2)) * CLOSE_ZOOM_LEVEL);//inner radius of fade ring
		r_out = (((grid_length * visibility) + (FADE_RING_WIDTH/2)) * CLOSE_ZOOM_LEVEL);//outer radius of fade ring

		if(dist > r_out){
			fog_close.data[i+3] = FOG_DARKNESS;
		}
		else if((dist > r_in) && (dist <= r_out)){
			grad_level = ((dist - r_in) / (r_out - r_in)) * FOG_DARKNESS;
			fog_close.data[i+3] = grad_level;
		}
		else{
			fog_close.data[i+3] = 0;
		}
	}
}

function FindImageBySource(src)
{
	// console.log("finding image with name: " + src);
	for (let i = 0; i < image_list.length; i++)
	{
		if (image_list[i].name === src) return image_list[i];
	}
	return null;
}

function loadJSON() 
{   
    let request = new XMLHttpRequest();
    request.overrideMimeType("application/json");
    request.open('GET', 'scripts/images.json', false);
    request.send(null);
    
    if (request.status === 200)
    {
    	let obj = JSON.parse(request.responseText);
    	ParseImages(obj);
    }
 }

function ParseImages(obj)
{
	let i_list = obj.images;
	
	for (let i = 0; i < i_list.length; i++)
	{
		let image = new Image();
		image.name = i_list[i].src;
		image.src = i_list[i].src;
		GenerateSpriteSheetData(i_list[i].params.sprite_height, i_list[i].params.sprite_width, i_list[i].params.columns, i_list[i].params.rows, image);
		image_list.push(image);
	}
}

function GenerateSpriteSheetData(pHeight, pWidth, cols, rows, img)
{
	img.sprites = [];
	for(let i = 0; i < rows; i++)
	{
		for(let j = 0; j < cols; j++)
		{
			img.sprites.push({x:(pWidth*j), y:(pHeight*i), w:pWidth, h:pHeight});
		}
	}
}