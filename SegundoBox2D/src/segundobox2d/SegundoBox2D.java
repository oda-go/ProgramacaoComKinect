package segundobox2d;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

import pbox2d.PBox2D;
import processing.core.PApplet;


public class SegundoBox2D extends PApplet {

	//Physics physics;
	PBox2D box2d;
	
	public void setup() {
		//initScene();
		box2d = new PBox2D(this);
		box2d.createWorld();
		box2d.setGravity(0, -10);
	}

//	public void initScene() {
//		float gravX = 0.0f;
//		float gravY = -10.0f;
//		float screenAABBWidth = 2 * width;
//		float screenAABBHeight = 2 * height;
//		float borderBoxWidth = width;
//		float borderBoxHeight = height;
//		float pixelsPerMeter = 30;
//		physics = new Physics(this, width, height, gravX, gravY,
//				screenAABBWidth, screenAABBHeight, borderBoxWidth,
//				borderBoxHeight, pixelsPerMeter);
//		physics.setDensity(1.0f);
//	}

	
	
	@Override
	public void draw() {
		box2d.step();
		Vec2 mundoPosicao = new Vec2(0,0);
		 
		Vec2 pixelPos = box2d.coordWorldToPixels(mundoPosicao);
		//ellipse(pixelPos.x, pixelPos.y ,160,160);
		
		BodyDef def = new BodyDef();
		def.position = pixelPos;
		
		Body b = box2d.createBody(def);
		
		
	}
	/*
	public void draw2() {
		background(0);

		if (keyPressed) {
			if (key == '1') {
				physics.createCircle(mouseX, mouseY, random(5, 10));
			} else if (key == '2') {
				float sz = random(5, 10);
				physics.createRect(mouseX - sz, mouseY - sz, mouseX + sz,
						mouseY + sz);
			} else if (key == '3') {
				float sz = random(10, 20);
				physics.createPolygon((float)mouseX, (float)mouseY, (float)mouseX + sz,(float) mouseY, (float)(mouseX + sz * .5), (float)mouseY - sz);
			} else if (key == '4') {
				int nVerts = floor(random(4, 8));
				float rad = random(5, 10);
				float[] vertices = new float[nVerts * 2];
				for (int i = 0; i < nVerts; ++i) {
					vertices[2 * i] = mouseX + rad * sin(TWO_PI * i / nVerts);
					vertices[2 * i + 1] = mouseY + rad
							* cos(TWO_PI * i / nVerts);
				}
				physics.createPolygon(vertices);
			} else {
				// Reset everything
				physics.destroy();
				initScene();
			}
		}

	}
	*/

}
