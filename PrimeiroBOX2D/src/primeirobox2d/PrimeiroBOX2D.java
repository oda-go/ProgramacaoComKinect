package primeirobox2d;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;

import pbox2d.PBox2D;
import processing.core.PApplet;


public class PrimeiroBOX2D extends PApplet {
	
	ArrayList<Box> caixas = new ArrayList<Box>();
	PBox2D mundo; //instancia do mundo
	
	
	public void setup() {
		size(600,600);
		smooth();
		frameRate(30);
		
		mundo = new PBox2D(this);
		mundo.createWorld();
		mundo.setGravity(0, -2);
		
		mundo.listenForCollisions();
		
	}

	public void beginContact(Contact c){
		System.out.println("Colidiu !!");
		
//		Fixture textura1 = c.getFixtureA();
//		Fixture textura2 = c.getFixtureB();
//		
//		Body corpoA = textura1.getBody();
//		Body corpoB = textura2.getBody();
//		
//		Vec2 posB = mundo.getBodyPixelCoord(corpoB);
//		fill(0,0,255); // cor do preenchimento
//		stroke(0); // cor da borda
//		rectMode(CENTER);
//		rect(posB.x, posB.y, 16, 16);
//		
//		mundo.destroyBody(corpoA);
		
		
	}
	
	
	public void draw() {
		background(255);
		mundo.step();//se nao tiver essa linha, nada acontece !!
		if(mousePressed){
			
			Box b;
			if(mouseButton == LEFT){
				b = new Box(mouseX, mouseY, 16, 16, true);
			}else{
				b = new Box(mouseX, mouseY, 16, 16, false);
			}
			
			caixas.add(b);
		}
		
		for(Box c : caixas){
			c.display();
		}
		
	}
	
	class Box{
		Body corpo;
		
		
		float x, y, w, h;
		public Box(float _x, float _y, float _w, float _h, 
				boolean fixa) {
			x = _x;
			y = _y;
			w = _w;
			h = _h;
			
			BodyDef definicao = new BodyDef();
			Vec2 posicao = mundo.coordPixelsToWorld(x, y);
			definicao.position.set(posicao);
			definicao.bullet = true;
			
			if(fixa){
				definicao.type = BodyType.STATIC;	
			}else{
				definicao.type = BodyType.DYNAMIC;
			}
			
			
			corpo = mundo.createBody(definicao);
			
			PolygonShape shape = new PolygonShape();
			
			float wMundoReal = mundo.scalarPixelsToWorld(w/2);
			float hMundoReal = mundo.scalarPixelsToWorld(h/2);
			
			shape.setAsBox(wMundoReal, hMundoReal);
			
			FixtureDef textura = new FixtureDef();
			textura.shape = shape;
			textura.density = 1;
			textura.friction = 0.3f;
			textura.restitution = 2f;
			
			corpo.createFixture(textura);
			
			
		}
		void display(){
			Vec2 posB = mundo.getBodyPixelCoord(corpo);
			float a = corpo.getAngle();
			pushMatrix();
			translate(posB.x, posB.y);
			rotate(-a);
			fill(175); // cor do preenchimento
			stroke(0); // cor da borda
			rectMode(CENTER);
			rect(0, 0, w, h);
			popMatrix();
		}
	}
}
